# docker

<https://docs.docker.com/engine/install/ubuntu/#install-using-the-repository>

### docker compose

[docker-compose.yml](docker-compose.yml)

# certbot

### 인증서 발급 (certonly)

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

### 인증서 갱신 (renew)

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

```sh
docker start certbot-renew
```

### refs

1. <https://eff-certbot.readthedocs.io/en/latest/install.html#alternative-1-docker>
2. <https://certbot-dns-cloudflare.readthedocs.io/en/stable/>
3. <https://github.com/certbot/certbot/blob/master/certbot-nginx/certbot_nginx/_internal/tls_configs/options-ssl-nginx.conf>

# jenkins

### DooD

- `998`: host docker gid (`DOCKER_GID=$(getent group docker | cut -d: -f3)`)

### gitlab 연동

1. `freestyle` vs `pipeline`: `pipeline`
2. `integration` vs `webhook`: `integration`

### pipeline script

[Jenkinsfile](Jenkinsfile)

### refs

1. <https://docs.gitlab.com/ee/integration/jenkins.html>
2. <https://plugins.jenkins.io/gitlab-plugin/>
3. <https://github.com/jenkinsci/gitlab-plugin#scripted-pipeline-jobs>
4. <https://crispyblog.kr/development/common/10>
5. <https://www.jenkins.io/doc/book/system-administration/reverse-proxy-configuration-with-jenkins/reverse-proxy-configuration-nginx/>
