# 처음 배우는 스프링 부트2

```
[처음 배우는 스프링 부트2 - 김영재 저] 책을 실습하고 정리한 내용입니다.
저작권 문제 시, 연락 부탁드립니다. 
``` 

해당 책은 커뮤니티 게시판을 구현하며 배우는 스프링부트2를 알아본다. 회사에서 스프링부트로 실무를 하고 있지만, 좀더 깊게 내용을 알고 싶어서 책을 구매했고 개인적으로 노트하기 위해 실습내용을 정리합니다 :smile:

## 삽질기

- gradle 1.7 과 java 11 은 h2 의존성 주입시 문제가 있다
    - [javax/xml/bind/JAXBException](https://stackoverflow.com/questions/43574426/how-to-resolve-java-lang-noclassdeffounderror-javax-xml-bind-jaxbexception)
    - gradle 1.7의 명령어와, java9 이상일 때 위의 문제점을 해결해봐야 할듯

## 1장. 스프링 부트 입문하기

Spring Boot의 다양한 Starter를 살펴본다. Start는 dependency set 이며 일일이 dependency를 찾지 않고도 모든 필요한 스프링 관련 기술을 도입할 수 있다.

기존의 스프링 프레임워크로 개발환경설정을 하려면 Tomcat, Jetty 등과 같은 서블릿 컨테이너를 설치하고 프로젝트 내에 필요한 환경을 직접 구성해야 했지만 스프링 부트의 각종 starter는 이와 같은 환경설정을 집합해 놓아서 개발자가 비즈니스 로직에 집중할 수 있도록 해주는 것이다.

*스타터의 명명규칙*
```
spring-boot-starter-* 
```

[다양한 Spring Boot Starter](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using.build-systems.starters)


[스프링의 Release Notes를 확인해서 Changes를 트래킹하자](https://github.com/spring-projects/spring-boot/wiki)

## 2장. 스프링 부트 환경설정

[Spring Initializer](https://start.spring.io/) 를 이용해 간편하게 프로젝트를 생성할 수 있다.

> Not War, Just Jar

스프링 부트는 따로 was를 설치하지 않아도 실행할 수 있다

### Gradle

Maven은 pom.xml에 설정 정보를 넣는데, xml 기반으로 작성되어 있어서 동적인 행위에 제약이 있다.

Gradle은 Ant로부터 기본적인 빌드 도구의 기능을, 메이븐으로부터 의존 라이브러리 관리 기능을 차용했다.

멀티 프로젝트를 구성하면 코드 재사용성이 높아지고 통합 프로젝트처럼 관리할 수 있다. module 을 만들고, settings.gradle 에 include로 모듈들을 포함시킨다.

*settings.gradle*
```
rootProject.name = 'board'
include 'demo-web'
include 'demo-domain'
```

멀티 모듈로 구성한 프로젝트 디렉토리
```
├─demo-domain
│  └─src
│      ├─main
│      │  ├─java
│      │  └─resources
│      │      ├─static
│      │      └─templates
│      └─test
│          ├─java
│          └─resources
├─demo-web
│  └─src
│      ├─main
│      │  ├─java
│      │  │  └─com
│      │  │      └─board
│      │  │          ├─controller
│      │  │          ├─domain
│      │  │          │  └─enums
│      │  │          ├─repository
│      │  │          └─service
│      │  └─resources
│      │      ├─static
│      │      │  ├─css
│      │      │  ├─images
│      │      │  └─js
│      │      └─templates
│      │          └─board
│      └─test
│          ├─java
│          └─resources
```

### properties

application-{zone}.yml 로 dev, qa, prod 별 환경설정

스프링 부트 프로젝트는 jar 파일로 빌드해서 서버에서 직접 간단한 명령어로 profile을 실행할 수 있음
```
$ java -jar ... -D spring.profiles.active=dev
```

### 값 매핑

`@Value`, `@ConfigurationProperties` 를 사용해서 프로퍼티값을 매핑할 수 있다.

- [ ] [포스팅](https://jojoldu.tistory.com/123) 참고해서 도메인 모듈로 분리해보기

### 자동환경 설정

`@EnableAutoCongfiguration`, `@SpringBootApplication` 둘 중에 하나만 설정되 어있어도 스프링 부트 자동설정이 가능하다. 의존성이 클래스 경로에 존재한다면 자동으로 해당 빈에 접근한다.

(예를들어, mongo 의존성이 있을 경우, mongoTemplate 과 같은 빈에 접근할 수 있다)

[스프링의 각종 properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties)
각 의존성의 spring.factories 와 spring-configuration-metadata.json 을 확인하면 어떤 프로퍼티 값을 수정해야 하는 지 볼 수 있다.

## 3장. 스프링 부트 테스트

스프링부트는 각종 테스트를 위한 어노티에이션을 제공한다.

- `@SpringBootTest` : 통합테스트
- `@WebMvcTest` : MVC를 위한 테스트
- `@DataJpaTest` : JPA 관련 테스트 (데이터소스의 설정이 정상인지, CRUD 정상인지)
- `@RestClientTest` : json 형식이 예상대로 응답을 반환하는지 테스트
- `@JsonTest` : json의 직렬화와 역직렬화를 수행하는 라이브러리인 Gson과 Jackson API의 테스트를 제공한다.
- `@DataMongoTest`

- [ ] 단위 테스트를 꼼꼼히 해야겠다. 그리고 @DataMongoTest 사용해보자

## 4장. 스프링 부트 웹

MVC 패턴 & Thymeleaf 를 사용해서 게시판 사이트를 만들어본다

### JPA 도메인 매핑

``` java
@Getter
@NoArgsConstructor
@Entity
@Table
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String title;

    @Column
    private String subTitle;

    @Column
    private String content;

    @Column
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

}
```

- `@GeneratedValue(strategy = GenerationType.IDENTITY)` : 기본 키 자동 할당
- `@Enumerated(EnumType.STRING)` : 실제로 자바 enum 형이지만 DB의 String 형으로 변환하여 저장
- `@OneToOne(fetch = FetchType.LAZY)` : Board 와 User 도메인을 1:1 관계로 설정
  - FetchType.EAGER : Board 도메인을 조회할 때 즉시 User 객체 함께 조회
  - FetchType.LAZY : User 도메인을 실제로 사용할 때 조회

### @Test 코드 선작성 , 필요 리소스 후작성

책에서는 도메인 객체를 정의하고 save, find 하는 @Test 메서드를 먼저 작성 한뒤, 빨간색으로 에러가 나면 그 클래스들을 `Maske UserRepository` `Create interface UserRepository` 등을 클릭해서 class를 만들었다.

TDD 개념으로 테스트를 먼저 작성하고 그에 해당하는 클래스를 후 작성하는 패턴인 것 같다

`CommandLineRunner` 를 수행해서 초기 데이터를 미리 설정할 수 있다

[ThymeLeaf 문법](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)

## 5장. 스프링 부트 시큐리티 + OAuth2

## 6장. 스프링 부트 데이터 Rest

## 7장. 스프링 부트 배치
