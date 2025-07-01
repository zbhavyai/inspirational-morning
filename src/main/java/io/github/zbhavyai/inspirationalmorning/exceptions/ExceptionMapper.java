package io.github.zbhavyai.inspirationalmorning.exceptions;

import io.github.zbhavyai.inspirationalmorning.models.ErrorResponse;
import jakarta.ws.rs.ServiceUnavailableException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Provider
public class ExceptionMapper {

    @ServerExceptionMapper(ServiceUnavailableException.class)
    public Response handleServiceUnavailable(ServiceUnavailableException e) {

        return createErrorResponse(Response.Status.SERVICE_UNAVAILABLE);
    }

    @ServerExceptionMapper(WebApplicationException.class)
    public Response handleWebApplication(WebApplicationException e) {

        return createErrorResponse(e.getResponse().getStatusInfo().toEnum());
    }

    private static Response createErrorResponse(Response.Status status) {
        return Response.status(status).entity(ErrorResponse.create(status.getReasonPhrase())).build();
    }
}
