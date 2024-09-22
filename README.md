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
<h3>TODO ����: </h3>

- ui ���� �� ������� ���������� ++
- ������ �������� � ������������� ���������� ��������� +++
- ����������� (�� jwt �������� � �������) ++
- ��������� ���������� ������������
- ���������, �������� ��� �������� ������ (�� ������� ��������� ��������)
- ���������, �������� ��� �������� ���������� ��������� ������ 
- ��������� ����� �� ��������� � �� �������� (���������)
- ��� ����� �������� sql � �������� ������ (�������� � ������� ��� �� ��)
- �������� ����������� �������� ���������� ��� ���� email (����� ������������ ���������)

��������� ������������
- album controller, ������ �������� �� ���������� ������� ++
- track controller, get all tracks by artist ��������� ++
- track controller, get all tracks by artist Id ++
- genre controller, mediaType-controller (������������ �� �����) ++
- /tracks/all-tracks-by-media-type/{id} - �� �������� ��������� ++
- invoice line controller, get all invoice line by trackId;  get all track by invoiceId ++
- invoice controller, get all customers by employeeId; get all employees by customersId ++



# Spring-boot rest-api boilerplate

������ ��������: Spring Boot(3.3.1), Spring Data JPA, Spring Validation, Spring Security + JWT Token, PostgreSQL, Mapstruct, Lombok, Swagger (Open API).

���� �� ���������� � ������ AQA - [��� ������� � ���������](https://t.me/TommyBahama).

## REST API Endpoints

Swagger ������������ - http://localhost:8181/api/swagger-ui/index.html


```
registration-controller
  POST /register - ������� ������������

login-controller
  POST /login - �������������� �� ������������
  
artist-controller
  PUT /artists - �������� �������
  POST /artists - �������� �������
  GET /artists/{id} - �������� ������� �� ��������������
  DELETE /artists/{id} - �������� �������
  GET /artists/artists-by-pseudonym/{pseudonym} - �������� ������ �������� �� ����������
  GET /artists/artists-by-name/{name} - �������� ������ �������� �� �����
  
genre-controller
  PUT /genres - �������� ����
  POST /genres - ������� ����
  GET /genres/{id} - �������� ���� �� ��������������
  DELETE /genres/{id} - ������� ����
  GET /genres/all - �������� ��� �����

media-type-controller  
  PUT /media-types - �������� �����-���
  POST /media-types - ������� �����-���
  GET /media-types/{id} - �������� �����-��� �� ��������������
  DELETE /media-types/{id} - ������� �����-���
  GET /media-types/all - �������� ��� �����-����

album-controller
  PUT /albums - �������� ������
  POST /albums - ������� ������
  GET /albums/{id} - �������� ������ �� ��������������
  DELETE /albums/{id} - ������� ������
  GET /albums/albums-by-title/{title} - �������� ������ �������� �� ���������
  GET /albums/albums-by-artist-pseudonym/{pseudonym} - �������� ������ �������� �� ���������� �������
  GET /albums/albums-by-artist-id/{artistId} - �������� ������ �������� �� �������������� �������
  
track-controller
  PUT /tracks - �������� �����������
  POST /tracks - ������� �����������
  GET /tracks/{id} - �������� ����������� �� ��������������
  DELETE /tracks/{id} - ������� �����������
  GET /tracks/tracks-by-artist-pseudonym/{pseudonym} - �������� ��� ����������� �� ���������� �������
  GET /tracks/tracks-by-artist-id/{id} - �������� ��� ����������� �� �������������� �������
  GET /tracks/all-tracks-by-media-type/{id} - �������� ��� ����������� �� �������������� �����-����
  GET /tracks/all-tracks-by-genre/{id} - �������� ��� ����������� �� �������������� �����
  GET /tracks/all-tracks-by-album/{id} - �������� ��� ����������� �� �������������� �������
  
employee-controller  
  PUT /employees - �������� ���������
  POST /employees - ������� ���������
  GET /employees/{id} - �������� ��������� �� ��������������
  DELETE /employees/{id} - ������� ���������
  GET /employees/lastname/{name} - �������� ������ ���������� �� �������
  GET /employees/firstname/{name} - �������� ������ ���������� �� �����
  
customer-controller  
  PUT /customers - �������� �������
  POST /customers - ������� �������
  GET /customers/{id} -�������� ������� �� ��������������
  DELETE /customers/{id} - ������� �������
  GET /customers/lastname/{name} - �������� ������ �������� �� �������
  GET /customers/firstname/{name} - �������� ������ �������� �� �����
  
invoice-controller  
  PUT /invoices - �������� �����
  POST /invoices - ������� �����
  GET /invoices/{id} - �������� ����� �� ��������������
  DELETE /invoices/{id} - ������� �����
  GET /invoices/invoices-by-employee/{id} - �������� ����� �� �������������� ����������
  GET /invoices/invoices-by-customer/{id} - �������� ����� �� �������������� �������
  
invoice-line-controller  
  PUT /invoice-lines - �������� �������������� �����
  POST /invoice-lines - ������� �������������� �����
  GET /invoice-lines/{id} - �������� �������������� ����� �� ��������������
  DELETE /invoice-lines/{id} - ������� �������������� �����
  GET /invoice-lines/invoice-lines-by-track/{id} - �������� ������ �������������� ������� �� �������������� �����������
  GET /invoice-lines/invoice-line-by-invoice/{id} - �������� ������ �������������� ������� �� �������������� ������

```
