package io.github.zbhavyai.function;

import java.io.IOException;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import io.github.zbhavyai.service.GreetingsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response.Status;

@Named("inspirational-morning-http")
@ApplicationScoped
public class HTTPTriggeredFunction implements HttpFunction {

    private static final Logger LOGGER = Logger.getLogger(HTTPTriggeredFunction.class.getSimpleName());

    private static final ObjectMapper mapper = new ObjectMapper();
    private final GreetingsService greetingService;

    @Inject
    public HTTPTriggeredFunction(GreetingsService greetingService) {
        this.greetingService = greetingService;
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        LOGGER.infof("request: \"%s\"", mapper.writeValueAsString(request));

        response.getWriter().append("Hello, World!");

        this.greetingService.greet()
                .onItem().invoke(r -> {
                    try {
                        response.getWriter().append(r.message());
                        response.setStatusCode(200);
                    } catch (IOException e) {
                        response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
                    }
                })
                .onFailure().recoverWithNull()
                .subscribe().with(v -> LOGGER.info("Greeting sent"));
    }
}
