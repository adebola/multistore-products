package io.factorialsystems.msscstore21products.service.product;

import io.factorialsystems.msscstore21products.dto.ProductVariantOptionResponseDTO;
import io.factorialsystems.msscstore21products.dto.ProductVariantRequestDTO;
import io.factorialsystems.msscstore21products.dto.ProductVariantResponseDTO;
import io.factorialsystems.msscstore21products.model.ProductVariant;
import io.factorialsystems.msscstore21products.model.ProductVariantOption;
import io.factorialsystems.msscstore21products.repository.ProductRepository;
import io.factorialsystems.msscstore21products.security.JwtTokenWrapper;
import io.factorialsystems.msscstore21products.security.TenantContext;
import io.factorialsystems.msscstore21products.service.TenantUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {
    private final TenantUtils tenantUtils;
    private final ProductRepository productRepository;

    @Override
    public void addVariantToProduct(String productId, ProductVariantRequestDTO dto) {
        log.info("Adding variant to product with id {}", productId);
        tenantUtils.checkTenantForProduct(productId);

        final String createdBy = JwtTokenWrapper.getUserName();
        ProductVariant pv = new ProductVariant();
        pv.setId(UUID.randomUUID().toString());
        pv.setName(dto.getName());
        pv.setCreatedBy(createdBy);
        pv.setOptions(createOptions(dto, createdBy));

        if (productRepository.addVariant(productId, pv) > 0) {
            log.info("Successfully added variant to product with id {}", productId);
        } else {
            log.error("Failed to add variant to product with id {}", productId);
        }
    }

    @Override
    public void editVariant(String productId, String variantId, String newVariantName) {
        log.info("Editing variant to product with id {}", productId);

        tenantUtils.checkTenantForProduct(productId);
        final Map<String, String> map =
                Map.of("productId", productId, "id", variantId, "variantName", newVariantName);

       if (productRepository.editVariant(map) > 0) {
            log.info("Successfully edited variant with id {} for product {}", variantId, productId);
        } else {
            log.error("Failed to edit variant with id {} for product {}", variantId, productId);
        }
    }

    @Override
    public void disableVariant(String productId, String variantId) {
        tenantUtils.checkTenantForProduct(productId);
        log.info("disabling Variant with id {} for product {}", variantId, productId);

        final Map<String, String> map =
                Map.of("productId", productId, "id", variantId );

        if (productRepository.disableVariant(map) > 0) {
            log.info("Successfully disabled variant with id {} for product {}", variantId, productId);
        } else {
            log.error("Failed to disable variant with id {} for product {}", variantId, productId);
        }

    }

    @Override
    public void enableVariant(String productId, String variantId) {
        tenantUtils.checkTenantForProduct(productId);
        log.info("enabling UOM with id {} for product {}", variantId, productId);

        final Map<String, String> map =
                Map.of("productId", productId, "id", variantId );

        if(productRepository.enableVariant(map) > 0) {
            log.info("Successfully enabled variant with id {} for product {}", variantId, productId);
        } else {
            log.error("Failed to enable variant with id {} for product {}", variantId, productId);
        }
    }

    public static List<ProductVariantOption> createOptions(ProductVariantRequestDTO dto, String createdBy) {
        if (dto.getOptions() == null || dto.getOptions().isEmpty()) {
            return List.of();
        }

        return dto.getOptions().stream()
                .map(o -> {
                    ProductVariantOption o1 = new ProductVariantOption();
                    o1.setName(o.name());
                    o1.setId(UUID.randomUUID().toString());
                    o1.setCreatedBy(createdBy);
                    // o1.setDisabled();
                    return o1;
                }).collect(Collectors.toList());
    }

    @Override
    public int removeVariantFromProduct(String productId, String variantId) {
        tenantUtils.checkTenantForProduct(productId);
        return productRepository.removeVariant(productId, variantId);
    }


    @Override
    public List<ProductVariantResponseDTO> getProductVariants(String productId) {
        final String tenantId = TenantContext.getCurrentTenant();

        List<ProductVariant> variants = productRepository.findProductVariants(Map.of("productId", productId, "tenantId", tenantId));

        // Group variants by their ID to create a hierarchical structure
        Map<String, List<ProductVariant>> variantGroups = variants.stream()
                .collect(Collectors.groupingBy(ProductVariant::getId));

        // Convert to DTOs
        return variantGroups.values().stream()
                .map(productVariants -> {
                    ProductVariantResponseDTO dto = new ProductVariantResponseDTO();
                    ProductVariant firstVariant = productVariants.getFirst();

                    dto.setId(firstVariant.getId());
                    dto.setName(firstVariant.getName());
                    dto.setDisabled(firstVariant.getDisabled());

                    // Map variant options
                    List<ProductVariantOptionResponseDTO> options = productVariants.stream()
                            .filter(v -> v.getOptions() != null)
                            .flatMap(v -> v.getOptions().stream())
                            .map(option -> {
                                ProductVariantOptionResponseDTO optionDto = new ProductVariantOptionResponseDTO();
                                optionDto.setId(option.getId());
                                optionDto.setName(option.getName());
                                return optionDto;
                            }).collect(Collectors.toList());

                    dto.setOptions(options);
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public ProductVariantResponseDTO getProductVariant(String productId, String variantId) {
        final String tenantId = TenantContext.getCurrentTenant();
        Optional<ProductVariant> variant = productRepository.findSingleProductVariant(
                Map.of("productId", productId, "id", variantId, "tenantId", tenantId)
        );

        if (variant.isPresent()) {
            ProductVariant firstVariant = variant.get();
            ProductVariantResponseDTO dto = new ProductVariantResponseDTO();
            dto.setId(firstVariant.getId());
            dto.setName(firstVariant.getName());
            dto.setDisabled(firstVariant.getDisabled());

            List<ProductVariantOptionResponseDTO> options = firstVariant.getOptions().stream()
                    .map(option -> {
                        ProductVariantOptionResponseDTO pod = new ProductVariantOptionResponseDTO();
                        pod.setId(option.getId());
                        pod.setName(option.getName());
                        return pod;
                    }).collect(Collectors.toList());

            dto.setOptions(options);
            return dto;
        }

        return null;
    }
}
