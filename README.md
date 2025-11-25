# Blind Chicken Market - Backend

간단 설명
- Blind Chicken Market의 백엔드 서비스입니다.

목차
- 소개
- 주요 기능
- 기술 스택 (자리표시자)
- 빠른 시작
  - 요구사항
  - 설치
  - 환경 변수
  - 실행
- 테스트
- API 요약
- 데이터베이스 및 마이그레이션
- 구현 중점
- 배포
- 기여 방법
- 라이선스 및 연락처

소개
- 이 프로젝트는 실시간 경매입니다.

## 주요 기능 

1. 사용자
   1. 회원가입
   2. 로그인 (JWT 발급)
   3. 로그아웃
   4. 토큰 재발급
2. 유저
   1. 내 정보 가져오기
   2. 내 정보 수정하기
   3. ~~내 별명 수정~~
3. 상품
   1. 상품 등록하기
   2. 상품 목록 조회하기
   3. 상품 상세 조회하기
4. S3
   1. Upload Presigned URL 발급
   2. ~~Download Presigned URL 발급~~
5. 경매
   1. 경매 참여하기

## 기술 스택
- 애플리케이션 언어: Java 17
- 프레임워크: Spring Boot
- 빌드 도구: Gradle
- DB: PostgreSQL, Redis

테스트
  - Gradle: ./gradlew test

API 요약 (예시)
- RESTful API: http://localhost:8080/swagger-ui/index.html
-

## 🚀 Performance Improvements
### 1. Refresh Token Rotation 도입으로 보안성 강화
- 토큰 재발급 시 기존 토큰의 무효화로 **재사용 공격 방지**
- Redis를 활용을 통한 로그아웃, 세션 만료 흐름 일관성 확보

### 2. Stomp WebSocket 도입을 통해 실시간 경매 처리 구조 개선
- Long Polling 방식 대비 **네트워크 오버헤드 감소** 및 **실시간성 향상**
- 다수의 동시 접속자 처리에 유리한 구조로 **확장성 강화**

### 3. Redis Sorted Set을 활용한 경매 종료 시스템 성능 개선
- 서버 재시작 시 경매 종료 스케쥴 유실 방지로 **운영 안정성** 향상
- Quartz 제거로 인한 **설정 복잡도 및 운영 복잡도 감소**
- Quartz JDBC Store 대비 약 **20배 이상 빠른 조회 및 처리 속도** 달성
- 다중 인스턴스 환경에서도 중복 실행 없이 안전한 작업 처리 가능