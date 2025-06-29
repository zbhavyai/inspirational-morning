package io.github.zbhavyai.function;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import io.github.zbhavyai.service.GreetingsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response.Status;

import java.io.IOException;

@Named("inspirational-morning-http")
@ApplicationScoped
public class HTTPFunctionImpl implements HttpFunction {

    private final GreetingsService greetingService;

    @Inject
    public HTTPFunctionImpl(GreetingsService greetingService) {
        this.greetingService = greetingService;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        response.getWriter().append("Hello, World!");

        this.greetingService.greet()
            .onItem().invoke(r -> {
                try {
                    response.getWriter().append(r.text());
                    response.setStatusCode(200);
                } catch (IOException e) {
                    response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
                }
            })
            .onFailure().recoverWithNull()
            .await().indefinitely();
    }
}
