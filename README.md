# BackEnd 사이드 프로젝트 - 근근웹

- 근근웹 게시판 주소 : https://board.roco.moe

# 1. 개요
1) Spring Boot 내용 복습 및 배운 내용을 적용해보기 위한 BackEnd 학습 목적 게시판
2) 무료 Web 서버와 무료 RDB 서버 활용으로 인해, 관리에 있어 과금이 발생하지 않음
3) 게시글 업로드 및 댓글 기능 제공
4) 이미지 업로드 기능 제공

# 2. 환경
- Spring Boot 3.1.2
- Java 17 (Java openjdk 17.0.8)
- IntelliJ IDEA Community Edition 2023.2 (IDE)
- Qoddi (무료 WebServer)
- Elephant SQL (무료 PostgreSQL RDB PostgreSQL 13.9)

# 3. 백엔드 흐름
![근근웹_백엔드_구조](https://github.com/KimHyungkeun/makeboard_sideproject/assets/12759500/2db02b9e-fe0b-4221-832e-dd1e4f401d7d)


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
