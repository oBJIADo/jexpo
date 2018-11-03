package ru.tsystems.jirexpo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:spring.xml")
public class JirexpoApplication {

    /**
     * Starting spring-boot app.
     */
    public static void main(String[] args) {
        SpringApplication.run(JirexpoApplication.class, args);
    }
}
