FROM golang:1.22.0-alpine3.19 AS builder
RUN apk update && apk add --no-cache git

WORKDIR /usr/src/app

COPY ["go.mod", "go.sum", "./"]
RUN go mod download

COPY . .
RUN go build cmd/main.go


FROM alpine:3.19.1 as production
RUN apk add --no-cache dumb-init

WORKDIR /usr/src/app

RUN addgroup --system go && adduser -S -s /bin/false -G go go

COPY --chown=go:go --from=builder /usr/src/app/main .
COPY --chown=go:go ./config/ .

USER go
CMD [ "dumb-init", "./main" ]
