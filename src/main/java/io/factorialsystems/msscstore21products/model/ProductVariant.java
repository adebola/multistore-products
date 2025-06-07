package io.factorialsystems.msscstore21products.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariant {
    private String id;
    private String name;
    private Date createdOn;
    private String createdBy;
    private Boolean disabled;
    private String productId;
    private List<ProductVariantOption> options;
}
