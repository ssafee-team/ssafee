# 포팅 매뉴얼

이 문서는 `git clone` 이후 서버 배포 방법과 개발 환경 구성 방법을 설명합니다.

## Deploy

배포

### Requirements
- docker

### 1. 인증서 발급 (`certbot`)

```sh
# 1. 인증서 발급
docker run -it --rm --name certbot \
  -v /etc/letsencrypt:/etc/letsencrypt \
  -v /var/lib/letsencrypt:/var/lib/letsencrypt \
  -v ./.secrets/cloudflare.ini:/etc/cloudflare.ini:ro \
  certbot/dns-cloudflare \
    certonly \
    --dns-cloudflare \
    --dns-cloudflare-credentials /etc/cloudflare.ini \
    -d 'ssafy.coffee' -d '*.ssafy.coffee'

# (optonal)
# 2.1. 인증서 갱신 명령어 등록
docker create --rm --name certbot-renew \
  -v /etc/letsencrypt:/etc/letsencrypt \
  -v /var/lib/letsencrypt:/var/lib/letsencrypt \
  -v ./.secrets/cloudflare.ini:/etc/cloudflare.ini:ro \
  certbot/dns-cloudflare \
    renew \
    --dns-cloudflare \
    --dns-cloudflare-credentials /etc/cloudflare.ini \
    -d 'ssafy.coffee' -d '*.ssafy.coffee' --quiet
# 2.2. 인증서 갱신
docker start certbot-renew
```
위 인증서 발급 명령어는 cloudflare에서 관리하는 도메인을 가정합니다. 수정 가능한 문자열은 위와 같습니다.
- `.secrets/cloudflare.ini`
  - 도메인 소유권 확인을 위해 필요합니다. 아래 링크한 문서의 지시에 작성합니다.
  - <https://certbot-dns-cloudflare.readthedocs.io/en/stable/#examples>
- `ssafy.coffee`, `*.ssafy.coffee`
  - 도메인 이름입니다.

### 2. 환경변수 설정 (`.env`)

```sh
touch .env
```

`.env` 파일을 프로젝트 루트에 생성합니다. 이 파일은 도커 컨테이너에 전달해야 하는 환경변수를 나열한 것으로, 작성이 필요한 항목은 다음과 같습니다.

```properties
# Origin
ALLOWED_ORIGIN=http://localhost:3000
PROD_ALLOWED_ORIGIN=https://ssafy.coffee
TEST_ALLOWED_ORIGIN=https://test.ssafy.coffee
# DB
PROD_DB_USERNAME=
PROD_DB_PASSWORD=
PROD_DB_NAME=
TEST_DB_USERNAME=
TEST_DB_PASSWORD=
TEST_DB_NAME=
LOCAL_DB_HOST=
LOCAL_DB_USERNAME=
LOCAL_DB_PASSWORD=
LOCAL_DB_NAME=
# OAuth2
GOOGLE_CLIENT_ID=
GOOGLE_CLIENT_SECRET=
GOOGLE_REDIRECT_URI={baseUrl}/login/oauth2/redirect/{registrationId}
PROD_GOOGLE_REDIRECT_URI=
TEST_GOOGLE_REDIRECT_URI=
# JWT
JWT_ACCESS_SECRET_KEY_BASE64=
JWT_ACCESS_EXPIRATION_SECONDS=
JWT_REFRESH_SECRET_KEY_BASE64=
JWT_REFRESH_EXPIRATION_SECONDS=
# S3
S3_ACCESS_KEY_ID=
S3_SECRET_ACCESS_KEY=
S3_REGION=
S3_ENDPOINT=https://dcfbfd9f489ad8a785c3cdb81469e07d.r2.cloudflarestorage.com
S3_BUCKET=
S3_PUBLIC_BASE_URL=
# docker gid for Jenkins
DOCKER_GROUP_ID=
```

`JWT_ACCESS_SECRET_KEY_BASE64`과 `JWT_REFRESH_SECRET_KEY_BASE64`은 `HMAC512` 알고리즘으로 생성하며, 다음과 같은 코드를 통해 생성할 수 있습니다.

```java
KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
SecretKey secretKey = keyGen.generateKey();
String secretKeyBase64 = Base64.getEncoder().encodeToString(secretKey.getEncoded());
System.out.println(secretKeyBase64);
```

`DOCKER_GROUP_ID`는 Jenkins 컨테이너에 호스트의 docker 그룹 권한을 부여하기 위해 필요합니다. docker gid를 확인할 수 있는 명령어는 다음과 같습니다.
```sh
export DOCKER_GROUP_ID=$(getent group docker | awk -F ":" '{ print $3 }')
```

### 3. 서비스 배포 (`docker compose`)

```sh
docker compose -p ssafee up -d
```

인증서 발급과 환경변수 설정이 완료됐다면 위 명령어를 실행하여 서비스를 배포할 수 있습니다. 외부에 노출되는 서비스는 다음과 같습니다.

- `https://ssafy.coffee`
  - branch: `master`
- `https://test.ssafy.coffeee` (optional)
  - branch: `develop`
- `https://jenkins.ssafy.coffee`

```sh
docker compose -f docker-compose.yml -p ssafee up -d
```

`test.ssafy.coffee`는 `docker-compose.override.yml` 설정에 정의된 서비스입니다. 위 명령어를 통해 명시적으로 설정에서 제외하여 서비스를 배포할 수도 있습니다.

## Development

개발 환경 구성

### Dependencies
- backend
  - jdk 17 (lts)
  - gradle 8.5
  - mysql 8.0
- frontend
  - node 20 (lts)
  - npm 10

### Backend

```sh
cd backend
./gradlew bootJar
```

### Frontend

```sh
cd frontend
npm i
npm run dev
```
