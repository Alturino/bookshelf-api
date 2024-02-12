const getAllBookHandler = async (request, h) => {
  const { name, reading, finished } = request.query;

  const books = await request.mongo.db.collection('books').find({}).toArray();

  const bookAttributeFiltered = books.map((book) => ({
    id: book.id,
    name: book.name,
    publisher: book.publisher,
  }));

  if (name) {
    const filteredBooksName = books
      .filter((book) => {
        // kalau ada query name
        const nameRegex = new RegExp(name, 'gi');
        return nameRegex.test(book.name);
      })
      .map((book) => ({
        id: book.id,
        name: book.name,
        publisher: book.publisher,
      }));

    return h
      .response({
        status: 'success',
        data: {
          books: filteredBooksName,
        },
      })
      .code(200);
  }

  if (reading) {
    const filteredBooksReading = books
      .filter((book) => Number(book.reading) === Number(reading))
      .map((book) => ({
        id: book.id,
        name: book.name,
        publisher: book.publisher,
      }));

    return h
      .response({
        status: 'success',
        data: {
          books: filteredBooksReading,
        },
      })
      .code(200);
  }

  if (finished) {
    const filteredBooksFinished = books
      .filter((book) => Number(book.finished) === Number(finished))
      .map((book) => ({
        id: book.id,
        name: book.name,
        publisher: book.publisher,
      }));

    return h
      .response({
        status: 'success',
        data: {
          books: filteredBooksFinished,
        },
      })
      .code(200);
  }

  return h
    .response({
      status: 'success',
      data: {
        books: bookAttributeFiltered,
      },
    })
    .code(200);
};

module.exports = { getAllBookHandler };
