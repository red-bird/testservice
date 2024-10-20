package com.patrego.testservice.common.domain.valueObjects;

import java.util.Objects;
import java.util.UUID;

public record Id(UUID id) {

    public Id {
        Objects.requireNonNull(id, "id must not be null");
    }

    public Id() {
        this(UUID.randomUUID());
    }

}
