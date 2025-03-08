package io.factorialsystems.msscstore21products.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreProductDto {
    private String templateId;
    private String productName;
    private String brand;
    private String imageUrl;
    private String description;
    List<StoreProductVariantDto> variants;
}
