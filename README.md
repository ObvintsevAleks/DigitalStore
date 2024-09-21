# Chinook-REST
<h4> Small personal project regarding a RESTful API for the popular digital media sample database "Chinook"
<br>
<h4> This small project is being built in Java, making use of Spring Boot tools from the Spring Framework.
<br>
<hr>
<h3>Database Name: Chinook</h3>
<h3> Database Engine: PostgreSQL </h3>

<img width="674" alt="chinook-datamodel" src="https://github.com/gchang110101/Chinook-REST/assets/111550683/1c6b21b8-0b76-416a-a477-86bbd4489498">
<hr>
<h3><u> Credit (create db script): </u></h3>
Provider repository:
<a href= "https://github.com/lerocha/chinook-database">chinook-database<a/>
<br>
<br>
Referenced (and found it on):
<a href= "https://github.com/morenoh149/postgresDBSamples">postgresDBSamples<a/>
<hr>
<h3>TODO ёпта: </h3>

- ui дока на сваггер нормальная ++
- решить проблему с зацикливанием последнего эндпоинта +++
- авторизация (по jwt спиздить с гитхаба) ++
- валидация нормальная переработать
- валидация, добавить при создании ошибку (не найдена вложенная сущность)
- валидация, добавить при создании уникальных сущностей ошибку 
- перевести схему на валидацию а не создание (хибернейт)
- для схемы добавить sql с инсертом данных (спиздить с аналога той же бд)
- отдельно попробовать написать валидлацию для поля email

Доработка контроллеров
- album controller, список альбомов по псевдониму артиста ++
- track controller, get all tracks by artist псевдоним ++
- track controller, get all tracks by artist Id ++
- genre controller, mediaType-controller (уникальность по имени) ++
- /tracks/all-tracks-by-media-type/{id} - не работает нормально ++
- invoice line controller, get all invoice line by trackId;  get all track by invoiceId ++
- invoice controller, get all customers by employeeId; get all employees by customersId ++



# Spring-boot rest-api boilerplate

This project includes : Spring Boot(3.3.1), Spring Data JPA, Spring Validation, Spring Security + JWT Token, PostgreSQL, Mapstruct, Lombok, Swagger (Open API).
If you're hiring for AQA - [my telegramm contact](https://t.me/TommyBahama).

## REST API Endpoints

All inputs and outputs use JSON format.

To open Swagger (interactive) API documentation, navigate your browser to http://localhost:8181/api/swagger-ui/index.html


```
registration-controller
  POST /register - create user, required : String name , String email, String username, String password

login-controller
  POST /login - Login using username: b and password:b
  
artist-controller
  PUT /artists - Update artist
  POST /artists - Create artist
  GET /artists/{id} - Get artist with ID
  DELETE /artists/{id} - Remove artist with ID
  GET /artists/artists-by-pseudonym/{pseudonym} - Get list of artist with same pseudonym
  GET /artists/artists-by-name/{name} - Get list of artist with same name
  
genre-controller
  PUT /genres - Update genre
  POST /genres - Create genre
  GET /genres/{id} - Get genre with ID
  DELETE /genres/{id} - Remove genre with ID
  GET /genres/all - Get all genre

media-type-controller  
  PUT /media-types - Update media-type
  POST /media-types - Create media-type
  GET /media-types/{id} - Get media-type with ID
  DELETE /media-types/{id} - Remove media-type with ID
  GET /media-types/all - Get all media-type

album-controller
  PUT /albums - Update album
  POST /albums - Create album
  GET /albums/{id} - Get album with ID
  DELETE /albums/{id} - Remove album with ID
  GET /albums/albums-by-title/{title} - Get list of albums with identical title
  GET /albums/albums-by-artist-pseudonym/{pseudonym}  - Get list of albums by artist pseudonym
  GET /albums/albums-by-artist-id/{id}  - Get list of albums by artist ID
  
track-controller
  PUT /tracks - Update track
  POST /tracks - Create genres
  GET /tracks/{id} - Get genres with ID
  DELETE /tracks/{id} - Remove genres with ID
  GET /tracks/tracks-by-artist-pseudonym/{pseudonym}
  
  
 

```
