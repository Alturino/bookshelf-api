FROM gradle:8.5-jdk21-alpine as builder
WORKDIR /usr/src/app/

COPY . .
RUN ./gradlew clean build -v && \
    ./gradlew bootJar -v

FROM eclipse-temurin:21.0.1_12-jre-alpine as production
RUN apk add dumb-init
WORKDIR /usr/src/app/

RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser

COPY --chown=javauser:javauser --from=builder /usr/src/app/build/libs/learn-springboot-0.0.1-SNAPSHOT.jar app.jar
COPY --chown=javauser:javauser ./src/main/resources/application.properties .

USER javauser
CMD ["dumb-init", "java", "-jar", "./app.jar"]
