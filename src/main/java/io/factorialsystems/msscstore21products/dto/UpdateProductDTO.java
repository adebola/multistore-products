package io.factorialsystems.msscstore21products.dto;

import jakarta.validation.constraints.NotEmpty;

public record UpdateProductDTO(@NotEmpty String id, String name, String description, String categoryName, Boolean disabled) { }

