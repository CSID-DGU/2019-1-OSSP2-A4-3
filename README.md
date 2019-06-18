# 음식을 찾아줘!

## tensorflow model을 사용한 food detection program

### 2019년도 1학기 공개SW프로젝트 2분반 A4팀 (3조)

#### 팀원
2017112154 응웬딩흐엉 : 어플리케이션 담당.<br>
2015112120 임성두 : 모델 학습 및 발표 담당.<br>
2014112081 정세인 : 어플리케이션 담당.<br>
2015112109 탁성환 : 모델 학습 담당, 팀장.
<br>
<br>
#### 실행 환경
Android Studio<br>
Android SDK 27 Version
<br>
<br>
#### 프로젝트 설명
 이 프로젝트는 한국에 방문하는 외국인들이 이름을 잘 모르는 한식을 먹고 싶을 때 사진을 찍어서 주변 음식점을 찾을 수 있도록 하기 위해 시작!<br>
 모델의 architecture는 inception resnet v2 를 사용하였다.<br>
 모델 링크 : https://drive.google.com/open?id=1OwwKwRP7kqvcnL1O-8IBgd5NGHPVnFcg <br>
 apk 링크 : https://drive.google.com/open?id=1qHBacAkuZTnvCiBh2YVdxEtyPZoJRCOv
<br>
<br>
#### 실행 방법
1. github에 올라와있는 파일을 다운로드 후 Android Studio에서 Android-TensorFlow 폴더를 open.
2. 구글 드라이브 링크로 들어가 모델을 다운로드 후 Android-TensorFlow/app/src/main/assets/ 에 저장.
3. 1번의 방법대로 open 후 build 하여 휴대전화와 연결 후 run 하거나 build apk 이후 휴대전화에 다운로드하여 설치.
또는 구글 드라이브 링크로 들어가 apk를 다운로드 후 설치.
<br>
<br>
#### 실행 시 유의사항
1. Android Studio에서 open 할 때 다운로드한 전체 폴더를 하지 말고 반드시 Android-TensorFlow 폴더로 open 할 것!
2. Android-TensorFlow/app/src/main/java/com/amitshekhar/tflite/ 의 CameraActivity.java 안의 MODEL_PATH의 이름과 assets에 저장한 모델의 이름이 일치하는 지 확인할 것!
3. 2번과 마찬가지로 같은 파일의 LABEL_PATH의 이름과 assets에 저장한 라벨의 이름이 일치하는 지 확인할 것!
<br>
<br>
#### 결과물 소개
 <div>
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682012-3fdb9d00-9210-11e9-8960-0d9e0a09640f.jpg">
 </div>
 
 1. 흰색으로 동그라미 친 부분에서 국가를 선택할 수 있다. 국가는 한국, 일본, 베트남 3국을 선택할 수 있으며, 국가를 변경하면 메인 페이지에서의 음식 추천이 선택한 국적의 음식 추천으로 바뀐다.
 <br>
 <br>
 <div>
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682486-50404780-9211-11e9-9733-c9ce222fb12a.jpg">
 </div>
 
 2. 로그인 시에 보이는 메인 화면이다. 한국 국적을 선택했기 때문에 한식이 추천되는 모습을 볼 수 있다.
 <br>
 <br>

 <div>
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682519-60f0bd80-9211-11e9-9272-740006fec5ae.jpg">
 </div>
 
 3. 왼쪽 위의 메뉴를 누르면 3국과 detection, contact, my page 버튼이 있다. 각 국가를 클릭하면 아래와 같이 각 국가별 음식의 목록들이 나온다. contact의 경우에는 개발자 정보, my page의 경우에는 로그인 시의 정보가 나온다.
 
 <div>
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682524-62ba8100-9211-11e9-8ba7-0162d6a68f91.jpg">
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682526-63ebae00-9211-11e9-871b-58ab5045fcbb.jpg">
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682529-651cdb00-9211-11e9-902e-7863bb75b2e1.jpg">
 </div>
  <br>
 <br>
  <br>
 <br>
 <div>
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682533-664e0800-9211-11e9-8328-c343a4da7e74.jpg">
 </div>
 
 4. 한식의 음식 목록에서 잡채를 클릭하면 나오는 화면이다. 이 부분의 기능에 대해서는 detection 부분을 설명한 뒤에 같이 설명하겠다.
 <br>
 <br>

 <div>
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682539-677f3500-9211-11e9-8034-429ec93a9ac8.jpg">
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682540-68b06200-9211-11e9-869b-6defb858c96c.jpg">
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682543-69e18f00-9211-11e9-8e4e-3ce94a06bcde.jpg">
 </div>
 
 5. detection에 들어가서 소주병을 앞에 놓고 take photo를 누르면 추론 결과로 소주가 뜨는 것을 볼 수 있다. 나온 결과를 누르면 소주에 관한 내용이 오른쪽 사진처럼 나타나게 된다.
 <br>
 <br>

 <div>
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682546-6bab5280-9211-11e9-95da-2c008c2bd430.jpg">
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682548-6c43e900-9211-11e9-96ad-0df70772d495.jpg">
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682549-6d751600-9211-11e9-80f2-9f84eadb1cc2.jpg">
 </div>
 
 6. 이번엔 detection에서 앨범에서 사진을 가져와서 추론하는 모습이다. 다만 여기서 유의할 점은 album에서 사진을 선택한 후 take photo를 한 번 눌러줘야 추론이 된다.
 <br>
 <br>

 <div>
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682552-6ea64300-9211-11e9-8e87-38d3197cf323.jpg">
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682558-71089d00-9211-11e9-8e53-f61f8da331c6.jpg">
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682561-7239ca00-9211-11e9-87f8-32f518e42d0b.jpg">
 </div>
 
 7. 보쌈의 추론 결과를 클릭하여 나온 화면이다. 여기서 search map 부분을 눌러보면 오른쪽 사진과 같이 자신의 현재 위치 주변의 음식점이 나온다. 이 결과에서 오른쪽 아래의 검은 동그라미 부분을 누르면 오른쪽 사진과 같이 그 식당까지 갈 수 있는 교통편을 알려준다.
 <br>
 <br>

 <div>
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682562-736af700-9211-11e9-8d65-baa8ff9f8ee1.jpg">
 <img width="200" src="https://user-images.githubusercontent.com/48276736/59682568-749c2400-9211-11e9-9b06-97e7baea4337.jpg">
 </div>
 
 8. 이번에는 보쌈에서 watch in youtube 부분을 눌러보면 오른쪽과 같이 유튜브의 보쌈 레시피 영상을 볼 수 있다.
<br>
<br>
#### 모델 성능 평가
<img src="https://user-images.githubusercontent.com/48276736/59683898-f0976b80-9213-11e9-9d3b-339cb799d7a2.PNG">
 recall_5 약 99%, accuracy 약 91.5% 의 성능을 보인다. <br>
 어플리케이션에서 동작할 경우에는 lite로 변환함에 따라서 성능이 약간 저하된다.
<br>     
<br>
#### 참고 github link
https://github.com/amitshekhariitbhu/Android-TensorFlow-Lite-Example
<br>
<br>
#### 문의
응웬딩흐엉 nguyenhuonghq97@gmail.com <br>
임성두 sungdoolim@naver.com <br>
정세인 wnddkdpckqd@gmail.com <br>
탁성환 z0116z@naver.com
