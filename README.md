# Messenger application

Приложение делается в ходе учебного курса по Java Spring Boot

## Requirements

* Docker и docker compose
* JDK 11

### Как запустить

* Разверните БД, прописав в корне проекта <code>docker compose up</code> или <code>docker-compose up</code> для более ранних версий docker-compose
* Сконфигурируйте среду в IDE под проект
* Запустите проект как отдельные сервисы с помощью тула Services в тулбаре снизу
* А затем запустите приложение через IDE, думаю, она вам обязательно поможет, так как запускать приложения Spring из консоли я пока не умею
* Теперь можно потестить запросы, некоторые из них описаны в <code>./HTTP Requests.http</code>, позже будет оформлен Swagger и документация OpenAPI