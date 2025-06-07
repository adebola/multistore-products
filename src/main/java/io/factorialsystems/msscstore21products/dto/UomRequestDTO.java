package io.factorialsystems.msscstore21products.dto;

import jakarta.validation.constraints.NotEmpty;

public record UomRequestDTO(String id, @NotEmpty String name) { }