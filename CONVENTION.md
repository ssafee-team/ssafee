# Convention

## Git

### Branch

- 주요 브랜치 (항상 유지)
  - **main**
    - 항상 배포 가능한 브랜치
  - **develop**
    - 배포 브랜치(main)의 다음 버전을 준비하는 브랜치
    - 개발된 모든 코드의 통합 장소
- 보조 브랜치 (임시 생성/머지했으면 삭제)
  - **feature**
    - 특정 기능을 개발하는 브랜치
    - develop 브랜치에서 생성, 기능 개발 후 develop 브랜치로 병합
    - e.g. `feature/backend-login`, `feature/frontend-login`
  - **release**
    - 배포 브랜치의 다음 버전을 갱신하기 직전에 QA를 수행하는 브랜치
    - develop 브랜치에서 생성, 테스트 및 버그 수정 후 main 및 develop 브랜치로 병합
    - e.g. `release/0.0.1`, `release/1.0-beta`
  - **hotfix**
    - 배포 브랜치의 현재 버전에서 버그가 생겼을 때 긴급 수정을 위한 브랜치
    - main 브랜치에서 생성, 버그 수정 후 main 및 develop 브랜치로 병합
    - e.g. `hotfix/20240111`, `hotfix/#5`


### Commit

개행은 반드시 2번씩

```
type: <subject>

[body]

[footer]
```

- type
  - `feat`: 기능 추가
  - `fix`: 버그 수정
  - `refactor`: 코드 리팩토링
  - `design`: CSS 등 디자인
  - `style`: 코드 스타일 수정
  - `test`: 코드 테스트 관련
  - `docs`: 문서 수정
  - `chore`: 빌드 등 기타 변경사항
  - `rename`: 파일명 수정
  - `remove`: 파일 삭제

- \<subject\>
  1. 최대 50글자까지 작성
  2. 특수기호 사용 금지 (마침표 등)
  3. 영문 작성시 첫 단어 명령문, 첫 글자 대문자
     - e.g. `Add file`
  4. 한글 작성시 개조식으로 작성
     - e.g. `파일 추가`

- \<body\>
  1. 생략 가능
  2. '어떻게'보다는 '무엇을', '왜' 변경했는지 작성

- \<footer>
  1. 생략 가능
  2. GitHub 이슈번호 연동
  3. `Fixes #<이슈번호>`
  4. `Resolves #<이슈번호>`
  5. `Ref #<이슈번호>`
  6. `Related to #<이슈번호>`
  7. e.g. `Fixes: #47`, `Related to: #32, 21`
