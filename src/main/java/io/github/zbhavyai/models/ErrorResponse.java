package io.github.zbhavyai.models;

import jakarta.ws.rs.core.Response.Status;

public record ErrorResponse(
        Status status,
        String error) {
}
