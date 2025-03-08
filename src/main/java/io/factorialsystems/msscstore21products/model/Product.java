package io.factorialsystems.msscstore21products.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String id;
    private String templateId;
    private String sku;
    private String productName;
    private String uomId;
    private String uomName;
    private String imageUrl;
    private String productImageUrl;
    private BigDecimal price;
    private BigDecimal discount;
    private String extraDescription;
    private Boolean isNew;
    private Boolean sale;
    private Integer quantity;
    private String variantId;
    private String variantName;
    private String variantOptionId;
    private String variantOptionName;
    private Boolean suspended;
    private Boolean deleted;
    private String deletedBy;
    private Date deletedOn;
    private String createdBy;
}
