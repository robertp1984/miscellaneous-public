package org.softwarecave.springboottours;

import org.softwarecave.springboottours.db.DBLoader;
import org.softwarecave.springboottours.db.repo.TourPackageRepository;
import org.softwarecave.springboottours.db.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.InputStream;
import java.io.InputStreamReader;

@SpringBootApplication
public class Springboot1Application {

    public static void main(String[] args) {
        SpringApplication.run(Springboot1Application.class, args);
    }

    @Bean
    public CommandLineRunner testRunner1(RandomNumberGenService randomNumberGenService) {
        return (args) -> {
            System.out.println("Hello, World! + " + randomNumberGenService.generateSecureRandomNumber(10, 100));
        };
    }

    @Bean
    public CommandLineRunner runDbLoader(DBLoader dbLoader) {
        return (args) -> {
            ClassLoader classLoader = getClass().getClassLoader();
            try (InputStream is = classLoader.getResourceAsStream("/sample-tour.json")) {
                if (is != null) {
                    dbLoader.loadFromFile(new InputStreamReader(is));
                }
            }
        };
    }

}
