package com.tinyerp.gateway.domain.budget;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.tinyerp.gateway.domain.Id;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonDeserialize(builder = Budget.Builder.class)
public class Budget {

    private final Id id;
    private final BudgetAmount amount;
    private final BudgetYear year;
    private final Id departmentId;

    @lombok.Builder(builderClassName = "Builder", builderMethodName = "newBuilder", toBuilder = true)
    private Budget(Id id, BudgetAmount amount, BudgetYear year, Id departmentId) {
        this.id = id;
        this.amount = amount;
        this.year = year;
        this.departmentId = departmentId;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }

}
