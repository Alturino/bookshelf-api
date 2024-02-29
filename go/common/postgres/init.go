package postgres

import (
	"database/sql"
	"errors"
	"log"
	"time"

	"github.com/golang-migrate/migrate/v4"
	"github.com/golang-migrate/migrate/v4/database/postgres"
	_ "github.com/golang-migrate/migrate/v4/source/file"
	_ "github.com/lib/pq"
)

func NewPostgreSQLClient(migrationPath string, dataSourceName string) *sql.DB {
	db, err := sql.Open("postgres", dataSourceName)
	if err != nil {
		log.Fatalf("error in migratePostgresql l:18 with error: %v", err.Error())
	}

	driver, err := postgres.WithInstance(db, &postgres.Config{})
	if err != nil {
		log.Fatalf("error in migratePostgresql l:23 with error: %v", err.Error())
	}

	migration, err := migrate.NewWithDatabaseInstance(migrationPath, dataSourceName, driver)
	if err != nil {
		log.Fatalf("error in migratePostgresql l:28 with error: %v", err.Error())
	}

	err = migration.Down()
	if err != nil && !errors.Is(err, migrate.ErrNoChange) {
		log.Fatalf("error in migratePostgresql l:33 with error: %v", err.Error())
	}

	err = migration.Up()
	if err != nil && !errors.Is(err, migrate.ErrNoChange) {
		log.Fatalf("error in migratePostgresql l:38 with error: %v", err.Error())
	}

	err = db.Ping()
	if err != nil {
		log.Fatalf("error in NewPostgreSQLClient l:43 with error: %v", err.Error())
	}

	db.SetConnMaxLifetime(time.Minute * 5)
	db.SetMaxOpenConns(5)
	db.SetMaxIdleConns(5)
	return db
}
