package com.tinyerp.gateway.domain;

import java.util.UUID;

public final class VoucherId extends SimpleWrapper<UUID> {

    public VoucherId(final UUID value) {
        super(value);
    }

    public VoucherId(final String uuid) {
        super(UUID.fromString(uuid));
    }

    public static VoucherId generate() {
        return new VoucherId(UUID.randomUUID());
    }
}
