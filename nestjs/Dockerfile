FROM node:21.6.1-bullseye-slim@sha256:075946be0844cc78063ac4c4654ec6c0d232b21a80f5af96ef2681182c5bf237 as development
WORKDIR /usr/src/app

COPY --chown=node:node ./package*.json .
COPY --chown=node:node . .

RUN npm ci \
  npx prisma generate

USER node


FROM node:21.6.1-bullseye-slim@sha256:075946be0844cc78063ac4c4654ec6c0d232b21a80f5af96ef2681182c5bf237 as builder
WORKDIR /usr/src/app

COPY --chown=node:node ./package*.json .
COPY --chown=node:node --from=development /usr/src/app/node_modules ./node_modules
COPY --chown=node:node . .

RUN npm run build
ENV NODE_ENV production

RUN npm ci --omit=dev && npm cache clean --force
USER node


FROM node:21.6.1-bullseye-slim@sha256:075946be0844cc78063ac4c4654ec6c0d232b21a80f5af96ef2681182c5bf237 as production
WORKDIR /usr/src/app

COPY --chown=node:node --from=builder /usr/src/app/node_modules ./node_modules
COPY --chown=node:node --from=builder /usr/src/app/dist ./dist

CMD [ "node", "dist/main.js" ]

