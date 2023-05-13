# README

Futuristic RPG game with uber-majestic-iperbombastic graphics!

Main goal is to have an ecosystem where I can experiment some "AI".

## Prerequisites

- JDK 11
- Maven

```
$env:M2_HOME = 'C:\a\software\apache-maven-3.8.7-java11'
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-11.0.16.1'
$env:PATH = $env:M2_HOME + '\bin;' + $env:JAVA_HOME + '\bin;' + $env:PATH
```

## Compile

```bash
mvn clean package
```

## Run

```bash
java -jar "-Dspring.profiles.active=run" ./target/cosucce-0.0.1-SNAPSHOT.jar

$env:spring_profiles_active="run"
./mvnw spring-boot:run
```

