package io.lypsis.commons.filters;

import io.lypsis.commons.interceptors.RequestProcessingTimeInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class HttpsEnforcerFilter implements Filter {

    private static final String HTTPS_PROTOCOL = "https";

    public static final String X_FORWARDED_PROTO = "x-forwarded-proto";

    private final boolean sslEnabledProperty;

    public HttpsEnforcerFilter(boolean sslEnabledProperty) {
        this.sslEnabledProperty = sslEnabledProperty;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.debug("Filtering https protocol....");

        if (Boolean.TRUE.equals(sslEnabledProperty)) {

            log.debug("SSL Enabled true");

            if (request.getHeader(X_FORWARDED_PROTO) != null) {

                log.debug("Header X_FORWARDED_PROTO found");

                if (request.getHeader(X_FORWARDED_PROTO).indexOf(HTTPS_PROTOCOL) != 0) {
                    sendRedirectHttps(request, response);
                    return;
                }

            } else if (!HTTPS_PROTOCOL.equalsIgnoreCase(request.getScheme())) {

                log.debug("HTTPS_PROTOCOL not found");

                sendRedirectHttps(request, response);
                return;
            }

            log.debug("HTTPS_PROTOCOL found");
        }

        filterChain.doFilter(request, response);
    }

    private void sendRedirectHttps(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.debug("logging request details ....");
        RequestProcessingTimeInterceptor.logProcessingRequestDetails(request, response);

        final String origin = request.getHeader(HttpHeaders.ORIGIN);
        log.debug("Origin {}", origin);
        final String referer = request.getHeader(HttpHeaders.REFERER);
        log.debug("Referer {}", origin);
        final String host = request.getHeader(HttpHeaders.HOST);
        log.debug("Host {}", host);
        final String serverName = request.getServerName();
        log.debug("Server Name {}", serverName);

        String secureUrl = HTTPS_PROTOCOL + "://";

        if (StringUtils.hasText(origin)) {
            secureUrl += getAuthorityName(origin);
        } else if (StringUtils.hasText(referer)) {
            secureUrl += getAuthorityName(referer);
        } else if (StringUtils.hasText(host)) {
            secureUrl += host;
        } else {
            secureUrl += serverName;
        }

        secureUrl += "/";

        log.info("Secure protocol required. Send response redirect to {}", secureUrl);

        response.sendRedirect(secureUrl);
    }

    private String getAuthorityName(final String requestedUri) throws MalformedURLException {
        URL url = new URL(requestedUri);
        return url.getAuthority();
    }

    @Override
    public void destroy() { }
}
