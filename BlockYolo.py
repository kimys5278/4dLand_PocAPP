import logging
from fastapi import FastAPI, HTTPException
from fastapi.responses import JSONResponse
from typing import List, Dict
from io import BytesIO
from PIL import Image, ImageDraw, ImageFont
import numpy as np
from ultralytics import YOLO
import boto3
import uuid
import requests
import os
from dotenv import load_dotenv, find_dotenv
import urllib.parse

# key.env 파일 로드
dotenv_path = find_dotenv('key.env')
if dotenv_path:
    load_dotenv(dotenv_path)
else:
    raise FileNotFoundError('The key.env file was not found')

# 환경 변수 로드
aws_access_key_id = os.getenv('AWS_ACCESS_KEY_ID')
aws_secret_access_key = os.getenv('AWS_SECRET_ACCESS_KEY')
aws_default_region = os.getenv('AWS_DEFAULT_REGION')
bucket_name = os.getenv('S3_BUCKET_NAME')

# 환경 변수 로그로 출력 (확인용)
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# YOLO 모델 로드
model = YOLO('./utils/submission.pt')

# 클래스 이름과 색상 정의
names = {
    0: ('기본', (255, 0, 0)),
    1: ('계단', (255, 165, 0)),
    2: ('모아', (255, 255, 0)),
    3: ('틀어', (0, 255, 0)),
    4: ('지그재그', (0, 0, 255)),
    5: ('세워', (128, 0, 128)),
    6: ('교차', (255, 0, 255)),
    7: ('돌려', (0, 0, 0)),
    8: ('기댐', (255, 255, 255)),
    9: ('비스듬', (128, 128, 128))
}

# S3 클라이언트 설정
s3 = boto3.client(
    's3',
    aws_access_key_id=aws_access_key_id,
    aws_secret_access_key=aws_secret_access_key,
    region_name=aws_default_region
)

# 한글 폰트 경로
font_path = './utils/MaruBuri-Bold.ttf'

app = FastAPI()

def get_rating(accuracy):
    if accuracy >= 80:
        return "perfect"
    elif accuracy >= 60:
        return "great"
    elif accuracy >= 40:
        return "good"
    elif accuracy >= 20:
        return "bad"
    else:
        return "miss"

@app.post("/predict_images")
async def predict_images(image_urls: List[str]):
    try:
        detections: Dict[str, int] = {name[0]: 0 for name in names.values()}
        class_confidences: Dict[str, List[float]] = {name[0]: [] for name in names.values()}
        result_image_urls = []

        for url in image_urls:
            logger.info(f"Processing image from URL: {url}")
            decoded_url = urllib.parse.unquote(url)
            response = requests.get(decoded_url)
            if response.status_code != 200:
                raise HTTPException(status_code=400, detail=f"Failed to download image from {decoded_url}")
            image = Image.open(BytesIO(response.content))
            logger.info(f"Image size: {image.size}")

            if image.mode == 'RGBA':
                image = image.convert('RGB')

            draw = ImageDraw.Draw(image)
            font = ImageFont.truetype(font_path, 15)

            results = model(np.array(image))[0]
            logger.info(f"Detection results: {results}")

            for box in results.boxes:
                class_id = int(box.cls[0])
                class_name, color = names[class_id]
                confidence = round(float(box.conf[0]) * 100, 2)
                class_confidences[class_name].append(confidence)
                detections[class_name] += 1

                x1, y1, x2, y2 = map(int, box.xyxy[0])
                draw.rectangle([x1, y1, x2, y2], outline=color, width=3)
                text = f"{class_name} {confidence}%"
                text_bbox = draw.textbbox((x1, y1), text, font=font)
                text_width = text_bbox[2] - text_bbox[0] + 10
                text_height = text_bbox[3] - text_bbox[1] + 10
                draw.rectangle([x1, y1 - text_height, x1 + text_width, y1], fill=color)
                draw.text((x1 + 5, y1 - text_height + 5), text, fill=(255, 255, 255), font=font)

            result_image_key = f"images/after_result/{uuid.uuid4()}.png"
            buffer = BytesIO()
            image.save(buffer, "PNG")
            buffer.seek(0)
            s3.upload_fileobj(buffer, bucket_name, result_image_key, ExtraArgs={"ContentType": "image/png", "ACL": "public-read"})
            result_image_url = f"https://{bucket_name}.s3.amazonaws.com/{result_image_key}"
            result_image_urls.append(result_image_url)

        detections_list = []
        total_combinations = 0
        for class_name, count in detections.items():
            if count > 0:
                total_combinations += 1
                average_confidence = sum(class_confidences[class_name]) / count
                rating = get_rating(average_confidence)
                detection = {
                    "name": class_name,
                    "accuracy": round(average_confidence, 2),
                    "rate": rating,
                    "imageUrl": result_image_urls.pop(0)
                }
                detections_list.append(detection)

        response = {
            "count": total_combinations,
            "results": detections_list
        }

        return JSONResponse(content=response)
    except Exception as e:
        logger.error(f"Error during image processing: {e}", exc_info=True)
        return JSONResponse(content={"error": str(e)}, status_code=500)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
