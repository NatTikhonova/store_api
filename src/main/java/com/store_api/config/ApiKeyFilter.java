package com.store_api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {
    private static final String API_KEY_HEADER_NAME = "X-API-KEY";
    private static final String API_KEY = "6efd363e-tsmh-8551-r9d9-19aa7a9ef035";

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws IOException, ServletException {

        String apiKey = request.getHeader(API_KEY_HEADER_NAME);

        if (apiKey == null || !apiKey.equals(API_KEY)) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext()
                .setAuthentication(new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES));
        filterChain.doFilter(request, response);
    }
}
