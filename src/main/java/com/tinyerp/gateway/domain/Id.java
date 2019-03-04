package com.tinyerp.gateway.domain;

import com.tinyerp.gateway.common.SimpleWrapper;
import lombok.NonNull;

import java.util.UUID;

public final class Id extends SimpleWrapper<UUID> {

    public Id(@NonNull final UUID value) {
        super(value);
    }

    public Id(@NonNull final String uuid) {
        super(UUID.fromString(uuid));
    }

    public static Id generate() {
        return new Id(UUID.randomUUID());
    }
}
