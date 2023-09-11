const { nanoid } = require('nanoid');
const books = require('./books');

const addBookHandler = (request, h) => {
  const { name, year, author, summary, publisher, pageCount, readPage, reading } = request.payload;

  const id = nanoid(16);
  const finished = readPage === pageCount;
  const insertedAt = new Date().toISOString();
  const updatedAt = insertedAt;

  const newBook = {
    id,
    name,
    year,
    author,
    summary,
    publisher,
    pageCount,
    readPage,
    finished,
    reading,
    insertedAt,
    updatedAt,
  };

  if (name === '' || name === undefined || name === null) {
    return h
      .response({
        status: 'fail',
        message: 'Gagal menambahkan buku. Mohon isi nama buku',
      })
      .code(400);
  }

  if (readPage > pageCount) {
    return h
      .response({
        status: 'fail',
        message: 'Gagal menambahkan buku. readPage tidak boleh lebih besar dari pageCount',
      })
      .code(400);
  }

  books.push(newBook);
  const isSuccess = books.filter((book) => book.id === id).length > 0;

  if (isSuccess) {
    return h
      .response({
        status: 'success',
        message: 'Buku berhasil ditambahkan',
        data: {
          bookId: id,
        },
      })
      .code(201);
  }

  return h
    .response({
      status: 'error',
      message: 'Buku gagal ditambahkan',
    })
    .code(500);
};

const getAllBookHandler = (request, h) => {
  const { name, reading, finished } = request.query;

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

const getDetailBookByIdHandler = (request, h) => {
  const { bookId } = request.params;
  const bookById = books.filter((book) => book.id === bookId)[0];

  if (bookById) {
    return h
      .response({
        status: 'success',
        data: {
          book: bookById,
        },
      })
      .code(200);
  }

  return h
    .response({
      status: 'fail',
      message: 'Buku tidak ditemukan',
    })
    .code(404);
};

const editBookByIdHandler = (request, h) => {
  const { bookId } = request.params;
  const { name, year, author, summary, publisher, readPage, pageCount, reading } = request.payload;

  const index = books.findIndex((book) => book.id === bookId);
  const finished = readPage === pageCount;
  const updatedAt = new Date().toISOString();

  if (name === '' || name === undefined || name === null) {
    return h
      .response({
        status: 'fail',
        message: 'Gagal memperbarui buku. Mohon isi nama buku',
      })
      .code(400);
  }

  if (readPage > pageCount) {
    return h
      .response({
        status: 'fail',
        message: 'Gagal memperbarui buku. readPage tidak boleh lebih besar dari pageCount',
      })
      .code(400);
  }

  if (index !== -1) {
    books[index] = {
      ...books[index],
      name,
      year,
      author,
      summary,
      publisher,
      pageCount,
      readPage,
      reading,
      finished,
      updatedAt,
    };

    return h
      .response({
        status: 'success',
        message: 'Buku berhasil diperbarui',
      })
      .code(200);
  }

  // id yang dilampirkan oleh client tidak ditemukkan oleh server
  return h
    .response({
      status: 'fail',
      message: 'Gagal memperbarui buku. Id tidak ditemukan',
    })
    .code(404);
};

const deleteBookByIdHandler = (request, h) => {
  const { bookId } = request.params;

  const index = books.findIndex((book) => book.id === bookId);

  if (index !== -1) {
    books.splice(index, 1);

    return h
      .response({
        status: 'success',
        message: 'Buku berhasil dihapus',
      })
      .code(200);
  }

  return h
    .response({
      status: 'fail',
      message: 'Buku gagal dihapus. Id tidak ditemukan',
    })
    .code(404);
};

module.exports = {
  addBookHandler,
  getAllBookHandler,
  getDetailBookByIdHandler,
  editBookByIdHandler,
  deleteBookByIdHandler,
};
