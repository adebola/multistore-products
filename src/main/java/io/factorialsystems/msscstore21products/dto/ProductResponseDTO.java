package io.factorialsystems.msscstore21products.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private String id;
    private String name;
    private String brand;
    private String imageUrl;
    private String categoryId;
    private String categoryName;
    private String description;
    private String createdBy;
    private Instant createdOn;
    private Boolean disabled;
    private List<UomResponseDTO> uoms;
    private List<ProductVariantResponseDTO> variants;
}
