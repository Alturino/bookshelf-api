const deleteBookByIdHandler = async (request, h) => {
  const { bookId } = request.params;

  const result = await request.mongo.db.collection('books').deleteOne({
    id: bookId,
  });

  if (result.deletedCount === 1) {
    return h.response({
      status: 'success',
      message: 'Buku berhasil dihapus',
    });
  }

  return h
    .response({
      status: 'fail',
      message: 'Buku gagal dihapus. Id tidak ditemukan',
    })
    .code(404);
};

module.exports = { deleteBookByIdHandler };
