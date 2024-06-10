package io.github.zbhavyai.models;

import java.time.ZonedDateTime;

public record GChatMsgPostResponse(
        String webhookName,
        String spaceName,
        ZonedDateTime messageCreateDateTime,
        String message,
        String threadRetensionPolicy) {
}
