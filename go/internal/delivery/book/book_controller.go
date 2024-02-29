package book

import (
	"database/sql"
	"encoding/json"
	"errors"
	"fmt"
	"log"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/go-playground/validator/v10"
	"github.com/redis/go-redis/v9"

	hashutil "github.com/Alturino/bookshelf-api/internal/delivery/hash-util"
	model "github.com/Alturino/bookshelf-api/internal/domain/model/book"
	"github.com/Alturino/bookshelf-api/internal/domain/repository/book"
)

type BookController interface {
	GetAllBook(c *gin.Context)
	GetBookByID(c *gin.Context)
	InsertBook(c *gin.Context)
	UpdateBookByID(c *gin.Context)
	DeleteBookByID(c *gin.Context)
}

func NewBookController(repository book.BookRepository, redisClient *redis.Client) BookController {
	return newBookController(repository, redisClient)
}

type bookController struct {
	repository  book.BookRepository
	redisClient *redis.Client
}

func newBookController(repository book.BookRepository, redisClient *redis.Client) bookController {
	return bookController{repository: repository, redisClient: redisClient}
}

func (r bookController) GetAllBook(c *gin.Context) {
	var queryParam model.GetBookDto
	err := c.ShouldBindQuery(&queryParam)
	if err != nil {
		log.Printf("error in BookController.GetAllBook with error: %v", err.Error())
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{
			"status": "success",
			"data": gin.H{
				"books": []model.GetBookDto{},
			},
		})
		return
	}

	queryParamJSON, err := json.Marshal(queryParam)
	if err != nil {
		log.Printf("error in BookController.GetAllBook with error: %v", err.Error())
	}

	cacheKey, err := hashutil.GetCacheKey(queryParamJSON)
	if err != nil {
		log.Printf("error in BookController.GetAllBook with error: %v", err.Error())
	}

	cacheData, err := r.redisClient.Get(c, string(cacheKey)).Result()
	if err != nil {
		books, err := r.repository.GetAllBook(
			c,
			queryParam.Name,
			queryParam.Reading,
			queryParam.Finished,
		)
		if err != nil {
			log.Printf("error in BookController.GetBookByID with error: %v", err.Error())
			c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{
				"status": "success",
				"data": gin.H{
					"books": []model.GetBookDto{},
				},
			})
			return
		}

		booksJSON, err := json.Marshal(books)
		if err != nil {
			log.Printf("error in BookController.GetBookByID with error: %v", err.Error())
		}

		err = r.redisClient.Set(c, string(cacheKey), booksJSON, 30*time.Minute).Err()
		if err != nil {
			log.Printf("error in BookController.GetBookByID with error: %v", err.Error())
		}

		c.JSON(http.StatusOK, gin.H{
			"status": "sucess",
			"data": gin.H{
				"books": books,
			},
		})
		return
	}

	c.JSON(http.StatusOK, gin.H{
		"status": "sucess",
		"data": gin.H{
			"books": cacheData,
		},
	})
}

func (r bookController) GetBookByID(c *gin.Context) {
	bookID := c.Param("id")
	cacheData, err := r.redisClient.Get(c, bookID).Result()
	if err != nil {
		book, err := r.repository.GetBookByID(c, bookID)
		if errors.Is(err, sql.ErrNoRows) {
			log.Printf("error in BookController.GetBookByID with error: %v", err.Error())
			c.AbortWithStatusJSON(http.StatusNotFound, gin.H{
				"status":  "fail",
				"message": "Buku tidak ditemukan",
			})
			return
		}

		bookJSON, err := json.Marshal(book)
		if err != nil {
			log.Printf("error in BookController.GetBookByID with error: %v", err.Error())
		}

		err = r.redisClient.Set(c, bookID, bookJSON, 30*time.Minute).Err()
		if err != nil {
			log.Printf("error in BookController.GetBookByID with error: %v", err.Error())
		}
	}

	c.JSON(http.StatusOK, gin.H{
		"status": "success",
		"data": gin.H{
			"book": cacheData,
		},
	})
}

