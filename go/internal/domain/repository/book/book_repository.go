package book

import (
	"database/sql"
	"errors"
	"log"

	"golang.org/x/net/context"

	"github.com/Alturino/bookshelf-api/internal/domain/model/book"
)

type BookRepository interface {
	GetAllBook(
		ctx context.Context,
		name string,
		reading int,
		finished int,
	) (books []book.Entity, err error)
	GetBookByID(ctx context.Context, bookID string) (book book.Entity, err error)
	InsertBook(ctx context.Context, bookDto book.BookDto) (book book.Entity, err error)
	UpdateBook(
		ctx context.Context,
		bookDto book.BookDto,
		bookID string,
	) (book book.Entity, err error)
	DeleteBookByID(ctx context.Context, bookID string) (book book.Entity, err error)
}

func NewBookRepository(db *sql.DB) BookRepository {
	return newBookRepositoryImpl(db)
}

type bookRepositoryImpl struct {
	db *sql.DB
}

func newBookRepositoryImpl(db *sql.DB) *bookRepositoryImpl {
	return &bookRepositoryImpl{db: db}
}

func (r bookRepositoryImpl) GetAllBook(
	ctx context.Context,
	name string,
	reading int,
	finished int,
) (books []book.Entity, err error) {
	rows, err := r.db.QueryContext(
		ctx,
		"SELECT id, name, publisher FROM books WHERE ($1::text is null or name = $1) AND ($2::integer IS NULL OR reading = $2::boolean) AND ($3::boolean IS NULL or finished = $3);",
		name,
		reading,
		finished,
	)
	defer func() {
		err = rows.Close()
	}()
	if errors.Is(err, sql.ErrNoRows) {
		log.Printf("error in GetAllBook with error: %v", err.Error())
		return nil, err
	}

	for rows.Next() {
		var book book.Entity
		err = rows.Scan(
			&book.ID,
			&book.Name,
			&book.Year,
			&book.Author,
			&book.Summary,
			&book.Publisher,
			&book.Pagecount,
			&book.ReadPage,
			&book.Reading,
			&book.Finished,
		)
		if err != nil {
			log.Printf("error in GetAllBook with error: %v", err.Error())
			return nil, err
		}
		books = append(books, book)
	}

	if err = rows.Err(); err != nil {
		return nil, err
	}

	return books, nil
}

func (r bookRepositoryImpl) GetBookByID(
	ctx context.Context,
	bookID string,
) (book book.Entity, err error) {
	row := r.db.QueryRowContext(
		ctx,
		"SELECT * from books WHERE id = $1 LIMIT 1;",
		bookID,
	)
	err = row.Scan(
		&book.ID,
		&book.Name,
		&book.Year,
		&book.Author,
		&book.Summary,
		&book.Publisher,
		&book.Pagecount,
		&book.ReadPage,
		&book.Reading,
		&book.Finished,
	)
	if errors.Is(err, sql.ErrNoRows) {
		log.Printf("error in GetBookByID with error: %v", err.Error())
		return book, err
	}

	return book, err
}

func (r bookRepositoryImpl) InsertBook(
	ctx context.Context,
	bookDto book.BookDto,
) (book book.Entity, err error) {
	row := r.db.QueryRowContext(
		ctx,
		"INSERT INTO books (name, year, author, summary, publisher, page_count, read_page, reading, finished) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9) RETURNING *;",
		bookDto.Name,
		bookDto.Year,
		bookDto.Author,
		bookDto.Summary,
		bookDto.Publisher,
		bookDto.PageCount,
		bookDto.ReadPage,
		bookDto.ReadPage > 0,
		bookDto.ReadPage == bookDto.PageCount,
	)
	err = row.Scan(
		&book.ID,
		&book.Name,
		&book.Year,
		&book.Author,
		&book.Summary,
		&book.Publisher,
		&book.Pagecount,
		&book.ReadPage,
		&book.Reading,
		&book.Finished,
	)
	if errors.Is(err, sql.ErrNoRows) {
		log.Printf("error in InsertBook with error: %v", err.Error())
		return book, err
	}

	return book, nil
}

func (r bookRepositoryImpl) UpdateBook(
	ctx context.Context,
	bookDto book.BookDto,
	bookID string,
) (book book.Entity, err error) {
	row := r.db.QueryRowContext(
		ctx,
		"INSERT INTO books (id, name, year, author, summary, publisher, page_count, read_page, reading, finished) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9) RETURNING id; ON CONFLICT (name, year, author, summary, publisher, page_count, read_page, reading, finished) DO NOTHING | DO UPDATE SET name=$1, year=$2, author=$3, summary=$4, publisher=$5, page_count=$6, read_page=$7, reading=$8, finished=$9; RETURNING id, name, year, author, summary, publisher, page_count, read_page, reading, finished",
		bookID,
		bookDto.Name,
		bookDto.Year,
		bookDto.Author,
		bookDto.Summary,
		bookDto.Publisher,
		bookDto.PageCount,
		bookDto.ReadPage,
		bookDto.ReadPage > 0,
		bookDto.ReadPage == bookDto.PageCount,
	)
	err = row.Scan(
		&book.ID,
		&book.Name,
		&book.Year,
		&book.Author,
		&book.Summary,
		&book.Publisher,
		&book.Pagecount,
		&book.ReadPage,
		&book.Reading,
		&book.Finished,
	)
	if errors.Is(err, sql.ErrNoRows) {
		log.Printf("error in UpdateBook with error: %v", err.Error())
		return book, err
	}

	return book, nil
}

func (r bookRepositoryImpl) DeleteBookByID(
	ctx context.Context,
	bookID string,
) (book book.Entity, err error) {
	row := r.db.QueryRowContext(
		ctx,
		"DELETE FROM books WHERE id = $1 LIMIT 1 RETURNING id, name, year, author, summary, publisher, page_count, read_page, reading, finished;",
		bookID,
	)
	err = row.Scan(
		&book.ID,
		&book.Name,
		&book.Year,
		&book.Author,
		&book.Summary,
		&book.Publisher,
		&book.Pagecount,
		&book.ReadPage,
		&book.Reading,
		&book.Finished,
	)
	if errors.Is(err, sql.ErrNoRows) {
		log.Printf("error in DeleteBookByID with error: %v", err.Error())
		return book, err
	}

	return book, nil
}
