package io.github.zbhavyai.service;

import io.github.zbhavyai.client.ZenquoteClient;
import io.github.zbhavyai.client.ZenquoteParser;
import io.github.zbhavyai.models.Zenquote;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ZenquoteService {

    private static final Logger LOGGER = Logger.getLogger(ZenquoteService.class.getSimpleName());

    private final ZenquoteClient zenClient;
    private final ZenquoteParser parser;

    @Inject
    public ZenquoteService(@RestClient ZenquoteClient zenClient, ZenquoteParser parser) {
        this.zenClient = zenClient;
        this.parser = parser;
    }

    public Uni<Zenquote> getTodayQuote() {
        LOGGER.infof("getTodayQuote");
        return this.zenClient.getTodayQuote().onItem().transform(parser::parseZenquoteResponse);
    }

    public Uni<Zenquote> getRandomQuote() {
        LOGGER.infof("getRandomQuote");

        return this.zenClient.getRandomQuote().onItem().transform(parser::parseZenquoteResponse);
    }
}
