package io.lypsis.user.web;

import io.lypsis.user.services.UserService;
import io.lypsis.user.web.interfaces.UserController;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController {

    private UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String greeting() {
        return userService.greeting();
    }

}