func (r bookController) UpdateBookByID(c *gin.Context) {
	bookID := c.Param("id")
	var requestBody model.BookDto
	err := c.ShouldBindJSON(&requestBody)
	if err != nil {
		var ve validator.ValidationErrors
		errors.As(err, &ve)
		for _, fe := range ve {
			log.Println(fe)
			if fe.Field() == "name" {
				c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{
					"status":  "fail",
					"message": "Gagal memperbarui buku. Mohon isi nama buku",
				})
				return
			}
			if fe.Field() == "readPage" {
				c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{
					"status":  "fail",
					"message": "Gagal memperbarui buku. readPage tidak boleh lebih besar dari pageCount",
				})
				return
			}
		}
	}

	book, err := r.repository.UpdateBook(c, requestBody, bookID)
	if errors.Is(err, sql.ErrNoRows) {
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{
			"status":  "fail",
			"message": "Gagal memperbarui buku. Id tidak ditemukan",
		})
		return
	}

	bookJSON, err := json.Marshal(book)
	if err != nil {
		log.Printf("error in BookController.UpdateBookByID with error: %v", err.Error())
	}

	cacheKey, err := hashutil.GetCacheKey(bookJSON)
	if err != nil {
		log.Printf("error in BookController.UpdateBookByID with error: %v", err.Error())
	}

	err = r.redisClient.Set(c, string(cacheKey), bookJSON, 30*time.Minute).Err()
	if err != nil {
		log.Printf("error in BookController.UpdateBookByID with error: %v", err.Error())
	}

	c.JSON(http.StatusOK, gin.H{
		"status":  "success",
		"message": "Buku berhasil diperbarui",
	})
}

func (r bookController) InsertBook(c *gin.Context) {
	var requestBody model.BookDto
	if err := c.ShouldBindJSON(&requestBody); err != nil {
		for _, fe := range err.(validator.ValidationErrors) {
			if fe.Field() == "Name" {
				c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{
					"status":  "fail",
					"message": "Gagal menambahkan buku. Mohon isi nama buku",
				})
				return
			}
			if fe.Field() == "ReadPage" {
				c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{
					"status":  "fail",
					"message": "Gagal menambahkan buku. readPage tidak boleh lebih besar dari pageCount",
				})
				return
			}
		}
	}

	book, err := r.repository.InsertBook(c, requestBody)
	if err != nil {
		log.Printf("error in BookController.InsertBook with error: %v", err.Error())

		c.AbortWithStatusJSON(http.StatusInternalServerError, gin.H{
			"status":  "fail",
			"message": fmt.Sprintf("Gagal menambahkan buku dengan error: %s", err.Error()),
		})
		return
	}

	bookJSON, err := json.Marshal(book)
	if err != nil {
		log.Printf("error in BookController.InsertBook with error: %v", err.Error())

		c.AbortWithStatusJSON(http.StatusInternalServerError, gin.H{
			"status":  "fail",
			"message": fmt.Sprintf("Gagal menambahkan buku dengan error: %s", err.Error()),
		})
		return
	}

	err = r.redisClient.Set(c, book.ID, bookJSON, 30*time.Minute).Err()
	if err != nil {
		log.Printf("error in BookController.InsertBook with error: %v", err.Error())

		c.AbortWithStatusJSON(http.StatusInternalServerError, gin.H{
			"status":  "fail",
			"message": fmt.Sprintf("Gagal menambahkan buku dengan error: %s", err.Error()),
		})
		return
	}

	c.JSON(http.StatusCreated, gin.H{
		"status":  "success",
		"message": "Buku berhasil ditambahkan",
		"data": gin.H{
			"bookId": book.ID,
		},
	})
}

func (r bookController) DeleteBookByID(c *gin.Context) {
	bookID := c.Param("id")
	_, err := r.repository.DeleteBookByID(c, bookID)
	if errors.Is(err, sql.ErrNoRows) {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{
			"status":  "fail",
			"message": "Buku gagal dihapus. Id tidak ditemukan",
		})
	}

	err = r.redisClient.Del(c, bookID).Err()
	if err != nil {
		log.Printf("error in BookController.DeleteBookByID with error: %v", err.Error())
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{
			"status":  "fail",
			"message": fmt.Sprint("Buku gagal dihapus. dengan error: %s", err.Error()),
		})
	}

	c.JSON(http.StatusOK, gin.H{
		"status":  "success",
		"message": "Buku berhasil dihapus",
	})
}
