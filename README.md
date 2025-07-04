# Git Convention : GitHub Flow 🌐

- **브랜치 종류** : `main`, `develop`, `feature`
- **MVP 개발 완료 전까지**는 `develop` 브랜치를 사용하지 않고, `main`과 `feature` 브랜치만 유지합니다.




### 1. main

- **Production 환경**에 언제 배포해도 문제없는 stable branch입니다.
- 장애 혹은 버그 발생 시 **main 브랜치**를 기준으로 빠르게 수정합니다.
- Initial commit을 제외하고, **main 브랜치에 직접 커밋하지 않습니다.**

### 2. develop

- 새로운 기능 개발 시 **main을 기준**으로 develop 브랜치를 생성합니다.
- **feature 브랜치**들을 병합하는 곳입니다.
- 모든 기능이 통합되고 버그가 수정된 후 **main 브랜치로 PR**을 생성합니다.
- main 브랜치는 항상 안정적이어야 하며, **불완전한 작업은 develop에서 처리**합니다.

### 3. feature

- **이슈 기반**으로 브랜치를 생성하며, 브랜치명은 반드시 `feature/{이슈번호}-{기능명}` 형식을 따릅니다.
예) `feature/1-user-authentication`
- **develop 브랜치**를 기준으로 새로운 기능을 개발합니다.
- 기능에 대한 버그 수정은 해당 feature 브랜치 내에서 완료 후 **develop으로 PR**을 생성합니다.




# Git Convention : Commit 📝

커밋 메시지는 **제목, 본문, 꼬리말** 세 부분으로 나누며, 각 부분은 빈 줄로 구분합니다.

### 제목

- `Tag: Title` 형식을 사용합니다.
- Tag 첫 글자는 **대문자**, 콜론 바로 뒤에 한 칸 띄우고 제목 작성
예: `Feat: 로그인 기능 추가`

### Tag 종류

| Tag | 설명 |
| --- | --- |
| Feat | 새로운 기능 추가 |
| Fix | 버그 수정 |
| Docs | 문서 수정 |
| Style | 코드 포맷/스타일 변경 |
| Refactor | 코드 리팩토링 |
| Test | 테스트 코드 추가/수정 |
| Chore | 빌드/설정/패키지 관련 작업 |
| Merge | 브랜치 병합 |

### 본문

- **72자 내로 줄바꿈**하여 작성합니다.
- 무엇을, 왜 변경했는지 **상세히 설명**합니다.

### 꼬리말

- `Type: #이슈번호` 형식으로 작성 (필수 아님)
예: `Fixes: #12`




# Code Convention 💻 (Spring Boot / Java 기준)

### 클래스명

- **PascalCase** 사용
예: `UserService`, `OrderController`

### 메서드명

- **camelCase** 사용
예: `getUserById()`, `calculateTotalPrice()`

### 변수명

- **camelCase** 사용
예: `userName`, `orderList`

### 상수명

- **UPPER_SNAKE_CASE** 사용
예: `MAX_LOGIN_ATTEMPTS`, `DEFAULT_TIMEOUT`

### 패키지명

- **모두 소문자**, 도메인 기반으로 구성
예: `com.example.project.service`, `com.example.project.controller`

### 파일명

- **클래스명과 동일하게 PascalCase + .java**
예: `UserService.java`, `OrderController.java`

### 예외 처리 (Custom Exception)

- `RuntimeException` 또는 `Exception` 상속
- 클래스명은 **PascalCase + Exception** 접미사 사용
예: `UserNotFoundException`, `InvalidInputException`

### 예외 클래스 예시

```java
public class UserNotFoundException extends RuntimeException {
    private final String errorCode;
    private final int statusCode;
    private final String reason;

    public UserNotFoundException(String reason) {
        super(reason);
        this.errorCode = "U001";
        this.statusCode = 404;
        this.reason = reason;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReason() {
        return reason;
    }
}
```




# Database 🗄️ (MySQL 기준)

- 테이블명과 컬럼명은 **snake_case** 사용
- PK 컬럼명은 **`{table명}_id`** 형식 또는 간략화 가능
    
    예) **`user_oauth`** 테이블의 PK 컬럼명 **`user_oauth_id`**
    
- 모든 테이블에 **`created_at`**, **`updated_at`** 컬럼 포함 (TIMESTAMP(6), 기본값 및 on update 설정)
- 정규화 원칙 준수 권장
- 파일(이미지 등)은 DB에 직접 저장하지 않고 URL 또는 UUID로 관리

# Import / Export (Java 기준)

- 패키지별로 클래스를 분리하여 관리
- Spring Boot에서는 **`@Component`**, **`@Service`**, **`@Repository`**, **`@Controller`** 등 어노테이션으로 의존성 주입 사용
- 클래스 간 의존성은 인터페이스와 구현체를 분리하여 관리 권장

# Project Architecture 🏗️ (Spring Boot 기준)

```
src/main/java/com/example/project/
├── controller      // REST API 요청 처리, @RestController 사용
├── service         // 비즈니스 로직 처리, @Service 사용
├── repository      // DB 연동, JpaRepository 인터페이스 구현
├── domain          // 엔티티 클래스 (JPA Entity)
├── dto             // 데이터 전송 객체 (Request/Response)
├── exception       // 커스텀 예외 클래스
├── config          // 설정 관련 클래스
└── util            // 유틸리티 클래스
```

### 각 계층 역할

- **Controller**
    
    클라이언트 요청을 받고, 서비스에 위임 후 응답 반환
    
    예외 발생 시 **`@ControllerAdvice`**로 처리
    
- **Service**
    
    핵심 비즈니스 로직 구현, 트랜잭션 관리
    
- **Repository**
    
    데이터베이스 접근, JPA/Hibernate 사용
    
- **Domain(Entity)**
    
    DB 테이블과 매핑되는 클래스
    
- **DTO**
    
    외부와 주고받는 데이터 구조 정의
