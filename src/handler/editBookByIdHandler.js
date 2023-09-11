const editBookByIdHandler = async (request, h) => {
  try {
    const { bookId } = request.params;
    const { name, year, author, summary, publisher, readPage, pageCount, reading } =
      request.payload;

    const finished = readPage === pageCount;
    const updatedAt = new Date().toISOString();

    const updatedAttribute = {
      name: name,
      year: year,
      author: author,
      summary: summary,
      publisher: publisher,
      pageCount: pageCount,
      readPage: readPage,
      reading: reading,
      finished: finished,
      updatedAt: updatedAt,
    };

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

    const updatedBook = await request.mongo.db.collection('books').updateOne(
      {
        id: bookId,
      },
      {
        $set: updatedAttribute,
      },
    );

    if (updatedBook.modifiedCount === 1) {
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
  } catch (e) {
    console.error(e);
  }
};

module.exports = { editBookByIdHandler };
