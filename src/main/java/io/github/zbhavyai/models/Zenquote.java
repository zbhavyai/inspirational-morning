package io.github.zbhavyai.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record Zenquote(
    @JsonProperty("author") String author,
    @JsonProperty("quote") String quote) {

    public static Zenquote fallbackQuote() {
        return new Zenquote("", "");
    }
}
