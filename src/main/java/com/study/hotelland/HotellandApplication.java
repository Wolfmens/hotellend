package com.study.hotelland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.study.hotelland.statistic.repository")
public class HotellandApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotellandApplication.class, args);
    }

}
