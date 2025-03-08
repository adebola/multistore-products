package io.factorialsystems.msscstore21products.mapper;


import io.factorialsystems.msscstore21products.dto.ProductClientDto;
import io.factorialsystems.msscstore21products.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface ProductMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "productName", target = "productName"),
            @Mapping(source = "sku", target = "sku"),
            @Mapping(source = "uomId", target = "uomId"),
            @Mapping(source = "uomName", target = "uomName"),
            @Mapping(source = "imageUrl", target = "imageUrl"),
            @Mapping(source = "productImageUrl", target = "productImageUrl"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "discount", target = "discount"),
            @Mapping(source = "extraDescription", target = "extraDescription"),
            @Mapping(source = "isNew", target = "isNew"),
            @Mapping(source = "sale", target = "sale"),
            @Mapping(source = "quantity", target = "quantity"),
            @Mapping(source = "variantId", target = "variantId"),
            @Mapping(source = "variantName", target = "variantName"),
            @Mapping(source = "variantOptionId", target = "variantOptionId"),
            @Mapping(source = "variantOptionName", target = "variantOptionName")
    })
    ProductClientDto toClientDto(Product product);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "productName", ignore = true),
            @Mapping(target = "imageUrl", ignore = true),
            @Mapping(source = "sku", target = "sku"),
            @Mapping(source = "uomId", target = "uomId"),
            @Mapping(source = "productImageUrl", target = "productImageUrl"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "discount", target = "discount"),
            @Mapping(source = "extraDescription", target = "extraDescription"),
            @Mapping(source = "isNew", target = "isNew"),
            @Mapping(source = "sale", target = "sale"),
            @Mapping(source = "quantity", target = "quantity"),
            @Mapping(source = "variantId", target = "variantId"),
            @Mapping(source = "variantName", target = "variantName"),
            @Mapping(source = "variantOptionId", target = "variantOptionId"),
            @Mapping(source = "variantOptionName", target = "variantOptionName")
    })
    Product toModel(ProductClientDto dto);
}
