package io.factorialsystems.msscstore21products.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantOptionMapping {
    private String productId;
    private String productVariantId;
    private String productVariantOptionId;
}
