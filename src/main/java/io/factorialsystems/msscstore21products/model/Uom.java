package io.factorialsystems.msscstore21products.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Uom {
    private String id;
    private String name;
    private Date createdOn;
    private String createdBy;
    private Boolean disabled;
    private String tenantId;
}
