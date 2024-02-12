FROM  eclipse-temurin:21.0.2_13-jre-jammy
VOLUME /tmp

WORKDIR /usr/src/app/

COPY ./build/libs/learn-springboot-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "./app.jar"]