# BackEnd 사이드 프로젝트 - 근근웹

**- 근근웹 게시판 URL** : https://board.roco.moe

# 1. 개요
1) Spring Boot 내용 복습 및 배운 내용을 적용해보기 위한 BackEnd 학습 목적 게시판
2) 무료 WebServer와 무료 RDB Server 활용으로 인해, 최대한의 저비용을 목표로 함 
3) 게시글 업로드 및 댓글 기능 제공
4) 이미지 업로드 기능 제공

# 2. 환경
- Spring Boot 3.1.2
- Java 17 (Java openjdk 17.0.8)
- IntelliJ IDEA Community Edition 2023.2 (IDE)
- Qoddi (무료 WebServer) (안내 페이지 : https://qoddi.com)
- Elephant SQL (무료 PostgreSQL RDB PostgreSQL 13.9) (안내 페이지 : https://www.elephantsql.com)
- AWS S3 (이미지 업로드 저장 공간. 1년 Free Tier)

# 3. 백엔드 내부 Flow
![근근웹_백엔드_구조](https://github.com/KimHyungkeun/makeboard_sideproject/assets/12759500/7013140a-3933-48e8-a22f-3b6bc59b4489)
![근근웹_DB관계도](https://github.com/KimHyungkeun/makeboard_sideproject/assets/12759500/5aa90ad3-fe08-4591-a1ac-e4b46333a376)

**1) baseboardlist**
  - **id : 게시글 ID (PK, Auto Increment)**
  - title : 게시글 제목
  - nickname : 작성자 닉네임
  - password : 게시글 패스워드 
  - content : 게시글 내용
  - update_date : 게시글 등록 및 수정 날짜

**2) baseboardreplylist**
  - **reply_id : 댓글 ID (PK, Auto Increment)** 
  - **post_id : 게시글 ID (baseboardlist의 id를 참조하는 FK)** 
  - parent_id : 부모 ID (대댓글과의 구별을 위한 새로운 식별자)
  - content : 댓글 내용
  - nickname : 작성자 닉네임
  - password : 댓글 패스워드
  - update_date : 댓글 등록 및 수정 날짜
  - parent_true_false : 부모댓글인지에 대한 여부

# 4. 백엔드 REST API 기능 간략 설명
![근근웹_swagger_ui](https://github.com/KimHyungkeun/makeboard_sideproject/assets/12759500/7e225d80-5312-4a3b-a5ad-2206ea4e5d57)
1) PUT /board : 게시글 내용 수정
2) POST /board : 게시판에 글을 새로 등록
3) DELETE /board : 게시글 삭제하기
4) GET /board/list : 게시판 전체목록 불러오기
5) GET /board/reply : 현재 게시글에 대한 댓글 전체 조회
6) PUT /board/reply : 댓글 내용 수정
7) POST /board/reply : 댓글 생성
8) DELETE /board/reply : 댓글 삭제
9) GET /board/replyCount : 게시판 목록에서 게시글 별 댓글 갯수 표시
10) GET /board/view : 특정 게시글 불러오기
11) POST /image : 이미지 파일 첨부
