package io.factorialsystems.msscstore21products.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {
    @NotEmpty(message = "The Product name must be specified")
    private String name;

    @NotEmpty(message = "The Product template must be specified")
    private String productTemplateId;

    @NotEmpty(message = "The Unit of Measure must be specified")
    private String uomId;

    @NotNull
    private BigDecimal price;

    @NotEmpty
    private String skuCode;

    private String variantId;
    private String variantOptionId;
    private BigDecimal discount;
    private String imageUrl;
    private String description;
    private Boolean isNew;
    private Boolean sale;
    private Integer quantity;
}
