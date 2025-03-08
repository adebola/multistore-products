package io.factorialsystems.msscstore21products.mapper;


import io.factorialsystems.msscstore21products.dto.ProductVariantClientDto;
import io.factorialsystems.msscstore21products.model.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface ProductVariantMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
    })
    ProductVariantClientDto toClientDto(ProductVariant variant);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
    })
    ProductVariant toModel(ProductVariantClientDto dto);
}
