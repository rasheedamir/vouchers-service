package com.tinyerp.gateway.json.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.NonNull;
import lombok.Value;

@SuppressWarnings("unused")
@Value
@JsonDeserialize(builder = ApiVoucherResponse.Builder.class)
public final class ApiVoucherResponse {
    private final Long id;
    private final String description;
    private final String processId;
    private final String processStatus;

    @JsonCreator
    @lombok.Builder(builderClassName = "Builder", builderMethodName = "newBuilder", toBuilder = true)
    private ApiVoucherResponse(Long id, String description, String processId, String processStatus) {
        this.id = id;
        this.description = description;
        this.processId = processId;
        this.processStatus = processStatus;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
