package io.factorialsystems.msscstore21products.mapper;


import io.factorialsystems.msscstore21products.dto.ProductVariantOptionClientDto;
import io.factorialsystems.msscstore21products.model.ProductVariantOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface ProductVariantOptionMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
    })
    ProductVariantOptionClientDto toClientDto(ProductVariantOption variant);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
    })
    ProductVariantOption toModel(ProductVariantOptionClientDto dto);
}
