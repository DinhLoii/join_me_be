package com.join.me.modules.auth.controller;

import com.join.me.common.constants.ApiConstant;
import com.join.me.common.dtos.response.ApiResponse;
import com.join.me.modules.auth.dto.request.AuthenticationRequest;
import com.join.me.modules.auth.dto.request.IntrospectRequest;
import com.join.me.modules.auth.dto.response.AuthenticationResponse;
import com.join.me.modules.auth.dto.response.IntrospectResponse;
import com.join.me.modules.auth.service.api.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest request) {
        ApiResponse<AuthenticationResponse> response = ApiResponse.<AuthenticationResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(authenticationService.authenticate(request))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/introspect")
    ResponseEntity<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request) throws ParseException {
        ApiResponse<IntrospectResponse> response = ApiResponse.<IntrospectResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(authenticationService.introspect(request))
                .build();
        return ResponseEntity.ok(response);
    }
}
