package io.factorialsystems.msscstore21products.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDTO {
    @NotEmpty(message = "The Product name must be specified")
    private String name;

    @NotEmpty
    private String categoryName;

    @NotEmpty
    private String description;

    List<UomRequestDTO> uoms;
    List<ProductVariantRequestDTO> variants;

}
