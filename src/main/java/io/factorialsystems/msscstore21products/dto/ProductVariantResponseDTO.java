package io.factorialsystems.msscstore21products.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductVariantResponseDTO {
    private String id;
    private String name;
    private Boolean disabled;
    private List<ProductVariantOptionResponseDTO> options;

}
