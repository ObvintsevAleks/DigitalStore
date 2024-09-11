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
- авторизация (по jwt спиздить с гитхаба)
- валидация нормальная переработать
- валидация, добавить при создании ошибку (не найдена вложенная сущность)
- перевести схему на валидацию а не создание (хибернейт)
- для схемы добавить sql с инсертом данных (спиздить с аналога той же бд)
- отдельно попробовать написать валидлацию для поля email

Доработка контроллеров
- album controller, список альбомов по псевдониму артиста
- album controller, список альбомов по фамилии и имени артиста
- genre controller, mediaType-controller (уникальность по имени)
- /tracks/all-tracks-by-media-type/{id} - не работает нормально
- track controller, get all tracks by artist Id
- invoice line controller, get all invoice line by trackId;  get all track by invoiceId 
- invoice controller, get all customers by employeeId; get all employees by customersId
