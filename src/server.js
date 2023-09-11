const Hapi = require('@hapi/hapi');
const routes = require('./routes');
const dotenv = require('dotenv');
// const mongoDb = require('hapi-mongodb');

const init = async () => {
  dotenv.config();

  // Server Driver
  const server = Hapi.server({
    port: process.env.PORT || 80,
    host: process.env.NODE_ENV !== 'production' ? 'localhost' : '0.0.0.0',
    routes: {
      cors: {
        origin: ['*'],
      },
    },
  });

  // await server.register({
  //   plugin: mongoDb,
  //   options: {
  //     url: process.env.MONGODB_URL,
  //     settings: {
  //       useUnifiedTopology: true,
  //     },
  //     decorate: true,
  //   },
  // });

  server.route(routes);

  await server.start();
  console.log(`Server berjalan pada ${server.info.uri}`);
};

init();
