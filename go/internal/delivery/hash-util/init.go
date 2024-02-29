package hashutil

import (
	"encoding/json"
	"hash/adler32"
)

func GetCacheKey(toHash any) (hashed []byte, err error) {
	algorithm := adler32.New()

	toHashJSON, err := json.Marshal(toHash)
	if err != nil {
		return nil, err
	}
	_, err = algorithm.Write(toHashJSON)
	if err != nil {
		return nil, err
	}

	return algorithm.Sum(nil), nil
}
