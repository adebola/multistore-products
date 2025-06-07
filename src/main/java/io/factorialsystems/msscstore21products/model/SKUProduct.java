package io.factorialsystems.msscstore21products.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class SKUProduct {
    private String id;
    private String sku;
    private String productId;
    private String productName;
    private String productVariantId;
    private String productVariantName;
    private String productVariantOptionId;
    private String productVariantOptionName;
    private String uomId;
    private String uomName;
    private BigDecimal price;
    private BigDecimal quantity;
    private Boolean isNew;
    private Boolean isOnSale;
    private Instant createdOn;
    private String createdBy;
    private Boolean disabled;
    private String tenantId;
}
