package env

import (
	"github.com/caarlos0/env/v10"
)

type applicationConfig struct {
	ApplicationPort  int    `env:"APPLICATION_PORT"  envDefault:"9000"`
	PostgresDb       string `env:"POSTGRES_DB"       envDefault:"bookshelf"`
	PostgresHost     string `env:"POSTGRES_HOST"     envDefault:"postgres"`
	PostgresPassword string `env:"POSTGRES_PASSWORD" envDefault:"bookshelf-application"`
	PostgresPort     int    `env:"POSTGRES_PORT"     envDefault:"5432"`
	PostgresUser     string `env:"POSTGRES_USER"     envDefault:"bookshelf-application"`
	RedisHost        string `env:"REDIS_HOST"        envDefault:"redis"`
	RedisPort        int    `env:"REDIS_PORT"        envDefault:"6379"`
}

func LoadConfig() *applicationConfig {
	config := &applicationConfig{}
	if err := env.Parse(config); err != nil {
		panic(err)
	}
	return config
}
