package com.example.learnspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestLearnSpringbootApplication {
    //
//    @Bean
//    @ServiceConnection
//    ElasticsearchContainer elasticsearchContainer() {
//        return new ElasticsearchContainer(DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:7.17.10"));
//    }
//
//    @Bean
//    @ServiceConnection
//    KafkaContainer kafkaContainer() {
//        return new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
//    }
//
    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.1-alpine3.19"));
    }
//
//    @Bean
//    @ServiceConnection
//    RabbitMQContainer rabbitContainer() {
//        return new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.12.12-alpine"));
//    }
//
//    @Bean
//    @ServiceConnection(name = "redis")
//    GenericContainer<?> redisContainer() {
//        return new GenericContainer<>(DockerImageName.parse("redis:7.2.4-alpine3.19")).withExposedPorts(6379);
//    }
//
    public static void main(String[] args) {
        SpringApplication.from(LearnSpringbootApplication::main).with(TestLearnSpringbootApplication.class).run(args);
    }

}
