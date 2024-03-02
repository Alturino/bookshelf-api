package response

type GetBookResponse struct {
	ID        string `json:"id,omitempty"`
	Name      string `json:"name,omitempty"`
	Publisher string `json:"publisher,omitempty"`
}
