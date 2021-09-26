const { addBookHandler } = require('./handler/addBookHandler.js');
const { deleteBookByIdHandler } = require('./handler/deleteBookByIdHandler');
const { editBookByIdHandler } = require('./handler/editBookByIdHandler');
const { getAllBookHandler } = require('./handler/getAllBookHandler');
const { getDetailBookByIdHandler } = require('./handler/getDetailBookByIdHandler');

const routes = [
  {
    method: 'POST',
    path: '/books',
    handler: addBookHandler,
  },
  {
    method: 'GET',
    path: '/books',
    handler: getAllBookHandler,
  },
  {
    method: 'GET',
    path: '/books/{bookId}',
    handler: getDetailBookByIdHandler,
  },
  {
    method: 'PUT',
    path: '/books/{bookId}',
    handler: editBookByIdHandler,
  },
  {
    method: 'DELETE',
    path: '/books/{bookId}',
    handler: deleteBookByIdHandler,
  },
];

module.exports = routes;
