# Spring-boot rest-api boilerplate
<h4> ��������� ���-������ rest-api </h4>
<h4>��������: Spring Boot, Spring Data JPA, Spring Validation, Spring Security (������������ JWT Token), Mapstruct, Lombok, Swagger.</h4>
<hr>
<h3> ���� � ��� ���������� ������� � �� ����� AQA - ��� ������� ��� ����� <a href= "https://t.me/TommyBahama">telegram<a/>  </h3> 

<hr>
<h3>
<li> Db Name: Digital store </li>
<br>
<li> Db Engine: PostgreSQL </li>
</h3>

<h3><p style="text-align: center;">ER - diagram</p>
 
<img width="1075" alt="Digital store"  src="src/main/resources/pictures/db_er.png" height="1171" title="">

<hr>

## Rest-api endpoints

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

<hr>

<h3>TODO ����: </h3>

- ui ���� �� ������� ���������� ++
- ������ �������� � ������������� ���������� ��������� +++
- ����������� (�� jwt �������� � �������) ++
- ������������ https://chartdb.io/ � ���������� �������� ��������� � �� ++

<h3>��������� ������������</h3>
- album controller, ������ �������� �� ���������� ������� ++
- track controller, get all tracks by artist ��������� ++
- track controller, get all tracks by artist Id ++
- genre controller, mediaType-controller (������������ �� �����) ++
- /tracks/all-tracks-by-media-type/{id} - �� �������� ��������� ++
- invoice line controller, get all invoice line by trackId;  get all track by invoiceId ++
- invoice controller, get all customers by employeeId; get all employees by customersId ++

<h3>���� � ���������� (���� �� ����� ������ �������)</h3>
- ��������� ���������� ������������ (500 �� ������, �� ������ 401 ������� ������������� ���� �� advice)
- ���������, �������� ��� �������� ������ (�� ������� ��������� ��������)
- ���������, �������� ��� �������� ���������� ��������� ������
- ��������� ��� ���� email (��������� spring ���������)

<h3>���� � ��</h3>
- ��������� ����� �� ��������� � �� �������� (���������, ���� �� ����� ������ �������)
- ��� ����� �������� sql � �������� ������ (���� �� ����� ������ �������, ������ ����� �� �������� ��� �� �� - ��� ���� �������� - ������ digital store)

