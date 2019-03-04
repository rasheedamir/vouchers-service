package com.tinyerp.gateway.domain.department;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.tinyerp.gateway.domain.Id;
import com.tinyerp.gateway.domain.Name;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonDeserialize(builder = Department.Builder.class)
public class Department {

    private final Id id;
    private final Name name;

    @lombok.Builder(builderClassName = "Builder", builderMethodName = "newBuilder", toBuilder = true)
    private Department(Id id, Name name) {
        this.id = id;
        this.name = name;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
