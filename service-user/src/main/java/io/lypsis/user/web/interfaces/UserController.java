package io.lypsis.customer.web.interfaces;

import org.springframework.web.bind.annotation.RequestMapping;

public interface CustomerController {

    @RequestMapping("/greeting")
    String greeting();


}
