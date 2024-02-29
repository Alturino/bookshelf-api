package book

type GetBookDto struct {
	Name     string `json:"name"`
	Finished int    `json:"finished" binding:"number,min=0,max=1"`
	Reading  int    `json:"reading"  binding:"number,min=0,max=1"`
}
