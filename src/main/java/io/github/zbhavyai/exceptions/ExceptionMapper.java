package io.github.zbhavyai.exceptions;

import io.github.zbhavyai.models.ErrorResponse;
import jakarta.ws.rs.ServiceUnavailableException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Provider
public class ExceptionMapper {

    @ServerExceptionMapper(ServiceUnavailableException.class)
    public Response handleServiceUnavailable(ServiceUnavailableException e) {
        return Response
            .status(Response.Status.SERVICE_UNAVAILABLE)
            .entity(ErrorResponse.create(Response.Status.SERVICE_UNAVAILABLE.getReasonPhrase())).build();
    }

    @ServerExceptionMapper(WebApplicationException.class)
    public Response handleServiceUnavailable(WebApplicationException e) {
        return Response
            .status(Response.Status.TOO_MANY_REQUESTS)
            .entity(ErrorResponse.create(Response.Status.TOO_MANY_REQUESTS.getReasonPhrase())).build();
    }
}
