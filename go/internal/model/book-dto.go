package model

type BookDto struct {
	Name      string `json:"name"`
	Year      int    `json:"year"`
	Author    string `json:"author"`
	Summary   string `json:"summary"`
	Publisher string `json:"publisher"`
	PageCount int    `json:"pageCount"`
	ReadPage  int    `json:"readPage"`
	Reading   bool   `json:"reading"`
}

func NewBookDto(
	name string,
	year int,
	author string,
	summary string,
	publisher string,
	pageCount int,
	readPage int,
	reading bool,
) *BookDto {
	return &BookDto{Name: name, Year: year, Author: author, Summary: summary, Publisher: publisher, PageCount: pageCount, ReadPage: readPage, Reading: reading}
}
