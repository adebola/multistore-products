package io.factorialsystems.msscstore21products.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantOption {
    private String id;
    private String name;
    private Date createdOn;
    private String createdBy;
    private Boolean suspended;
    private Boolean deleted;
    private String deletedBy;
    private Date deletedOn;
}
