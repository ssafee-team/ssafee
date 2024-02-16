# 포팅 매뉴얼

## usage

### dependencies
- docker

### step-by-step

1. 인증서 발급 (certonly)
```sh
docker run -it --rm --name certbot \
  -v /etc/letsencrypt:/etc/letsencrypt \
  -v /var/lib/letsencrypt:/var/lib/letsencrypt \
  -v /home/ubuntu/.secrets/certbot/cloudflare.ini:/etc/cloudflare.ini:ro \
  certbot/dns-cloudflare \
    certonly \
    --dns-cloudflare \
    --dns-cloudflare-credentials /etc/cloudflare.ini \
    -d 'ssafy.coffee' -d '*.ssafy.coffee'
```
서버 배포에 앞서 인증서를 발급한다. cloudflare에서 관리하는 도메인 `ssafy.coffee`를 가정한다.

2. 인증서 재발급 (renew) (toption)
```sh
docker create --rm --name certbot-renew \
  -v /etc/letsencrypt:/etc/letsencrypt \
  -v /var/lib/letsencrypt:/var/lib/letsencrypt \
  -v /home/ubuntu/.secrets/certbot/cloudflare.ini:/etc/cloudflare.ini:ro \
  certbot/dns-cloudflare \
    renew \
    --dns-cloudflare \
    --dns-cloudflare-credentials /etc/cloudflare.ini \
    -d 'ssafy.coffee' -d '*.ssafy.coffee' --quiet
```
재발급 명령어는 위와 같다.

3. 환경변수 설정 (.env)
```sh
DEV_ORIGIN=
DEV_DB_DATABASE=
DEV_DB_USERNAME=
DEV_DB_PASSWORD=
PROD_ORIGIN=
PROD_DB_DATABASE=
PROD_DB_USERNAME=
PROD_DB_PASSWORD=
GOOGLE_CLIENT_ID=
GOOGLE_CLIENT_SECRET=
GOOGLE_REDIRECT_URI=
JWT_ACCESS_SECRET_KEY_BASE64=
JWT_ACCESS_EXPIRATION_SECONDS=
JWT_REFRESH_SECRET_KEY_BASE64=
JWT_REFRESH_EXPIRATION_SECONDS=
S3_ACCESS_KEY_ID=
S3_SECRET_ACCESS_KEY=
S3_BUCKET=
S3_ENDPOINT=
S3_PUBLIC_ACCESS_DOMAIN=
```
`.env`를 프로젝트 루트에 위와 같은 형태로 생성하고 환경변수를 설정한다.

4. 서버 컨테이너 시작 (only ssafy.coffee)
```sh
docker compose -f docker-compose.yml -p ssafee up -d
```
   - `ssafy.coffee`
   - `jenkins.ssafy.coffee`
사용되는 도메인은 위와 같다.

1. 개발 서버를 포함하여 서버 컨테이너 시작 (extends dev.ssafy.coffee) (option)
```sh
docker compose -p ssafee up -d
```
   - `ssafy.coffee`
   - `dev.ssafy.coffee`
   - `jenkins.ssafy.coffee`
`docker-compose.override.yml`까지 설정을 적용하려면 위 명령어를 실행한다. 사용되는 도메인은 위와 같다.


1. 배포

## dev

### backend

### frontend

