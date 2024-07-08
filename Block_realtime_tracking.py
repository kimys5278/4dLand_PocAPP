import cv2
from PIL import ImageFont, ImageDraw, Image
import numpy as np
from ultralytics import YOLO
from io import BytesIO
import requests

font = ImageFont.truetype('./utils/MaruBuri-Bold.ttf', 20)
# 조합
names = {
    0: ('기본', (255, 0, 0)),      # Red
    1: ('계단', (255, 165, 0)),    # Orange
    2: ('모아', (255, 255, 0)),    # Yellow
    3: ('틀어', (0, 255, 0)),      # Green
    4: ('지그재그', (0, 0, 255)),   # Blue
    5: ('세워', (128, 0, 128)),    # Purple
    6: ('교차', (255, 0, 255)),    # Magenta
    7: ('돌려', (0, 0, 0)),        # Black
    8: ('기댐', (255, 255, 255)),  # White
    9: ('비스듬', (128, 128, 128)) # Gray
}

# Load YOLO model
model = YOLO('./utils/submission.pt')

# Fixed widths
cl_width = 40  # Assumed width for two Korean characters
co_width = 25  # Assumed width for two numeric characters

# URL 설정
url = "http://localhost:8000/predict_image"  # FastAPI 서버 주소

# 이미지 파일 경로 설정
file_path = "images/data1.jpg"  # 여기에 실제 이미지 파일 경로를 입력하세요

# 파일 업로드 및 요청 보내기
with open(file_path, "rb") as file:
    response = requests.post(url, files={"file": file})

# 이미지 응답 확인
if response.status_code == 200:
    # Convert response content to image
    img = Image.open(BytesIO(response.content))
    
    # Process image with YOLO model
    results = model(np.array(img))[0]

    # Convert to PIL for drawing
    frame_pil = Image.fromarray(np.array(img))

    draw = ImageDraw.Draw(frame_pil)

    for xyxy, c in zip(results.xyxy, zip(results.classes, results.scores)):
        x1, y1, x2, y2 = [int(i) for i in xyxy]
        cl, color = names[int(c[0])][0], names[int(c[0])][1]
        co = str(round(float(c[1]), 2))
        
        center_x = (x1 + x2) // 2
        
        # Use PIL to draw text
        draw.text((center_x - cl_width, y1-20), cl, font=font, fill=color)
        draw.text((center_x, y1-20), co, font=font, fill=color)
        
        # Use PIL to draw rectangle
        draw.rectangle([(x1, y1), (x2, y2)], outline=color, width=3)

    # Show the processed frame with detections
    frame_pil.show()

else:
    print("Error:", response.status_code)
