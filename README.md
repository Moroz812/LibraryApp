# LibraryApp - Онлайн-библиотека книг

## Описание проекта
REST API для управления онлайн-библиотекой книг. Проект разработан в рамках курса "Программирование на языке Java" (25 уроков).
Темы:
https://docs.google.com/document/d/1hHPyeTr2MIghouWxQdbwCDegzzV5D_sfzxnOswU4Pz8/edit?usp=sharing
#5 Онлайн-библиотека / каталог книг

**Преподаватель:** Авдюхова Любовь  
**Студент:** Макаров Сергей Валерьевич  
**Даты разработки:** 4-11.12.2025 года

## Технологический стек
- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (in-memory)
- Maven
- Swagger/OpenAPI 3.0

## Функциональность

### Сущности
- **Book** (Книга) - название, автор, жанр, год издания, ISBN, описание, рейтинг
- **Author** (Автор) - имя
- **Genre** (Жанр) - название, описание
- **Review** (Отзыв) - рецензент, оценка (1-5), комментарий, дата

### Бизнес-логика
- Уникальность названия книги
- Один пользователь = один отзыв на книгу
- Автоматический пересчет среднего рейтинга книги
- Нельзя удалить автора/жанр, если у них есть книги
- Валидация данных (ISBN формат, год публикации, рейтинг 1-5)

### API возможности
- Полный CRUD для всех сущностей
- Поиск книг по названию (регистронезависимый)
- Фильтрация по автору, жанру, году, рейтингу
- Пагинация с сортировкой
- Топ книг по рейтингу
- Получение книг автора/жанра

## Установка и запуск

### Требования
- JDK 17 или выше
- Maven 3.6+
- Postman (для тестирования)

### Запуск проекта
```bash
# Клонировать репозиторий
git clone git@github.com:Moroz812/LibraryApp.git

# Перейти в директорию проекта
cd LibraryApp

# Собрать проект
mvn clean install

# Запустить приложение
mvn spring-boot:run

Приложение будет доступно по адресу: http://localhost:8080
Документация API
Swagger UI

После запуска документация доступна по адресу:
http://localhost:8080/swagger-ui.html

Основные эндпоинты:

Книги
GET    /api/books                    - все книги
GET    /api/books/{id}               - книга по ID
POST   /api/books                    - создать книгу
PUT    /api/books/{id}               - обновить книгу
DELETE /api/books/{id}               - удалить книгу
GET    /api/books/search?query=      - поиск по названию
GET    /api/books/author/{id}        - книги автора
GET    /api/books/genre/{id}         - книги жанра
GET    /api/books/top-rated?limit=   - топ книг
GET    /api/books/paginated          - пагинация
GET    /api/books/filter             - фильтрация

Авторы
GET    /api/authors                  - все авторы
GET    /api/authors/{id}             - автор по ID
POST   /api/authors                  - создать автора
PUT    /api/authors/{id}             - обновить автора
DELETE /api/authors/{id}             - удалить автора

Жанры
GET    /api/genres                   - все жанры
GET    /api/genres/{id}              - жанр по ID
POST   /api/genres                   - создать жанр
PUT    /api/genres/{id}              - обновить жанр
DELETE /api/genres/{id}              - удалить жанр

Отзывы
GET    /api/books/{bookId}/reviews   - отзывы книги
POST   /api/books/{bookId}/reviews   - добавить отзыв

Тестовые данные
Для заполнения базы тестовыми данными:
POST   /api/test-data/fill           - заполнить тестовыми данными
GET    /api/test-data/status         - статус базы данных
POST   /api/test-data/clear          - очистить все данные

Примеры запросов
Создание автора
POST /api/authors
Content-Type: application/json

{
  "name": "Лев Толстой"
}

Создание книги
POST /api/books
Content-Type: application/json

{
  "title": "Война и мир",
  "authorId": 1,
  "genreId": 1,
  "publicationYear": 1869,
  "isbn": "978-5-17-090194-4",
  "description": "Роман-эпопея",
  "pageCount": 1225
}

Добавление отзыва
POST /api/books/1/reviews
Content-Type: application/json

{
  "reviewerName": "Иван Иванов",
  "rating": 5,
  "comment": "Отличная книга!"
}

Структура проекта
src/main/java/com/example/libraryapp/
├── LibraryApplication.java          # Главный класс
├── controller/                      # Контроллеры
│   ├── BookController.java
│   ├── AuthorController.java
│   ├── GenreController.java
│   ├── ReviewController.java
│   └── DataInitializerController.java
├── entity/                          # Сущности JPA
│   ├── Book.java
│   ├── Author.java
│   ├── Genre.java
│   └── Review.java
├── dto/                            # Data Transfer Objects
│   ├── BookRequest.java / BookResponse.java
│   ├── AuthorRequest.java / AuthorResponse.java
│   ├── GenreRequest.java / GenreResponse.java
│   ├── ReviewRequest.java / ReviewResponse.java
│   └── ErrorResponse.java
├── repository/                     # Репозитории Spring Data JPA
│   ├── BookRepository.java
│   ├── AuthorRepository.java
│   ├── GenreRepository.java
│   └── ReviewRepository.java
├── service/                        # Бизнес-логика
│   ├── BookService.java
│   ├── AuthorService.java
│   ├── GenreService.java
│   └── ReviewService.java
├── exception/                      # Кастомные исключения
│   ├── AuthorHasBooksExceptionjava
│   ├── AuthorNotFoundException.java
│   ├── BookAlreadyExistsException.java
│   ├── BookNotFoundException.java
│   ├── DuplicateAuthorException.java
│   ├── DuplicateGenreException.java
│   ├── DuplicateReviewException.java
│   ├── GenreHasBooksException.java
│   ├── GenreNotFoundException.java
│   └── GlobalExceptionHandler.java

Тестирование

Postman коллекция
Файл LibraryApp.postman_collection.json содержит все эндпоинты с примерами.

H2 Console
Встроенная база данных доступна по адресу:
http://localhost:8080/h2-console
Параметры подключения:
    JDBC URL: jdbc:h2:mem:testdb
    Username: sa
    Password: pass

Особенности реализации

Валидация
    ISBN формат (regex паттерн)
    Год публикации (не может быть в будущем)
    Рейтинг отзыва (1-5)
    Уникальность названия книги
    Обязательные поля

Обработка ошибок
    Глобальный обработчик исключений (@RestControllerAdvice)
    Единый формат ответа об ошибке
    HTTP статусы: 400, 404, 409, 500

Безопасность
    Проверка существования связанных сущностей
    Каскадное удаление отзывов при удалении книги
    Защита от дублирования данных

Что можно улучшить

Дальнейшее развитие
    Аутентификация и авторизация (Spring Security)
    Роли пользователей (админ, модератор, пользователь)
    Загрузка обложек книг (файлы/изображения)
    Избранное/закладки для пользователей
    Экспорт данных в Excel/PDF
    Уведомления о новых книгах/отзывах
    Фронтенд на React/Vue.js
    Миграция на PostgreSQL для production

Оптимизация
    Кэширование часто запрашиваемых данных
    Индексация полей для поиска
    Пагинация на уровне базы данных
    Асинхронная обработка запросов

Лицензия
Это учебный проект, разработанный в рамках курса обучения.
