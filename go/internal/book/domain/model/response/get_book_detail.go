package response

import (
	"encoding/json"
	"time"
)

type GetBookDetail struct {
	ID         string    `json:"id,omitempty"`
	Name       string    `json:"name,omitempty"`
	Year       int       `json:"year,omitempty"`
	Author     string    `json:"author,omitempty"`
	Summary    string    `json:"summary,omitempty"`
	Publisher  string    `json:"publisher,omitempty"`
	PageCount  int       `json:"pageCount,omitempty"`
	ReadPage   int       `json:"readPage,omitempty"`
	Reading    bool      `json:"reading"`
	Finished   bool      `json:"finished"`
	InsertedAt time.Time `json:"insertedAt,omitempty"`
	UpdatedAt  time.Time `json:"updatedAt,omitempty"`
}

func (e GetBookDetail) MarshalBinary() ([]byte, error) {
	return json.Marshal(e)
}
