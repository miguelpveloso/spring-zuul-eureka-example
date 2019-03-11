package io.lypsis.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("LypsisUserService")
public interface UserClient {

    @RequestMapping("/greeting")
    String greeting();

}
