# 4D Block POC APP 

포디 블록을 활용한 교육 자료용 어플리케이션입니다.</br>
블록쌓기와 블록 구성놀이로 구성된 AI 모델을 활용한 어플리케이션 개발이 주된 목표입니다.</br>
블록 쌓기는 정해진 조합을 판단하여 어린이들이 4D블록으로 만든 모양을 만들고, AI 모델은 그 모양에 10개 조합중 어떤 조합으로 만들어 졌는지 판단하여 결과를 제시합니다.</br>
기본,계단,모아,틀어,지그재그,세워,교차,돌려,기댐,비스듬 10가지 조합이 있습니다.</br>
4D블록으로 구조를 만든 후, POC앱으로 사진을 찍으면 어떤 조합이 쓰여졌는지, 몇 개의 조합인지 AI 모델 분석을 통하여 결과를 제공합니다.

## 예시
![image](https://github.com/kimys5278/4dLand_PocAPP/assets/107562291/2b8377d0-db1b-4499-a398-37586962438f)</br>

## 플로우 차트 생성
![image](https://github.com/kimys5278/4dLand_PocAPP/assets/107562291/acbaa140-3f1b-42c8-9674-831bcf01e944)</br>
1. 클라이언트에서 스프링부트로 이미지를 업로드합니다.</br>
2. 스프링부트에서 업로드된 이미지(AI 모델 적용 전) S3 Before 폴더에 저장합니다.</br>
3. 스프링부트에서 이미지를 FastAPI로 넘겨줍니다.</br>
4. FastAPI는 이미지를 받은 후, 학습된 Yolov8 모델을 이미지에 적용합니다.</br>
5. 적용한 이미지는 s3 After 폴더에 저장합니다.</br>
6. FastAPI에서 AI모델을 적용한 이미지와 이미지에 대한 결과 데이터를 스프링부트로 다시 반환합니다.</br>
7. 스프링부트는 FastAPI에서 온 데이터를 RDS(MariaDB)에 저장합니다.</br>
8. 스프링부트는 FastAPI에서 온 데이터를 클라이언트에 반환합니다.</br>

## 결과 
![image](https://github.com/kimys5278/4dLand_PocAPP/assets/107562291/0d349927-37b2-447b-9285-090c35cea987)</br>
![image](https://github.com/kimys5278/4dLand_PocAPP/assets/107562291/235769fd-240f-42e0-ae86-7c493975f7d9)</br>

## 개발 환경
IntellJ</br>
VSCode</br>
SpringBoot</br>
FastAPI</br>

## AI 모델
Yolov8</br>

## 인프라
AWS EC2</br>
AWS S3</br>
AWS RDS</br>

## 데이터베이스
MariaDB</br>
