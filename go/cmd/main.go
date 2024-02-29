package main

import (
	"fmt"

	"github.com/gin-gonic/gin"

	"github.com/Alturino/bookshelf-api/common/env"
	"github.com/Alturino/bookshelf-api/common/postgres"
	"github.com/Alturino/bookshelf-api/common/redis"
	"github.com/Alturino/bookshelf-api/internal/delivery/book"
	domain "github.com/Alturino/bookshelf-api/internal/domain/repository/book"
)

func main() {
	env := env.LoadConfig()
	redisURL := fmt.Sprintf(
		"redis://%s:%s@%s:%d/%d",
		env.RedisUsername,
		env.RedisPassword,
		env.RedisHost,
		env.RedisPort,
		env.RedisDB,
	)
	db := postgres.NewPostgreSQLClient(env.PostgresMigrationPath, env.PostgresURL)
	redisClient := redis.NewRedisClient(redisURL)
	bookRepository := domain.NewBookRepository(db)
	bookController := book.NewBookController(bookRepository, redisClient)

	r := gin.Default()
	books := r.Group("/books")
	{
		// books := v1.Group("/books")
		books.GET("", bookController.GetAllBook)
		books.POST("", bookController.InsertBook)
		books.GET("/:id", bookController.GetBookByID)
		books.DELETE("/:id", bookController.DeleteBookByID)
		books.PATCH("/:id", bookController.UpdateBookByID)
	}

	err := r.Run(env.ApplicationAddress)
	if err != nil {
		panic(err)
	}
}
