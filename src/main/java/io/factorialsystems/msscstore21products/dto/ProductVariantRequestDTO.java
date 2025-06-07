package io.factorialsystems.msscstore21products.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductVariantRequestDTO {
    private String id;
    private String name;
    List<ProductVariantOptionRequestDTO> options;
}
