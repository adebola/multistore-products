package io.factorialsystems.msscstore21products.dto;

import jakarta.validation.constraints.NotEmpty;

public record ProductVariantOptionRequestDTO(@NotEmpty String name) {
}
