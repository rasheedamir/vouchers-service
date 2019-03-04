package com.tinyerp.gateway.domain;

import com.tinyerp.gateway.common.SimpleWrapper;
import lombok.NonNull;

public final class Name extends SimpleWrapper<String> {

    public Name(@NonNull final String value) {
        super(value);
    }
}
