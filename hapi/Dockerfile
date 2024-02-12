FROM node:lts-alpine AS builder

WORKDIR /usr/src/app

COPY --chown=node:node ./package*.json .
COPY --chown=node:node . .

RUN npm ci --omit=dev
USER node

FROM node:lts-alpine AS production
WORKDIR /usr/src/app

COPY --chown=node:node --from=builder /usr/src/app/node_modules ./node_modules
COPY --chown=node:node ./src ./src

EXPOSE 9000
USER node
CMD [ "node", "./src/server.js" ]
