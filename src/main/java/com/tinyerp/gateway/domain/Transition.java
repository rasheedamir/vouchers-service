package com.tinyerp.gateway.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode(of = "id")
@ToString
@Getter
@JsonDeserialize(builder = Transition.Builder.class)
public class Transition {

    private final VoucherId id;

    private final VoucherState from;

    private final VoucherState to;

    // Add datetime
    // Add user

    @lombok.Builder(builderClassName = "Builder", builderMethodName = "newBuilder", toBuilder = true)
    private Transition(VoucherId id, VoucherState from, VoucherState to) {
        this.id = id;
        this.from = from;
        this.to = to;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }

}
