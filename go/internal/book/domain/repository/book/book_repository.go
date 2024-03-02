package book

import (
	"database/sql"
	"errors"
	"log"

	"golang.org/x/net/context"

	"github.com/Alturino/bookshelf-api/internal/book/domain/model/request"
	"github.com/Alturino/bookshelf-api/internal/book/domain/model/response"
)

type BookRepository interface {
	GetAllBook(
		ctx context.Context,
		name string,
		reading int,
		finished int,
	) (books []response.GetBookResponse, err error)
	GetBookByID(ctx context.Context, bookID string) (book response.GetBookDetail, err error)
	InsertBook(
		ctx context.Context,
		bookDto request.InsertBookDto,
	) (book response.GetBookDetail, err error)
	UpdateBook(
		ctx context.Context,
		bookDto request.InsertBookDto,
		bookID string,
	) (book response.GetBookDetail, err error)
	DeleteBookByID(ctx context.Context, bookID string) (book response.GetBookDetail, err error)
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
) (books []response.GetBookResponse, err error) {
	var rows *sql.Rows
	if name == "" && reading == 0 && finished == 0 {
		rows, err = r.db.QueryContext(ctx, "SELECT id, name, publisher FROM books")
	} else {
		rows, err = r.db.QueryContext(
			ctx,
			"SELECT id, name, publisher FROM books WHERE ($1::text is '' or name = $1) AND ($2::integer IS 0 OR reading = $2::boolean) AND ($3::boolean IS 0 or finished = $3);",
			name,
			reading,
			finished,
		)
	}
	defer func(rows *sql.Rows) {
		err := rows.Close()
		if err != nil {
			log.Printf("error in GetAllBook with error: %v", err.Error())
		}
	}(rows)
	if err != nil {
		log.Printf("error in GetAllBook with error: %v", err.Error())
		return nil, err
	}

	for rows.Next() {
		book := response.GetBookResponse{}
		err = rows.Scan(
			&book.ID,
			&book.Name,
			&book.Publisher,
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
) (book response.GetBookDetail, err error) {
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
		&book.PageCount,
		&book.ReadPage,
		&book.Reading,
		&book.InsertedAt,
		&book.UpdatedAt,
		&book.Finished,
	)
	if err != nil {
		log.Printf("error in GetBookByID with error: %v", err.Error())
		return book, err
	}

	return book, err
}

func (r bookRepositoryImpl) InsertBook(
	ctx context.Context,
	bookDto request.InsertBookDto,
) (book response.GetBookDetail, err error) {
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
		&book.PageCount,
		&book.ReadPage,
		&book.Reading,
		&book.InsertedAt,
		&book.UpdatedAt,
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
	bookDto request.InsertBookDto,
	bookID string,
) (book response.GetBookDetail, err error) {
	row := r.db.QueryRowContext(
		ctx,
		"UPDATE books set name=$2, year=$3, author=$4, summary=$5, publisher=$6, page_count=$7, read_page=$8, reading=$9 where id=$1 RETURNING id, name, year, author, summary, publisher, page_count, read_page, reading, finished, inserted_at, updated_at;",
		bookID,
		bookDto.Name,
		bookDto.Year,
		bookDto.Author,
		bookDto.Summary,
		bookDto.Publisher,
		bookDto.PageCount,
		bookDto.ReadPage,
		bookDto.ReadPage == bookDto.PageCount,
	)
	err = row.Scan(
		&book.ID,
		&book.Name,
		&book.Year,
		&book.Author,
		&book.Summary,
		&book.Publisher,
		&book.PageCount,
		&book.ReadPage,
		&book.Reading,
		&book.Finished,
		&book.InsertedAt,
		&book.UpdatedAt,
	)
	if err != nil {
		log.Printf("error in UpdateBook with error: %v", err.Error())
		return book, err
	}

	return book, nil
}

func (r bookRepositoryImpl) DeleteBookByID(
	ctx context.Context,
	bookID string,
) (book response.GetBookDetail, err error) {
	row := r.db.QueryRowContext(
		ctx,
		"DELETE FROM books WHERE id = $1 RETURNING id, name, year, author, summary, publisher, page_count, read_page, reading, finished, inserted_at, updated_at;",
		bookID,
	)
	err = row.Scan(
		&book.ID,
		&book.Name,
		&book.Year,
		&book.Author,
		&book.Summary,
		&book.Publisher,
		&book.PageCount,
		&book.ReadPage,
		&book.Reading,
		&book.Finished,
		&book.InsertedAt,
		&book.UpdatedAt,
	)
	if err != nil {
		log.Printf("error in DeleteBookByID with error: %v", err.Error())
		return book, err
	}

	return book, nil
}
