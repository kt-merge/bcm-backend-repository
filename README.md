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
- 개발 규칙 / 코딩 컨벤션
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
- DB: PostgreSQL
- 기타: Redis, S3

빠른 시작

요구사항
- JDK XX 이상 (또는 Node.js X.X)
- 빌드 도구 (Maven/Gradle/npm)
- Docker (선택)
- 데이터베이스 (로컬 or Docker)

설치 (예시)
1. 리포지토리 클론
   git clone <REPO_URL>
   cd blind-chicken-market-backend-repository

2. 의존성 설치 / 빌드
   - Maven 예: mvn clean install
   - Gradle 예: ./gradlew build
   - Node 예: npm install

환경 변수
- application.yml 또는 .env에 설정해야 하는 주요 항목(예시):
  - SERVER_PORT=8080
  - SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/your_db
  - SPRING_DATASOURCE_USERNAME=your_user
  - SPRING_DATASOURCE_PASSWORD=your_password
  - JWT_SECRET=your_jwt_secret
  - AWS_S3_BUCKET=your_bucket (필요 시)
(실제 키/값은 프로젝트 설정 파일을 확인 후 채워주세요)

실행
- 로컬 실행 예:
  - Maven: mvn spring-boot:run
  - Gradle: ./gradlew bootRun
  - Jar 실행: java -jar build/libs/your-app.jar
- 실행 후 기본 URL: http://localhost:8080 (포트는 환경 변수 참조)

테스트
- 유닛/통합 테스트 실행:
  - Maven: mvn test
  - Gradle: ./gradlew test
- 테스트 전략(간단히): 유닛 테스트는 서비스/유틸 중심, 통합 테스트는 DB/외부 연동 검증

API 요약 (예시)
- RESTful API: http://localhost:8080/swagger-ui/index.html
- Async API 문서: http://localhost:8080/api-docs
- 인증
  - POST /api/auth/register
  - POST /api/auth/login
- 사용자
  - GET /api/users/{id}
  - PUT /api/users/{id}
- 상품
  - POST /api/items
  - GET /api/items
  - GET /api/items/{id}
  - PUT /api/items/{id}
  - DELETE /api/items/{id}
(실제 엔드포인트는 소스의 컨트롤러/라우팅 파일을 확인해 채워주세요)

데이터베이스 및 마이그레이션
- DB 초기화: Flyway / Liquibase 사용 여부(확인 후 기입)
- 샘플 데이터 로딩 방법 (SQL 스크립트 또는 데이터 시더)
- Docker Compose 예시 (있다면 추가)

## 🚀 Performance Improvements
### 1. Stomp WebSocket 도입을 통해 실시간 경매 처리 구조 개선
- 기존 Long Polling 방식 대비 **네트워크 오버헤드 감소** 및 **실시간성 향상**
- 다수의 동시 접속자 처리에 유리한 구조로 **확장성 강화**
### 2. Redis Sorted Set을 활용한 경매 종료 시스템 성능 개선
1. 서버 재시작 시 경매 종료 스케쥴 유실 방지로 **운영 안정성** 향상
2. Quartz 제거로 인한 **설정 복잡도 및 운영 복잡도 감소**
3. Quartz JDBC Store 대비 약 **20배 이상 빠른 조회 및 처리 속도** 달성

배포
- 빌드 아티팩트 생성: mvn package / ./gradlew bootJar
- Docker 이미지 빌드 및 레지스트리 푸시(예시)
  docker build -t your-repo/your-app:latest .
  docker push your-repo/your-app:latest
- CI/CD: GitHub Actions / Jenkins / GitLab CI 설정 파일 참조

개발 규칙 / 코드 컨벤션
- 커밋 메시지 스타일 (예: Conventional Commits)
- 브랜치 전략 (예: main, develop, feature/*)
- 코드 포맷터 및 Linter (예: Checkstyle, ESLint)

기여
- 이슈 생성 → 브랜치 생성(feature/bugfix) → PR 템플릿 작성 → 코드 리뷰 → 머지
- PR 템플릿, 코드 리뷰 체크리스트 등을 프로젝트에 추가 권장

라이선스
- 프로젝트 라이선스 유형(예: MIT) 및 저작권 정보를 여기에 추가

연락처
- 유지보수자/팀 이메일 또는 슬랙 채널 등

마지막으로
- 이 README는 기본 템플릿입니다. 프로젝트 루트의 build/setting 파일과 컨트롤러/설정 파일을 확인하여 기술 스택, 실행 커맨드, 환경 변수 등을 실제 값으로 업데이트하세요.
