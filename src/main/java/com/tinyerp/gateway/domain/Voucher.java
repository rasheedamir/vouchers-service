package com.tinyerp.gateway.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
@ToString
@Getter
@JsonDeserialize(builder = Voucher.Builder.class)
@Entity
@Table(name = "voucher")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Entity unique id
     */
    @Id
    private final Long id;

    /**
     * The description of the voucher
     */
    @Column(name = "description")
    private final String description;

    @lombok.Builder(builderClassName = "Builder", builderMethodName = "newBuilder", toBuilder = true)
    private Voucher(@NonNull Long id, @NonNull String description) {
        this.id = id;
        this.description = description;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
