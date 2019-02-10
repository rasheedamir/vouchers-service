package com.tinyerp.gateway.json.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.tinyerp.gateway.domain.VoucherDescription;
import com.tinyerp.gateway.domain.VoucherId;
import lombok.Value;

@SuppressWarnings("unused")
@Value
@JsonDeserialize(builder = ApiVoucherResponse.Builder.class)
public final class ApiVoucherResponse {
    private final VoucherId id;
    private final VoucherDescription description;

    @JsonCreator
    @lombok.Builder(builderClassName = "Builder", builderMethodName = "newBuilder", toBuilder = true)
    private ApiVoucherResponse(VoucherId id, VoucherDescription description) {
        this.id = id;
        this.description = description;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
