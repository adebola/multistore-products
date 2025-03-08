package io.factorialsystems.msscstore21products.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.factorialsystems.msscstore21products.dto.*;
import io.factorialsystems.msscstore21products.exception.ProductExistsException;
import io.factorialsystems.msscstore21products.mapper.ProductMapper;
import io.factorialsystems.msscstore21products.model.*;
import io.factorialsystems.msscstore21products.repository.*;
import io.factorialsystems.msscstore21products.security.JwtTokenWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductTemplateRepository productTemplateRepository;
    private final ProductVariantOptionRepository productVariantOptionRepository;
    private final ProductVariantOptionMappingRepository productVariantOptionMappingRepository;

    public ProductClientDto findById(String id) {
        log.info("Retrieving Product by Id {}", id);
        return productMapper.toClientDto(productRepository.findById(id));
    }

    @Cacheable(value = "product_cache", key = "{#pageNumber, #pageSize}")
    public PagedDto<StoreProductDto> findAll(int pageNumber, int pageSize) {
        log.info("Retrieving Products PageNumber {}, PageSize {}", pageNumber, pageSize);

        try (var ignored = PageHelper.startPage(pageNumber, pageSize)) {
            Page<Product> products = productRepository.findAll();
            List<StoreProductDto> storeDto = transformProducts(products.getResult());
            return createDto(storeDto, products);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    public PagedDto<StoreProductDto> search(int pageNumber, int pageSize, String search) {
        log.info("Searching Products PageNumber {}, PageSize {}, Search {}", pageNumber, pageSize, search);

        try (var ignored = PageHelper.startPage(pageNumber, pageSize)) {
            Page<Product> products = productRepository.search(search);
            List<StoreProductDto> storeDto = transformProducts(products.getResult());
            return createDto(storeDto, products);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }

    }

    @Transactional
    public void createProduct(CreateProductDto dto) {

        if (dto.getVariantId() != null && dto.getVariantOptionId() == null) {
            final String errorMessage = "You are creating a Product with a Variant without an Option";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        if (dto.getVariantId() == null && dto.getVariantOptionId() != null) {
            final String errorMessage = "You are creating a Product without a Variant, but with an Option";
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        if (productRepository.checkIfExistsBySku(dto.getSkuCode())) {
            final String errorMessage = String.format("Product with sku_code %s has been created please change the code and re-submit", dto.getSkuCode());
            log.error(errorMessage);
            throw new ProductExistsException(errorMessage);
        }

        Map<String, String> map = new HashMap<>();
        map.put("templateId", dto.getProductTemplateId());
        map.put("variantId", dto.getVariantId());
        map.put("variantOptionId", dto.getVariantOptionId());

        if (productRepository.checkIfProductExists(map)) {
            ProductTemplate pt = productTemplateRepository.findById(dto.getProductTemplateId());
            ProductVariant pv = productVariantRepository.findById(dto.getVariantId());
            ProductVariantOption pvo = productVariantOptionRepository.findById(dto.getVariantOptionId());

            final String errorMessage = String.format("Product %s with Variants %s:%s already exists, Please correct and re-submit", pt.getName(), pv.getName(), pvo.getName());
            log.error(errorMessage);
            throw new ProductExistsException(errorMessage);
        }

        final String id = UUID.randomUUID().toString();

        Product product = Product.builder()
                .extraDescription(dto.getDescription())
                .productName(dto.getName())
                .quantity(dto.getQuantity() == null ? 1 : dto.getQuantity())
                .variantId(dto.getVariantId())
                .variantOptionId(dto.getVariantOptionId())
                .productImageUrl(dto.getImageUrl())
                .isNew(dto.getIsNew())
                .sale(dto.getSale())
                .uomId(dto.getUomId())
                .discount(dto.getDiscount())
                .templateId(dto.getProductTemplateId())
                .sku(dto.getSkuCode())
                .createdBy(JwtTokenWrapper.getUserName())
                .price(dto.getPrice())
                .isNew(dto.getIsNew() != null && dto.getIsNew())
                .sale(dto.getSale() != null && dto.getSale())
                .id(id)
                .build();

        productRepository.create(product);

        if (product.getVariantId() != null) {
            ProductVariantOptionMapping pvom = ProductVariantOptionMapping.builder()
                    .productVariantId(product.getVariantId())
                    .productVariantOptionId(product.getVariantOptionId())
                    .productId(id)
                    .build();

            productVariantOptionMappingRepository.save(pvom);
        }
    }

    private List<StoreProductDto> transformProducts(List<Product> products) {
        List<String> templateIds = products.stream()
                .map(Product::getTemplateId)
                .distinct()
                .toList();

        List<StoreProductDto> tenantProducts = new ArrayList<>();

        templateIds.forEach(t -> {
            List<Product> templateProducts = products.stream()
                    .filter(f -> f.getTemplateId().equals(t))
                    .toList();

            if (!templateProducts.isEmpty()) {
                ProductTemplate productTemplate = productTemplateRepository.findById(t);
                StoreProductDto dto = createStoreProductFromProduct(templateProducts.get(0), productTemplate);

                List<StoreProductVariantDto> variantDtos = templateProducts.stream()
                        .map(this::createVariantFromProduct)
                        .toList();

                dto.setVariants(variantDtos);

                tenantProducts.add(dto);
            }
        });

        return tenantProducts;
    }

    private StoreProductDto createStoreProductFromProduct(Product p, ProductTemplate parent) {
        return StoreProductDto.builder()
                .brand(parent.getBrand())
                .description(p.getExtraDescription())
                .productName(p.getProductName())
                .imageUrl(p.getImageUrl())
                .templateId(p.getTemplateId())
                .build();
    }

    private StoreProductVariantDto createVariantFromProduct(Product p) {
        return StoreProductVariantDto.builder()
                .variantId(p.getVariantId())
                .variantName(p.getVariantName())
                .variantOptionId(p.getVariantOptionId())
                .variantOptionName(p.getVariantOptionName())
                .discount(p.getDiscount())
                .sku(p.getSku())
                .extraDescription(p.getExtraDescription())
                .uomId(p.getUomId())
                .uomName(p.getUomName())
                .sale(p.getSale())
                .price(p.getPrice())
                .isNew(p.getIsNew())
                .productImageUrl(p.getProductImageUrl())
                .id(p.getId())
                .build();
    }

    private PagedDto<StoreProductDto> createDto( List<StoreProductDto> results, Page<Product> products) {
        PagedDto<StoreProductDto> pagedDto = new PagedDto<>();
        pagedDto.setTotalSize((int) products.getTotal());
        pagedDto.setPageNumber(products.getPageNum());
        pagedDto.setPageSize(products.getPageSize());
        pagedDto.setPages(products.getPages());
        pagedDto.setList(results);

        return pagedDto;
    }
}
