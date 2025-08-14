# 🫛 EatPic

## 프로젝트 설명

**EatPic**은 *사진 한 장으로 편리하게 식사 기록*을 할 수 있는 **식사 기록 & 커뮤니티 서비스**입니다.

사용자는 간단히 식사를 기록하고 **레시피·링크·팁을 공유**하여 서로에게 **유용한 정보**를 제공하며

**뱃지와 미션** 기능을 통해 식사 기록을 습관화할 수 있습니다.

또한 **팔로우 기반 커뮤니티**를 구성하여 건강한 식문화와 기록 루틴을 자연스럽게 형성하도록 돕습니다.

**주요 가치**

- 📸 **가벼운 기록**: 사진 한 장으로 간편하게 식사 기록
- 📚 **정보 공유**: 레시피 / 링크 / 팁 제공
- 🏆 **습관 형성**: 뱃지 & 미션으로 루틴 만들기
- 🤝 **커뮤니티**: 팔로우 관계 기반 소셜 소통

**핵심 키워드**: **`식사 기록`**, **`정보 공유`**, **`루틴 형성`**, **`뱃지`**, **`커뮤니티`**

EatPic Spring은 이러한 모바일 서비스의 **백엔드**를 담당하는 Spring Boot 기반 서버 애플리케이션입니다.

## 🌐 Git Convention : GitHub Flow

- **브랜치 종류** : **`main`**, **`feature`**

### 1. main

- **Production 환경**에 언제 배포해도 문제없는 안정(stable) 브랜치입니다.
- 장애나 긴급 버그 발생 시 **main 브랜치**에서 핫픽스를 진행합니다.
- Initial commit을 제외하고 **main 브랜치에 직접 커밋하지 않으며**, 반드시 Pull Request로만 병합합니다.

### 2. feature

- **이슈 기반**으로 브랜치를 생성하며, 브랜치명은 반드시 **`feature/{이슈번호}-{기능명}`** 형식을 따릅니다.
    
    예: **`feature/1-user-authentication`**
    
- 기능 개발과 관련된 버그 수정은 feature 브랜치 내에서 마무리한 뒤, **main 브랜치로 PR**을 올립니다.

## 📝 Git Convention : Commit

커밋 메시지는 **제목, 본문, 꼬리말** 세 부분으로 나누며, 각 부분은 **빈 줄로 구분**합니다.

### 1. 제목(Title)

- 형식: **`Tag: 제목`**
- Tag의 첫 글자는 **대문자**, 콜론(**`:`**) 뒤 한 칸 띄우고 제목 작성
- 예:
    
    `TextFeat: 로그인 기능 추가`
    

### 2. Tag 종류

| **Tag** | **설명** |
| --- | --- |
| Feat | 새로운 기능 추가 |
| Fix | 버그 수정 |
| Docs | 문서 수정 |
| Style | 코드 포맷/스타일 변경 |
| Refactor | 코드 리팩토링 |
| Test | 테스트 코드 추가/수정 |
| Chore | 빌드/설정/패키지 관련 작업 |
| Merge | 브랜치 병합 |

### 3. 본문(Body)

- **72자 이내**로 줄바꿈하여 작성
- 무엇을, 왜 변경했는지 **구체적으로 설명**
- 예:
    
    `text로그인 API에 JWT 토큰 발급 로직 추가
    - 로그인 성공 시 액세스 토큰과 리프레시 토큰 발급
    - 토큰 유효시간 설정 및 DB 저장 로직 구현`
    

### 4. 꼬리말(Footer)

- 형식: **`Type: #이슈번호`** (필수 아님)
- 예:
    
    `textFixes: #12`
    

## **🚀 서비스 아키텍처 다이어그램**

![image.png](attachment:3f3acf4f-d9f4-4fcd-802d-58fec86a7cf9:image.png)

## 📁 프로젝트 구조

`textsrc/
├── main/
│   └── java/
│       └── EatPic/
│           └── spring/
│               ├── domain/               # 기능별 엔티티 및 비즈니스 로직 모듈
│               │   ├── badge/
│               │   ├── bookmark/
│               │   ├── calendar/
│               │   ├── card/
│               │   ├── comment/
│               │   ├── greetingMessage/
│               │   ├── hashtag/entity/
│               │   ├── notification/
│               │   ├── reaction/
│               │   ├── reportHistory/
│               │   ├── term/entity/
│               │   ├── user/
│               │   └── uuid/
│               ├── global/               # 전역 설정 및 Application 실행 파일
│               │   └── Application.java
├── test/
│   └── java/
│       └── EatPic/
│           └── spring/                    # 테스트 코드
├── .github/                               # CI/CD 및 워크플로우
├── gradle/                                # 빌드 스크립트 관련 파일
├── Dockerfile                             # Docker 환경 설정
├── .gitignore`

## 🗂️ 주요 폴더 설명

- **domain/**: 각 서비스 기능별 엔티티 및 로직 관리 (badge, bookmark, calendar 등)
- **global/**: 전역 설정, 예외 처리, 보안 및 애플리케이션 진입점
- **test/**: 단위 테스트 및 통합 테스트
- **.github/**: GitHub Actions 및 자동화 설정
- **gradle/**: 빌드/의존성 관리
- **Dockerfile**: 컨테이너 배포 환경 설정

## 🗄️ 데이터베이스 설계 규칙

- **Naming Convention**
    - **테이블명 / 컬럼명**: **`snake_case`** 사용
    - **PK 컬럼명**: **`{table명}_id`** 형식
        - 예) **`user_oauth`** 테이블 → PK: **`user_oauth_id`**
- **공통 컬럼 규칙**
    - 모든 테이블에 **`created_at`**, **`updated_at`** 컬럼 포함
    - 타입: **`TIMESTAMP(6)`**
    - **`created_at`**: 기본값 **`CURRENT_TIMESTAMP(6)`**
    - **`updated_at`**: 기본값 **`CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)`**
- **정규화**
    - 가능하면 **3정규형(3NF)** 이상 준수하여 데이터 중복 최소화
- **파일 저장 원칙**
    - 이미지/파일을 DB에 직접 저장하지 않음
    - 대신 **URL** 또는 **UUID**로 경로·식별자를 관리
    

## 👤 EatPic Spring

| <img src="https://avatars.githubusercontent.com/u/145183497?v=4" width="150" height="150"/> | <img src="https://avatars.githubusercontent.com/u/186535028?v=4" width="150" height="150"/> | <img src=”https://avatars.githubusercontent.com/u/154819055?v=4" width="150" height="150"/> | <img src="https://github.com/ye-zin" width="150" height="150"/> |
| --- | --- | --- | --- |
| 김이안<br/>[@2anizirong](https://github.com/2anizirong) | 김준호<br/>[@kjhh2605](https://github.com/kjhh2605) | 윤해민<br/>[@hamtorygoals](https://github.com/hamtorygoals) | 박예진<br/>[@ye-zin](https://github.com/ye-zin) |
