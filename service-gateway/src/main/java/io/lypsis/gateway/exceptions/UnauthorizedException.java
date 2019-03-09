package io.lypsis.gateway.exceptions;

import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends ZuulException {

    public UnauthorizedException(String e) {
        super("Unauthorized exception", HttpStatus.UNAUTHORIZED.value(), e);
    }

}
