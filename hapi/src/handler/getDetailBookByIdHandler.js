const getDetailBookByIdHandler = async (request, h) => {
  const { bookId } = request.params;

  const bookById = await request.mongo.db.collection('books').findOne({
    id: bookId,
  });

  if (bookById != null) {
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

module.exports = { getDetailBookByIdHandler };
