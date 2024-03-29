# Messenger application
Приложение разрабатывалось в ходе учебного курса по Java Spring Boot. 
Задача состояла в том, чтобы разработать сервис по спецификации, ознакомиться с ней можете [здесь](TaskDescription/README.md)

## Requirements

* Docker и docker compose
* JDK 11
* mvn 3.2.1 or higher

### Как запустить
* mvn package jib:dockerBuild -am (еще можно добавить -pl <название сервиса> чтобы собрать отдельный модуль)
* В корне проекта разверните контейнеры с учетом необходимых переменных среды командой <code>docker compose up</code> или <code>docker-compose up</code> для более ранних версий docker-compose
* Сконфигурируйте среду в IDE под проект
* Запустите проект в IDE как отдельные сервисы с помощью инструмента Services в тулбаре. Можно сбилдить в jar файлы самостоятельно, но я такой цели не преследовал, тут уж сами

### Тестирование

Теперь можно потестить запросы. Все порты сервисов, смаппленные на порты хоста описаны в docker-compose.yml, можно поиграться с ними.  
По пути <service>/swagger-ui/index.html можно получить документацию сервисов
В дальнейшем планируется вывести общую Swagger-документацию именно сюда, собрав её из других сервисов с помощью swagger-aggregator модуля
Но сейчас это не приоритетная задача. 

По пути localhost:*порт_приложения*/swagger-ui/index.html можно перейти в спецификацию конкретного сервиса. 
В каждом сервисе используется авторизация по JWT токену, токен можно получить в UserService(app-users), зарегистрировав пользователя.

### Примерная схема работы сервиса

![Services-schema](TaskDescription/img/services-schema.jpg)
