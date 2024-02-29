package book

import "time"

type Entity struct {
	ID         string    `json:"id,omitempty"`
	Name       string    `json:"name,omitempty"`
	Year       int       `json:"year,omitempty"`
	Author     string    `json:"author,omitempty"`
	Summary    string    `json:"summary,omitempty"`
	Publisher  string    `json:"publisher,omitempty"`
	Pagecount  int       `json:"pageCount,omitempty"`
	ReadPage   int       `json:"readPage,omitempty"`
	Reading    bool      `json:"reading,omitempty"`
	InsertedAt time.Time `json:"inserted_at,omitempty"`
	UpdatedAt  time.Time `json:"updated_at,omitempty"`
	Finished   bool      `json:"finished,omitempty"`
}
