package io.lypsis.commons.utils;

import io.lypsis.commons.clients.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug(userClient.greeting());
        // Let people login with email
        return new User("asd", "asd", null);
    }

    // This method is used by JWTAuthenticationFilter
    public UserDetails loadUserById(UUID id) {
        log.debug(userClient.greeting());
        return new User("asd", "asd", null);
    }

}
