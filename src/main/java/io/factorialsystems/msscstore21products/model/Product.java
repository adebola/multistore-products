package io.factorialsystems.msscstore21products.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private String id;
    private String productName;
    private String categoryId;
    private String categoryName;
    private String imageUrl;
    private Boolean disabled;
    private String createdBy;
    private Date createdOn;
    private String description;
    private String tenantId;

    @ToString.Exclude
    private List<ProductVariant> variants;

    @ToString.Exclude
    private List<Uom> uoms;
}
