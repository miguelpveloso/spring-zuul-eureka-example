package io.lypsis.gateway.exceptions;

import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class RoleUnauthorizedException extends ZuulException {

    public RoleUnauthorizedException(String e) {
        super("Role unauthorized exception", HttpStatus.UNAUTHORIZED.value(), e);
    }

}
