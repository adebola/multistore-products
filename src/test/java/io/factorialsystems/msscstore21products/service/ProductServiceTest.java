package io.factorialsystems.msscstore21products.service;


import io.factorialsystems.msscstore21products.dto.PagedDto;
import io.factorialsystems.msscstore21products.dto.StoreProductDto;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CommonsLog
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void createProduct() {
//        final String templateId = "a15a9496-f224-11ed-a880-e4ce5d66e85a";
//        final String uomId = "8c963e58-f223-11ed-a880-e4ce5d66e85a";
//        final String productVariantId = "af28dcfa-f223-11ed-a880-e4ce5d66e85a";
//        final String productVariantOptionId = "d817b1c2-f223-11ed-a880-e4ce5d66e85a";
//
//        CreateProductDto dto = new CreateProductDto();
//        dto.setDescription("Ralph T-Shirt");
//        dto.setName("T-Shirt");
//        dto.setPrice(BigDecimal.valueOf(1000));
//        dto.setProductTemplateId(templateId);
//        dto.setSkuCode("xyz");
//        dto.setUomId(uomId);
//        dto.setVariantId(productVariantId);
//        dto.setVariantOptionId(productVariantOptionId);
//
//        productService.createProduct(dto);
    }

    @Test
    void findAll() {
        PagedDto<StoreProductDto> all = productService.findAll(1, 20);
        log.info(all.getList());
    }
}

//[
//        StoreProductDto(
//                templateId=a15a9496-f224-11ed-a880-e4ce5d66e85a,
//        productName=Ralph Lauren T-Shirt,
//        brand=null,
//        imageUrl=https://image.com, description=Ralph T-Shirt,
//        variants=[
//                StoreProductVariantDto(
//                        id=0d5c6ce5-8af6-40a8-91da-12d3fa05c277,
//        sku=code4x, uomId=8c963e58-f223-11ed-a880-e4ce5d66e85a,
//        uomName=Qty, productImageUrl=null,
//        price=1000.00,
//        discount=null,
//        extraDescription=Ralph
//        T-Shirt, isNew=false,
//        sale=false,
//        variantId=af28dcfa-f223-11ed-a880-e4ce5d66e85a,
//        variantName=color,
//        variantOptionId=d817b1c2-f223-11ed-a880-e4ce5d66e85a,
//        variantOptionName=red)
//        ]
//        )]


//[StoreProductDto(
//                templateId=a15a9496-f224-11ed-a880-e4ce5d66e85a,
//        productName=Ralph Lauren T-Shirt, brand=Ralph Lauren,
//        imageUrl=https://image.com,
//        description=Ralph T-Shirt,
//        variants=[
//                StoreProductVariantDto(id=0d5c6ce5-8af6-40a8-91da-12d3fa05c277, sku=code4x, uomId=8c963e58-f223-11ed-a880-e4ce5d66e85a, uomName=Qty, productImageUrl=null, price=1000.00, discount=null, extraDescription=Ralph T-Shirt, isNew=false, sale=false, variantId=af28dcfa-f223-11ed-a880-e4ce5d66e85a, variantName=color, variantOptionId=d817b1c2-f223-11ed-a880-e4ce5d66e85a, variantOptionName=red),
//        StoreProductVariantDto(id=404b2678-7d29-49e2-8200-487331ba0783, sku=xxx, uomId=8c963e58-f223-11ed-a880-e4ce5d66e85a, uomName=Qty, productImageUrl=null, price=1000.00, discount=null, extraDescription=Ralph T-Shirt, isNew=false, sale=false, variantId=af28dcfa-f223-11ed-a880-e4ce5d66e85a, variantName=color, variantOptionId=d817b1c2-f223-11ed-a880-e4ce5d66e85a, variantOptionName=red)])
//        ]