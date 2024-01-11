# app

개요

## Table of Contents

- [app](#app)
  - [Table of Contents](#table-of-contents)
  - [Contributing](#contributing)
  - [Development Convention](#development-convention)
    - [Git Flow](#git-flow)
    - [Commit Message Convention](#commit-message-convention)
    - [앗! 깃 명령어 뭐였지?](#앗-깃-명령어-뭐였지)
    - [Code Review](#code-review)
    - [Coding Style](#coding-style)
  - [Installation](#installation)
  - [Usage](#usage)
  - [Features](#features)

## Contributing

팀원 목록

## Development Convention

개발 컨벤션

### Git Flow

브랜치 전략

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

### Commit Message Convention

커밋 메시지 구조는 다음과 같습니다.
```
type: <subject>

[body]

[footer]
```

- type
  - **feat**: 기능 추가
  - **fix**: 버그 수정
  - **refactor**: 코드 리팩토링
  - **design**: CSS 등 디자인
  - **style**: 코드 스타일
  - **test**: 테스트 코드
  - **docs**: 문서 수정
  - **chore**: 빌드 등 기타 변경사항
  - **rename**: 파일명 수정
  - **remove**: 파일 삭제

- subject
  1. 최대 50글자까지 작성
  2. 특수기호 사용 금지 (마침표 등)
  3. 영문 작성시 첫 단어 명령문, 첫 글자 대문자
  4. 한글 작성시 개조식으로 작성

- body
  1. 생략 가능
  2. '어떻게'보다는 **'무엇을', '왜'** 변경했는지 작성

- footer
  1. 생략 가능
  2. Github 연동
  3. Fixes #<이슈번호>
  4. Resolves #<이슈번호>
  5. Ref #<이슈번호>
  6. Related to #<이슈번호>
  7. e.g. `Fixes: #47`, `Related to: #32, 21`

### 앗! 깃 명령어 뭐였지?

- branch
  - `git branch <브랜치명>`: 생성
  - `git branch -d <브랜치명>`: 삭제
  - `git switch <브랜치명>`: 이동
  - `git switch -c <브랜치명>`: 생성 & 이동
- commit 취소 (아직 push 안함)
  - `git reset --soft HEAD~1`: 변경사항의 staged 상태 유지
  - `git reset --mixed HEAD~1`: 변경사항의 staged 상태 해제
  - `git reset --hard HEAD~1`: 변경사항 제거
- commit 취소 (이미 push 함)
  - `git revert <커밋해시>`
- git add 취소
  - `git restore --staged [파일명]`: 파일명은 생략 가능
- 변경사항 제거
  - `git restore [파일명]`
- 변경사항 보기
  - `git diff [파일명]`: staged에서 unstaged 변경사항 비교
  - `git diff --staged [파일명]`: 현재 커밋에서 staged 변경사항 비교

### Code Review

모든 코드 변경 사항은 병합하기 전에 최소한 한 명의 팀 구성원이 검토합니다.

### Coding Style

TODO

## Installation

설치 방법

## Usage

사용 방법

## Features

핵심 기능 나열 및 설명
