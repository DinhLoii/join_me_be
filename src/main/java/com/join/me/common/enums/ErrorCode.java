package com.join.me.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    SERVER_UNCATEGORIZED_EXCEPTION("SERVER_9999", "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    // ==== AUTHENTICATION ERRORS ====
    AUTH_UNAUTHENTICATED("AUTH_1000", "Unauthenticated", HttpStatus.UNAUTHORIZED),
    AUTH_MISSING_TOKEN("AUTH_1001", "Client missing token", HttpStatus.BAD_REQUEST),
    AUTH_GENERATION_FAIL("AUTH_1002", "Generation JWT fail", HttpStatus.INTERNAL_SERVER_ERROR),
    JWT_CLAIM_MISSING("AUTH_1003", "JWT claim is missing or invalid", HttpStatus.UNAUTHORIZED),

    // ==== USER ERRORS ====
    USER_USERNAME_NOT_FOUND("USER_1000", "User not found with given username", HttpStatus.NOT_FOUND),

    // ==== ROLE ERRORS ====
    ROLE_NOT_FOUND("ROLE_1000", "Role not found", HttpStatus.NOT_FOUND),
    ROLE_NAME_EXISTED("ROLE_1001", "Role name existed", HttpStatus.BAD_REQUEST),

    // ==== PERMISSION ERRORS ====
    PERMISSION_NOT_FOUND("PERM_1000", "Permission not found", HttpStatus.NOT_FOUND),

    // ==== AUTHORIZATION ERRORS ====
    AUTHZ_UNAUTHORIZED("AUTHZ_1000", "You do not have permission", HttpStatus.FORBIDDEN),

    // ==== PRODUCT ERRORS ====
    PRODUCT_NOT_FOUND("PROD_1000", "Product not found", HttpStatus.NOT_FOUND),

    // ==== SIZE ERRORS ====
    SIZE_NOT_FOUND("SIZE_1000", "Size not found", HttpStatus.NOT_FOUND),

    // ==== WEBSOCKET ERRORS ====
    WEBSOCKET_UNAUTHENTICATED("WEBSOCKET_1000", "WebSocket connection not authenticated", HttpStatus.UNAUTHORIZED),
    WEBSOCKET_INVALID_MESSAGE("WEBSOCKET_1001", "Invalid WebSocket message format", HttpStatus.BAD_REQUEST),
    WEBSOCKET_CONVERSATION_NOT_FOUND("WEBSOCKET_1002", "Conversation not found", HttpStatus.NOT_FOUND),
    WEBSOCKET_UNAUTHORIZED("WEBSOCKET_1003", "User not authorized for this conversation", HttpStatus.FORBIDDEN),
    WEBSOCKET_SESSION_NOT_FOUND("WEBSOCKET_1004", "WebSocket session not found", HttpStatus.NOT_FOUND),
    WEBSOCKET_INVALID_TOKEN("WEBSOCKET_1005", "Invalid WebSocket authentication token", HttpStatus.UNAUTHORIZED),
    WEBSOCKET_MESSAGE_SEND_FAILED("WEBSOCKET_1006", "Failed to send WebSocket message", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    String code;
    String message;
    HttpStatusCode httpStatusCode;
}
