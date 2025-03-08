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
public class ProductTemplateClientDto {
    @Null(message = "Product Id cannot be set, it is generated automatically")
    private String id;

    @NotEmpty(message = "Product name must be specified")
    private String name;

    private String brand;
    private String imageUrl;
    private String description;

    @NotEmpty(message = "Product mus have a category")
    private String categoryName;
}
