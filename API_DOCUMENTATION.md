# LibraryApp API Documentation

## Базовый URL
`http://localhost:8080/api`

## Авторы
- `POST /authors` - создать автора
- `GET /authors` - все авторы
- `GET /authors/{id}` - автор по ID
- `PUT /authors/{id}` - обновить автора
- `DELETE /authors/{id}` - удалить автора

## Жанры
- `POST /genres` - создать жанр
- `GET /genres` - все жанры
- `GET /genres/{id}` - жанр по ID
- `PUT /genres/{id}` - обновить жанр
- `DELETE /genres/{id}` - удалить жанр

## Книги
- `POST /books` - создать книгу
- `GET /books` - все книги
- `GET /books/{id}` - книгу по ID
- `PUT /books/{id}` - обновить книгу
- `DELETE /books/{id}` - удалить книгу
- `GET /books/search?query=` - поиск по названию
- `GET /books/author/{id}` - книги автора
- `GET /books/genre/{id}` - книги жанра
- `GET /books/top-rated?limit=` - топ книг по рейтингу
- `GET /books/paginated?page=&size=&sort=` - пагинация
- `GET /books/filter?authorId=&genreId=&minYear=&maxYear=&minRating=` - фильтрация

## Отзывы
- `POST /books/{bookId}/reviews` - добавить отзыв
- `GET /books/{bookId}/reviews` - отзывы книги

## Swagger UI
Документация доступна по адресу: `http://localhost:8080/swagger-ui.html`