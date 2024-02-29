package book

type BookDto struct {
	Name      string `binding:"required"                                json:"name"`
	Year      int    `binding:"required,min=1,number"                   json:"year"`
	Author    string `binding:"required"                                json:"author"`
	Summary   string `binding:"required"                                json:"summary"`
	Publisher string `binding:"required"                                json:"publisher"`
	PageCount int    `binding:"required,min=1,number"                   json:"pageCount"`
	ReadPage  int    `binding:"required,min=1,number,ltfield=PageCount" json:"readPage"`
}
