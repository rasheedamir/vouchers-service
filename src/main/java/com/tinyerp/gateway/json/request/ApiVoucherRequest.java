package com.tinyerp.gateway.json.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.NonNull;
import lombok.Value;

@SuppressWarnings("unused")
@Value
@JsonDeserialize(builder = ApiVoucherRequest.Builder.class)
public final class ApiVoucherRequest {
    private final String description;

    @JsonCreator
    @lombok.Builder(builderClassName = "Builder", builderMethodName = "newBuilder", toBuilder = true)
    private ApiVoucherRequest(@NonNull String description) {
        this.description = description;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
