package io.github.zbhavyai.function;

import org.jboss.logging.Logger;

import com.google.cloud.functions.CloudEventsFunction;

import io.cloudevents.CloudEvent;
import io.github.zbhavyai.service.GreetingsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("inspirational-morning-pubsub")
@ApplicationScoped
public class PubSubFunctionImpl implements CloudEventsFunction {

    private static final Logger LOGGER = Logger.getLogger(PubSubFunctionImpl.class.getSimpleName());

    private final GreetingsService greetingService;

    @Inject
    public PubSubFunctionImpl(GreetingsService greetingService) {
        this.greetingService = greetingService;
    }

    @Override
    public void accept(CloudEvent cloudEvent) throws Exception {
        LOGGER.infof("cloudEvent: eventId=\"%s\", subject=\"%s\", type=\"%s\", data=\"%s\"",
                cloudEvent.getId(),
                cloudEvent.getSubject(),
                cloudEvent.getType(),
                new String(cloudEvent.getData().toBytes()));

        this.greetingService.greet().await().indefinitely();
    }
}
