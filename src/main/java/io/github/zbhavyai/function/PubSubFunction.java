package io.github.zbhavyai.function;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.cloud.functions.CloudEventsFunction;

import io.cloudevents.CloudEvent;
import io.github.zbhavyai.service.GreetingsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("inspirational-morning-pubsub")
@ApplicationScoped
public class PubSubFunction implements CloudEventsFunction {

    private static final Logger LOGGER = Logger.getLogger(PubSubFunction.class.getSimpleName());

    private static final ObjectMapper mapper = new ObjectMapper();
    private final GreetingsService greetingService;

    @Inject
    public PubSubFunction(GreetingsService greetingService) {
        this.greetingService = greetingService;
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void accept(CloudEvent cloudEvent) throws Exception {
        LOGGER.infof("cloudEvent: eventId=\"%s\", subject=\"%s\", type=\"%s\", data=\"%s\"",
                cloudEvent.getId(),
                cloudEvent.getSubject(),
                cloudEvent.getType(),
                new String(cloudEvent.getData().toBytes()));

        LOGGER.info(mapper.writeValueAsString(cloudEvent));

        this.greetingService.greet().subscribe().with(v -> LOGGER.info("Greeting sent"));
    }
}
