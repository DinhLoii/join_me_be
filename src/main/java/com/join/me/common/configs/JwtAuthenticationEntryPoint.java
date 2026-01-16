package com.join.me.common.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.join.me.common.constants.ApiConstant;
import com.join.me.common.dtos.ErrorMessage;
import com.join.me.common.dtos.response.ApiResponse;
import com.join.me.common.enums.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.AUTHZ_UNAUTHORIZED;
        ApiResponse<?> genericResponse = ApiResponse.builder()
                .success(ApiConstant.FAILURE)
                .errorMessage(ErrorMessage.builder()
                        .errorCode(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCode.getHttpStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(genericResponse));
        response.flushBuffer();
    }
}
