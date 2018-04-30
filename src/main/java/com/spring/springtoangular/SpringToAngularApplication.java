package com.spring.springtoangular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@SpringBootApplication
public class SpringToAngularApplication {

    public static void main(String[] args) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUrl("jdbc:postgresql://127.0.0.1:5432/movies");
        SpringApplication.run(SpringToAngularApplication.class, args);
    }
}
