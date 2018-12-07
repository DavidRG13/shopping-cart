# Shopping Cart

This project shows how to create a simple shopping cart API using Spring features such as:
* Controllers
* Input validation
* Repository, currently using [H2](http://www.h2database.com/html/main.html) for simplicity
* Unit testing Controllers
* Unit testing Repositories
* Documentation auto generated

## How to run it
As we are using the [Grandle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) and [Spring Boot Gradle Plugin](https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/gradle-plugin/reference/html/), running the application is that simple as executing:
```$bash
./gradlew bootRun
```

## How to build it

```$bash
./gradlew bootJar
```

## How to document it
To generate the documentation we are using [Spring Rest Docs](https://docs.spring.io/spring-restdocs/docs/2.0.2.RELEASE/reference/html5/) and [Spring Auto Rest Docs](https://scacap.github.io/spring-auto-restdocs/).
This allows us to generate the documentation based on tests and javaDoc comments.

As this project is currently not publishing the resulting HTML pages to GitHub pages, we have created a Gradle task that generates the documentation and copy it to `docs` folder, so it can be added to the repository or viewed in a browser easily.
Simply run:
```$bash
./gradlew copyDocs
```

## Known issues
* Documenting LocalDate internal fields instead of the String value that should be submitted.
* No custom error handling, so we are exposing internals