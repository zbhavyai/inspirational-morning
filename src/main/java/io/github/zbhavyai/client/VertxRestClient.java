package io.github.zbhavyai.client;

import java.time.Duration;
import java.util.Map;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import io.github.zbhavyai.models.ErrorResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.MultiMap;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.HttpRequest;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import io.vertx.mutiny.ext.web.codec.BodyCodec;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class VertxRestClient {

    private static final Logger LOGGER = Logger.getLogger(VertxRestClient.class.getSimpleName());

    public final WebClient client;
    private final Duration timeout;

    @Inject
    public VertxRestClient(
            Vertx vertx,
            @ConfigProperty(name = "timeout.secs", defaultValue = "5") long timeout) {

        this.client = WebClient.create(vertx);
        this.timeout = Duration.ofSeconds(timeout);
    }

    public <T> Uni<Response> getRequest(
            String uri,
            Map<String, String> headers,
            Class<T> responseType) {
        LOGGER.infof("getRequest: uri=\"%s\"", uri);

        HttpRequest<T> req = this.client
                .getAbs(uri)
                .as(BodyCodec.json(responseType))
                .putHeaders(convertMapToMultiMap(headers));

        return req
                .send()
                .onItem().transform(r -> handleResponse(r))
                .onFailure().transform(t -> new WebApplicationException(
                        handleFailure(t.getMessage(), Status.INTERNAL_SERVER_ERROR)))
                .ifNoItem()
                .after(timeout)
                .failWith(new WebApplicationException("Request timeout", Status.GATEWAY_TIMEOUT));
    }

    public <T> Uni<Response> postRequest(
            String uri,
            Map<String, String> headers,
            JsonObject payload,
            Class<T> responseType) {
        LOGGER.infof("postRequest: uri=\"%s\", payload=\"%s\"",
                uri,
                payload);

        HttpRequest<T> req = this.client
                .postAbs(uri)
                .as(BodyCodec.json(responseType))
                .putHeaders(convertMapToMultiMap(headers));

        return req
                .sendJson(payload)
                .onItem().transform(r -> handleResponse(r))
                .onFailure().transform(t -> new WebApplicationException(
                        handleFailure(t.getLocalizedMessage(), Status.INTERNAL_SERVER_ERROR)))
                .ifNoItem()
                .after(timeout)
                .failWith(new WebApplicationException("Request timeout", Status.GATEWAY_TIMEOUT));
    }

    private <T> Response handleResponse(HttpResponse<T> res) {
        LOGGER.infof("handleResponse: status=\"%s\", body=\"%s\"",
                res.statusCode(),
                res.body());

        if (res.statusCode() >= 200 && res.statusCode() < 300) {
            return Response.status(res.statusCode()).entity(res.body()).build();
        } else {
            throw new WebApplicationException(res.body().toString(), res.statusCode());
        }
    }

    private Response handleFailure(String message, Status status) {
        LOGGER.errorf("handleFailure: status=\"%d\", error=\"%s\"",
                status.getStatusCode(),
                message);

        return Response.status(status).entity(new ErrorResponse(status, message)).build();
    }

    private MultiMap convertMapToMultiMap(final Map<String, String> obj) {
        return MultiMap.caseInsensitiveMultiMap().addAll(obj);
    }
}
