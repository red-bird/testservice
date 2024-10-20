package com.patrego.testservice.catalog.application.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String id,

        @Schema(example = "Fahrenheit 451")
        @NotBlank
        @Size(min = 1)
        String title,

        @Schema(example = "978-5-04-102293-8")
        @NotBlank
        @Size(min = 13, max = 31)
        String isbn
) {
}
