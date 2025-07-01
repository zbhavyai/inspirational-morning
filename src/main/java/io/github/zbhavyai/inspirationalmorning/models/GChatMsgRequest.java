package io.github.zbhavyai.inspirationalmorning.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record GChatMsgRequest(@JsonProperty("text") String message) {
}
