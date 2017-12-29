# CS496_project2

week2 project


공통과제 II (12/29 ~ 1/4일 발표, Syllabus 참조) - 서버 및 DB 기술 적응 및 응용

공통과제 I 을 활용해도 좋고, 처음부터 새로 만들어도 좋음
서버 기술 세미나 시에, 해당 내용에 대한 좀더 자세한 설명을 제공
 	(최근 Http, web service framework을 위주로한 소개 예정)

DB 구축: NoSQL이던 SQL이던 상관없음, MongoDB 추천
서버 구축: HTTP(웹서버)던 TCP던 상관없음, NodeJS 추천
Facebook SDK 연동: https://developers.facebook.com/docs/android/
Facebook Login 구현
Facebook의 User Graph에서 Email 을 가져와, Email을 Key로 한 DB 구축
	
A탭: 나의 통합 연락처 구축. 폰의 연락처 정보와 Facebook 연락처 정보를 통합하여, 서버 DB에 기록하고, 이것을 List View로 표현
B탭: 나의 이미지 갤러리를 서버에 구축. 폰의 사진을 업로드(싱크)해도 되고, 카메라에서 바로 찍어서 업로드해도 좋음. A탭과 동일하게 DB에 저장
Advanced Option  : Amazon AWS에 account를 만들고, AWS SDK를 연동해서,  Amazon S3에 사진을 직접 올려보기
C탭: 서버와 DB를 활용한 자유 주제
