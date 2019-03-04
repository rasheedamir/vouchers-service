package com.tinyerp.gateway.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.io.Serializable;

@EqualsAndHashCode(of = "id")
@ToString
@Getter
@JsonDeserialize(builder = Voucher.Builder.class)
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique id of the voucher
     */
    private final VoucherId id;

    /**
     * The description of the voucher
     */
    private final VoucherDescription description;

    private final VoucherState state;

    // Add 1-to-M voucher transitions or maybe it's isn't needed?

    @lombok.Builder(builderClassName = "Builder", builderMethodName = "newBuilder", toBuilder = true)
    private Voucher(@NonNull final VoucherId id, @NonNull final VoucherDescription description,
                    @NonNull final VoucherState state) {
        this.id = id;
        this.description = description;
        this.state = state;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
