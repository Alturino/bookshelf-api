# Book-Shelf-API

Submission Dicoding Belajar Backend Pemula

I get 5 Star

## Authors

- [@onirutlA](https://www.github.com/onirutlA)

## Depedency

- Hapi
- nanoid
- eslint
- prettier (Airbnb Style)

# API Spec

### Add Book

Request:

- Method : POST
- Endpoint : `/books`
- Header :
  - Content-Type : application/json
  - Accept : application/json
- Body :

```json
    {
        "name": "String",
        "year": "String",
        "author": "String",
        "summary": "String",
        "publisher": "String",
        "pageCount": "Integer",
        "readPage": "Integer",
        "reading": "Boolean"
    }
```

Response:

```json
{
    "status": "String",
    "message": "String",
    "data": {
        "bookId": "String"
    }
}
```

### Get All Books

Request:

- Method : GET
- Endpoint : `/books`
- Header :
  - Content-Type : application/json

Response:

```json
{
  "status": "String",
  "data": {
    "books": [
      {
        "id": "String",
        "name": "String",
        "publisher": "String"
      }
    ]
  }
}
```

### Get Detail Book

Request:

- Method : GET
- Endpoint : `/books/{id}`
- Header :
  - Content-Type : application/json

Response:

```json
{
    "status": "String",
    "data": {
        "book": {
            "id": "String",
            "name": "String",
            "year": "Year",
            "author": "String",
            "summary": "String",
            "publisher": "String",
            "pageCount": "Integer",
            "readPage": "Integer",
            "finished": "Boolean",
            "reading": "Boolean",
            "insertedAt": "Date",
            "updatedAt": "Date"
        }
    }
}
```

### Update Book

Request:

- Method : PUT
- Endpoint : `/books/{id}`
- Header :
  - Content-Type : application/json
  - Accept : application/json
- Body :

```json
{
    "name": "String",
    "year": "Integer",
    "author": "String",
    "summary": "String",
    "publisher": "String",
    "pageCount": "Integer",
    "readPage": "Integer",
    "reading": "Boolean"
}
```

Response:

```json
{
  "status": "String",
  "message": "String"
}
```

### Delete Book

Request:

- Method : DELETE
- Endpoint : `/books/{id}`
- Header :
  - Content-Type : application/json

Response:

```json
{
  "status": "String",
  "message": "String"
}
```
