package io.lypsis.commons.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

public class RequestProcessingTimeInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestProcessingTimeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (LOGGER.isInfoEnabled() && request != null) {
            long startTime = System.currentTimeMillis();
            LOGGER.debug("[Start call] {} {}", request.getMethod(), request.getRequestURL().toString());
            request.setAttribute("startTime", startTime);
        }
        // if returned false, we need to make sure 'response' is sent
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (LOGGER.isInfoEnabled() && request != null && request.getAttribute("startTime") != null) {
            Long startTime = (Long) request.getAttribute("startTime");
            long requestTime = System.currentTimeMillis() - startTime.longValue();
            LOGGER.info("[Call took {}ms] {} {}", requestTime, request.getMethod(), request.getRequestURL().toString());
            LOGGER.debug("logging request details ....");
            logProcessingRequestDetails(request, response);
        }
    }

    public static void logProcessingRequestDetails(HttpServletRequest request, HttpServletResponse response) {

        if (LOGGER.isInfoEnabled() && request != null && response != null) {
            LOGGER.debug("[Call details] {} {}, requested query string: {}, requested content length: {}", request.getMethod(),
                    request.getRequestURL().toString(),
                    request.getQueryString(),
                    request.getContentLength());
            if (request.getHeaderNames() != null && request.getHeaderNames().hasMoreElements()) {
                LOGGER.debug("     Request headers: ");
                Enumeration<String> headers = request.getHeaderNames();
                while (headers.hasMoreElements()) {
                    final String headerName = headers.nextElement();
                    final String headerValue = request.getHeader(headerName);
                    LOGGER.debug("          " + headerName + " = " + headerValue);
                }
            }
            if (request.getParameterMap().size() > 0) {
                LOGGER.debug("     Request parameters: ");
                for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
                    if (entry.getValue() != null && entry.getValue().length > 0) {
                        LOGGER.debug("          " + entry.getKey() + " = " + entry.getValue()[0]);
                    }
                }
            }
        }
    }
}
