package com.example.learnspringboot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.logging.Logger;

@SpringBootApplication
@EnableCaching
public class LearnSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnSpringbootApplication.class, args);
    }

}

@Component
class EnvironmentPropertiesPrinter {

    private static Logger logger = Logger.getLogger(EnvironmentPropertiesPrinter.class.getName());

    @Autowired
    private Environment env;

    @PostConstruct
    public void logApplicationProperties() {
        ConfigurableEnvironment ce = (ConfigurableEnvironment) env;
        ce.getPropertySources().stream()
            .filter(ps -> ps instanceof MapPropertySource)
            .map(ps -> ((MapPropertySource) ps).getSource().keySet())
            .flatMap(Collection::stream)
            .distinct()
            .sorted()
            .forEach(key -> logger.info("key: " + key + "value: " + ce.getProperty(key)));

    }
}
