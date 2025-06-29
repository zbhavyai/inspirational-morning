package io.github.zbhavyai.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record ZenQuote(
    @JsonProperty("author") String author,
    @JsonProperty("quote") String quote) {

    public static ZenQuote fallbackQuote() {
        return new ZenQuote("", "");
    }
}
