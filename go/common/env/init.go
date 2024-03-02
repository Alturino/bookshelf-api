package env

import (
	"github.com/caarlos0/env/v10"
	_ "github.com/joho/godotenv/autoload"
)

type applicationConfig struct {
	ApplicationPort       int    `env:"APPLICATION_PORT"`
	AppicationHost        string `env:"APPLICATION_HOST"`
	ApplicationAddress    string `env:"APPLICATION_ADDRESS"`
	PostgresURL           string `env:"POSTGRES_URL"`
	PostgresDb            string `env:"POSTGRES_DB"`
	PostgresHost          string `env:"POSTGRES_HOST"`
	PostgresPassword      string `env:"POSTGRES_PASSWORD"`
	PostgresPort          int    `env:"POSTGRES_PORT"`
	PostgresUser          string `env:"POSTGRES_USER"`
	PostgresMigrationPath string `env:"POSTGRES_MIGRATION_PATH"`
	RedisUsername         string `env:"REDIS_USERNAME"`
	RedisAddress          string `env:"REDIS_ADDRESS"`
	RedisDB               int    `env:"REDIS_DB"`
	RedisPassword         string `env:"REDIS_PASSWORD"`
	RedisHost             string `env:"REDIS_HOST"`
	RedisPort             int    `env:"REDIS_PORT"`
}

func LoadConfig() *applicationConfig {
	config := &applicationConfig{}
	if err := env.Parse(config); err != nil {
		panic(err)
	}
	return config
}
