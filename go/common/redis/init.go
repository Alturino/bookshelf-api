package redis

import (
	"context"
	"log"

	"github.com/redis/go-redis/v9"
)

func NewRedisClient(address string, username string, password string, db int) *redis.Client {
	rc := redis.NewClient(&redis.Options{
		Addr:     address,
		Username: username,
		Password: password,
		DB:       db,
	})
	if err := rc.Ping(context.Background()).Err(); err != nil {
		log.Fatalln(err)
	}
	return rc
}
