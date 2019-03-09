package io.lypsis.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServiceUser {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUser.class, args);
    }
}
