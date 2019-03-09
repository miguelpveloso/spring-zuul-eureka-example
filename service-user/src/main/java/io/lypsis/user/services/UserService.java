package io.lypsis.user.services;

import com.netflix.discovery.EurekaClient;
import io.lypsis.user.clients.CustomerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String appName;

    private CustomerClient customerClient;

    public UserService(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    public String greeting() {
        return this.customerClient.greeting();
    }

}
