# Retrofit автотесты
- Небольшой проект с end2end api автотестами
- Используемые библиотеки: Retrofit, JUnit5, assertJ (soft asserts), lombok, jackson, faker, instancio
- Если у Вас интересный продукт и Вы ищете AQA - мои <a href="https://t.me/TommyBahama" rel="noopener noreferrer" class="link">telegram контакт</a> и <a href="mailto:Obvintsev.Aleksey@yandex.ru" rel="noopener noreferrer" class="link">почта</a>
<hr>

### Общее описание
Перед использованием необходимо локально поднять api - ссылка на проект.
- Автотесты расположены в DigitalStoreTests.
- Настройку Retrofit для контроллеров смотри в DigitalStoreTests (метод с аннотацией @BeforeEach) и в BaseTest (настройка без авторизации).
- В пакетах model/interface расположены dto/интерфейсы для выполнения/обработки запросов/ответов.
- Подготовку данных для теста смотри в DataUtil.
- В ICheckResponse расположены методы для проверки api запросов.

### Локальный запуск тестов
```
mvn clean test
```
<hr>

### Покрытые контроллеры
```
Более полное описание смотри в - ссылка на проект.
- artist-controller
- genre-controller
- media-type-controller  
- album-controller 
- track-controller 
- employee-controller  
- customer-controller  
- invoice-controller  
- invoice-line-controller  
```