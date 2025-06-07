package io.factorialsystems.msscstore21products.service.product;

import io.factorialsystems.msscstore21products.dto.ProductVariantRequestDTO;
import io.factorialsystems.msscstore21products.dto.ProductVariantResponseDTO;

import java.util.List;

public interface ProductVariantService {
    void addVariantToProduct(String productId, ProductVariantRequestDTO dto);
    void disableVariant(String productId, String variantId);
    void enableVariant(String productId, String variantId);
    void editVariant(String productId, String variantId, String newVariantName);
    int removeVariantFromProduct(String productId, String variantId);

    List<ProductVariantResponseDTO> getProductVariants(String productId);
    ProductVariantResponseDTO getProductVariant(String productId, String variantId);
}
