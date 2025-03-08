package io.factorialsystems.msscstore21products.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantOptionClientDto {
    @Null(message = "Product Variant Id cannot be set, it is generated automatically")
    private String id;

    @NotEmpty(message = "Product Variant Name must be specified")
    private String name;
}
