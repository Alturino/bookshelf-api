FROM node:lts-alpine

RUN mkdir -p /home/app
WORKDIR /home/app

COPY package.json /home/app
RUN npm i
COPY . /home/app
EXPOSE 9000
CMD [ "npm", "start" ]
