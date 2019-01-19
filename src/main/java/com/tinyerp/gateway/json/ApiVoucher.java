package com.tinyerp.gateway.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.NonNull;
import lombok.Value;

@SuppressWarnings("unused")
@Value
@JsonDeserialize(builder = ApiVoucher.Builder.class)
public final class ApiVoucher {
    private final Long id;
    private final String description;

    @JsonCreator
    @lombok.Builder(builderClassName = "Builder", builderMethodName = "newBuilder", toBuilder = true)
    private ApiVoucher(@NonNull Long id, String description) {
        this.id = id;
        this.description = description;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
