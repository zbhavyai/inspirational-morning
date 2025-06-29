package io.github.zbhavyai.service;

import io.github.zbhavyai.models.GChatMsgResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class GreetingsService {

    private final WeekdayService weekdayService;
    private final ZenquoteService zenquoteService;
    private final GChatPostService gchatService;
    private final String zoneid;

    @Inject
    public GreetingsService(
        WeekdayService weekdayService,
        ZenquoteService zenquoteService,
        GChatPostService gchatService,
        @ConfigProperty(name = "zoneid") String zoneid) {

        this.weekdayService = weekdayService;
        this.zenquoteService = zenquoteService;
        this.gchatService = gchatService;
        this.zoneid = zoneid;
    }

    public Uni<GChatMsgResponse> greet() {
        return this.zenquoteService.getRandomQuote()
            .chain(quote -> this.gchatService.postMessageToGChat(
                this.createGreetMessage(
                    this.weekdayService.getWeekDay(zoneid),
                    quote.quote(),
                    quote.author())));
    }

    private String createGreetMessage(String weekday, String quote, String author) {
        StringBuilder sb = new StringBuilder();

        sb.append("Good Morning");

        if (weekday == null || weekday.isBlank()) {
            // ignore empty weekday
        } else {
            sb.append(", ");
            sb.append(weekday);
        }

        sb.append("!");

        if (quote == null || author == null || quote.isBlank() || author.isBlank()) {
            // ignore empty quote and author
        } else {
            sb.append("\n\n");
            sb.append(quote);
            sb.append("\n");
            sb.append("-- ");
            sb.append(author);

        }

        return sb.toString();
    }
}
