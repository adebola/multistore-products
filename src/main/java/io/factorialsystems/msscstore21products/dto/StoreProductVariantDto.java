package io.factorialsystems.msscstore21products.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreProductVariantDto {
    private String id;
    private String sku;
    private String uomId;
    private String uomName;
    private String productImageUrl;
    private BigDecimal price;
    private BigDecimal discount;
    private String extraDescription;
    private Boolean isNew;
    private Boolean sale;
    private String variantId;
    private String variantName;
    private String variantOptionId;
    private String variantOptionName;
}
