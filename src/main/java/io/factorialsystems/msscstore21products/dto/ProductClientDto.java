package io.factorialsystems.msscstore21products.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductClientDto {
    private String id;
    private String sku;
    private String productName;
    private String brand;
    private String uomId;
    private String uomName;
    private String imageUrl;
    private String productImageUrl;
    private BigDecimal price;
    private BigDecimal discount;
    private String description;
    private String extraDescription;
    private Boolean isNew;
    private Boolean sale;
    private Integer quantity;
    private String variantId;
    private String variantName;
    private String variantOptionId;
    private String variantOptionName;
}
