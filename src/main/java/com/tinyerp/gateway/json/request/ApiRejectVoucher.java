package com.tinyerp.gateway.json.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.tinyerp.gateway.domain.RejectReason;
import com.tinyerp.gateway.domain.VoucherId;
import lombok.Value;

@Value
@JsonDeserialize(builder = ApiRejectVoucher.Builder.class)
public class ApiRejectVoucher {
    private final VoucherId id;
    private final RejectReason reason;

    @JsonCreator
    @lombok.Builder(builderClassName = "Builder", builderMethodName = "newBuilder", toBuilder = true)
    private ApiRejectVoucher(VoucherId id, RejectReason reason) {
        this.id = id;
        this.reason = reason;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
