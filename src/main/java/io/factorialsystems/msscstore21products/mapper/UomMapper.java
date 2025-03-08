package io.factorialsystems.msscstore21products.mapper;


import io.factorialsystems.msscstore21products.dto.UomClientDto;
import io.factorialsystems.msscstore21products.model.Uom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface UomMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name")
    })
    UomClientDto toClientDto(Uom uom);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name")
    })
    Uom toModel(UomClientDto uomClientDto);
}
