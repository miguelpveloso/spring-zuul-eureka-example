package io.lypsis.user.web.interfaces;

import org.springframework.web.bind.annotation.RequestMapping;

public interface UserController {

    @RequestMapping("/greeting")
    String greeting();


}
