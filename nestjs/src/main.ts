import { Logger, ValidationPipe } from '@nestjs/common';
import { NestFactory } from '@nestjs/core';
import { AppModule } from './app/app.module';
import { DocumentBuilder, SwaggerModule } from '@nestjs/swagger';

async function bootstrap() {
  const app = await NestFactory.create(AppModule, {
    cors: {
      origin: true,
    },
    abortOnError: process.env.NODE_ENV === 'production',
  });
  app.useGlobalPipes(
    new ValidationPipe({
      transform: true,
      enableDebugMessages: process.env.NODE_ENV !== 'production',
    }),
  );

  const swaggerConfig = new DocumentBuilder()
    .setTitle('Bookshelf API')
    .setDescription('Bookshelf API description')
    .setVersion('1.0')
    .addTag('Bookshelf')
    .build();
  const swaggerDocument = SwaggerModule.createDocument(app, swaggerConfig);
  const logger = new Logger('NestApplication');
  SwaggerModule.setup('api', app, swaggerDocument);
  const port = Number(process.env.NESTJS_SERVER_PORT);
  const host = process.env.NODE_ENV === 'production' ? '0.0.0.0' : 'localhost';
  logger.log(`Initializing listener on host: ${host} port: ${port}`);
  await app.listen(port, host);
  logger.log(`Listening on host: ${host} port: ${port}`);
}
bootstrap();
