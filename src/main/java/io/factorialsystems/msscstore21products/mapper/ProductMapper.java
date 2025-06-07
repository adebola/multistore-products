package io.factorialsystems.msscstore21products.mapper;


import io.factorialsystems.msscstore21products.dto.*;
import io.factorialsystems.msscstore21products.model.Product;
import io.factorialsystems.msscstore21products.model.ProductVariant;
import io.factorialsystems.msscstore21products.model.ProductVariantOption;
import io.factorialsystems.msscstore21products.model.Uom;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "productName", target = "name"),
            @Mapping(source = "imageUrl", target = "imageUrl"),
            @Mapping(source = "categoryId", target = "categoryId"),
            @Mapping(source = "categoryName", target = "categoryName"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "createdBy", target = "createdBy"),
            @Mapping(source = "createdOn", target = "createdOn"),
            @Mapping(source = "disabled", target = "disabled"),
            @Mapping(source = "uoms", target = "uoms"),
            @Mapping(source = "variants", target = "variants")
    })
    ProductResponseDTO toProductResponseDTO(Product product);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "productName", target = "name"),
            @Mapping(source = "imageUrl", target = "imageUrl"),
            @Mapping(source = "categoryId", target = "categoryId"),
            @Mapping(source = "categoryName", target = "categoryName"),
            @Mapping(source = "description", target = "description"),
            @Mapping(target = "uoms", ignore = true),
            @Mapping(target = "variants", ignore = true)
    })
    ProductResponseDTO toProductResponseDTOFlat(Product product);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
    })
    UomResponseDTO toUomResponseDTO(Uom uom);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
    })
    ProductVariantResponseDTO toVariantResponseDTO(ProductVariant pv);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "disabled", target = "disabled"),
    })
    ProductVariantOptionResponseDTO toVariantOptionResponseDTO(ProductVariantOption pvo);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "productName"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "disabled", target = "disabled"),
            @Mapping(target = "categoryName", ignore = true),
    })
    Product toProductFromUpdateDTO(UpdateProductDTO dto);
}