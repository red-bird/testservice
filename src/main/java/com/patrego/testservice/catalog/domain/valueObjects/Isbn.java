package com.patrego.testservice.catalog.domain.valueObjects;

import org.apache.commons.validator.routines.*;

public record Isbn(String value) {
    private static final ISBNValidator VALIDATOR = new ISBNValidator();

    public Isbn {
        if (!VALIDATOR.isValid(value)) {
            throw new IllegalArgumentException("invalid isbn: " + value);
        }
    }
}
