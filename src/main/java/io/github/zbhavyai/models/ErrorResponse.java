package io.github.zbhavyai.models;

import jakarta.ws.rs.core.Response.Status;

public class ErrorResponse {

    private final Status status;
    private final int statusCode;
    private final String error;

    public ErrorResponse(Status status, String error) {
        this.status = status;
        this.statusCode = status.getStatusCode();
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public Status getStatus() {
        return status;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
