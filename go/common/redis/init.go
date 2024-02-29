package redis

import (
	"context"
	"log"

	"github.com/redis/go-redis/v9"
)

func NewRedisClient(redisURL string) *redis.Client {
	redisOptions, err := redis.ParseURL(redisURL)
	if err != nil {
		log.Fatalf("error in NewRedisClient l:13 with error: %v", err.Error())
	}

	redicClient := redis.NewClient(redisOptions)
	if err := redicClient.Ping(context.Background()).Err(); err != nil {
		log.Fatalf("error in NewRedisClient l:18 with error: %v", err.Error())
	}

	return redicClient
}
