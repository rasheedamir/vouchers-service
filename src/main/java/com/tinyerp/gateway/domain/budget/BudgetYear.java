package com.tinyerp.gateway.domain.budget;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString
@Getter
@JsonDeserialize(builder = BudgetYear.Builder.class)
public class BudgetYear {

    private final Year from;
    private final Year to;

    @lombok.Builder(builderClassName = "Builder", builderMethodName = "newBuilder", toBuilder = true)
    private BudgetYear(@NonNull Year from, @NonNull Year to) {
        this.from = from;
        this.to = to;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
