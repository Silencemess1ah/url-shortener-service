# Url shortener service on Java with Spring Boot

## Used technologies: 
- Java 17
- Spring Boot
- PostgreSQL
- Liquibase
- TestContainers 
- Redis as cache
- Docker containers
- Gradle builder
- Project Lombok
- JUnit with Mockito & Integration tests

## Created [CI](https://github.com/Silencemess1ah/url-shortener-service/blob/master/.github/workflows/ci.yml) for build test and [checkstyle](https://github.com/Silencemess1ah/url-shortener-service/blob/master/src/main/resources/checkstyle/checkstyle.xml) tests

## How to use service:
- To use this service:
```bash
git clone https://github.com/Silencemess1ah/url-shortener-service
cd url_shortener_service
docker-compose build 
docker-compose up -d
```
## How to work with api examples:
Create a short url from yours given long url:
```http
POST http://localhost:8088/v1/urls/
Content-Type: application/json
{
    "url": "https://superuser.com/questions/1474874/intellij-idea-runs-git-log-command-in-the-background-causing-high-cpu-usage"
}
```
Answer would be like : 
```
http://localhost:8088/v1/urls/xCv4a
```
For redirect use given url from above answer:
```http
GET http://localhost:8088/v1/urls/xCv4a
```
Answer for GET request would be a redirect on original source:
```
https://superuser.com/questions/1474874/intellij-idea-runs-git-log-command-in-the-background-causing-high-cpu-usage
```
