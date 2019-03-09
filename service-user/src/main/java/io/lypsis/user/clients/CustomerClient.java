package io.lypsis.user.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("LypsisCustomerService")
public interface CustomerClient {

    @RequestMapping("/greeting")
    String greeting();

}
