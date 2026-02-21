package com.vnatives.pricingdiscountservice.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Component
@Slf4j
public class HttpAccessLogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        long start = System.currentTimeMillis();
        try {
            chain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;

            log.info("HTTP_ACCESS",
                    org.slf4j.MarkerFactory.getMarker("ACCESS"),
                    kv("method", req.getMethod()),
                    kv("path", req.getRequestURI()),
                    kv("status", res.getStatus()),
                    kv("durationMs", duration)
            );
        }
    }
}
