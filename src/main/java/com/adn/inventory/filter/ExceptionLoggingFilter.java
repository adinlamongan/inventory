package com.adn.inventory.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class ExceptionLoggingFilter extends OncePerRequestFilter implements OrderedFilter {
    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public int getOrder() {
        return OrderedFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        filterChain.doFilter(request, response);

        //logError(request, response);
    }

    private void logError(HttpServletRequest request, HttpServletResponse response) {
        Throwable error = errorAttributes.getError(new ServletWebRequest(request));
        if (error == null) {
            return;
        }

        int statusCode = response.getStatus();
        String reasonPhrase = Optional.ofNullable(HttpStatus.resolve(statusCode))
                .map(HttpStatus::getReasonPhrase).orElse("<nonstandard status code>");

        String uriString = request.getRequestURI()
                + Optional.ofNullable(request.getQueryString()).map(q -> "?" + q).orElse("");

        HttpStatus.Series series = HttpStatus.Series.resolve(statusCode);
        if (HttpStatus.Series.SERVER_ERROR.equals(series)) {
            log.warn("{} {} response for {}", statusCode, reasonPhrase, uriString, error);
        } else if (HttpStatus.Series.CLIENT_ERROR.equals(series)){
            log.debug("{} {} response for {}", statusCode, reasonPhrase, uriString, error);
        }
    }
}
