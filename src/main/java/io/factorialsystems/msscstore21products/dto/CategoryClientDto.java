package io.factorialsystems.msscstore21products.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryClientDto {
    @Null(message = "Product Id cannot be set, it is generated automatically")
    private String id;

    @NotEmpty(message = "Category name must be specified")
    private String name;
    private String imageUrl;
    private Boolean disabled;
    private String createdBy;
    private Instant createdOn;

    @NotEmpty
    private String description;
}
