package io.github.zbhavyai.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record GChatMsgResponse(
    @JsonProperty("name") String name,
    @JsonProperty("text") String text,
    @JsonProperty("thread") String thread,
    @JsonProperty("space") String space) {

    public static GChatMsgResponse fallbackResponse() {
        return new GChatMsgResponse(
            "",
            "",
            "",
            "");
    }
}
