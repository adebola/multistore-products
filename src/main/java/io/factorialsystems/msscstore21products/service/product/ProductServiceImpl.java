package io.factorialsystems.msscstore21products.service.product;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.factorialsystems.msscstore21products.dto.*;
import io.factorialsystems.msscstore21products.mapper.ProductMapper;
import io.factorialsystems.msscstore21products.model.*;
import io.factorialsystems.msscstore21products.repository.CategoryRepository;
import io.factorialsystems.msscstore21products.repository.ProductRepository;
import io.factorialsystems.msscstore21products.security.JwtTokenWrapper;
import io.factorialsystems.msscstore21products.security.TenantContext;
import io.factorialsystems.msscstore21products.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final S3Service s3Service;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponseDTO findById(String id) {
        log.info("Retrieving Product by Id {}", id);
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            log.error("Product with id {} not found", id);
            throw new RuntimeException(String.format("Product with id %s not found", id));
        }

        return productMapper.toProductResponseDTO(product.get());
    }

    @Override
    public PagedDTO<ProductResponseDTO> findAll(int pageNumber, int pageSize) {
        log.info("Retrieving all Products");

        final String tenantId = TenantContext.getCurrentTenant();
        if (tenantId == null) {
            log.error("Tenant Id is null in Product findAll");
            throw new RuntimeException("Tenant Id is null, user set a tenant to perform this action");
        }

        try (var ignored = PageHelper.startPage(pageNumber, pageSize)) {
            Page<Product> products = productRepository.findForTenant(tenantId);
            return createProductResponseDTO(products);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public PagedDTO<ProductResponseDTO> search(int pageNumber, int pageSize, String search) {
        log.info("Searching Products : {}", search);

        final String tenantId = TenantContext.getCurrentTenant();
        if (tenantId == null) {
            log.error("Tenant Id is null in Product search");
            throw new RuntimeException("Tenant Id is null, user must set a tenant to perform this action");
        }

        try (var ignored = PageHelper.startPage(pageNumber, pageSize)) {
            final Map<String, String> m = Map.of("search", search, "tenantId", tenantId);
            Page<Product> products = productRepository.search(m);
            return createProductResponseDTO(products);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public String createProduct(CreateProductDTO dto, MultipartFile file) {
        log.info("Creating Product {}", dto);

        final String productId = UUID.randomUUID().toString();
        final String tenantId = JwtTokenWrapper.getTenantId();

        if (tenantId == null) {
            log.error("Tenant Id is null in createProduct");
            throw new RuntimeException("Tenant Id is null, user must logged in and have admin rights to performs this action");
        }

        final String createdBy = JwtTokenWrapper.getUserName();

        // See if the product already exists
        final Map<String, String> m = Map.of("name", dto.getName(), "tenantId", tenantId);
        if (productRepository.findByNameAndTenantId(m).isPresent()) {
            log.error("Product with name {} already exists", dto.getName());
            throw new RuntimeException(String.format("Product with name %s already exists", dto.getName()));
        }

        // Check if the category exists
        final Map<String, String> m1 = Map.of("name", dto.getCategoryName(), "tenantId", tenantId);
        Optional<Category> c = categoryRepository.findByNameAndTenantId(m1);

        if (c.isEmpty()) {
            log.error("Category with name {} not found", dto.getCategoryName());
            throw new RuntimeException(String.format("Category with name %s not found", dto.getCategoryName()));
        }

        Product product = Product.builder()
                .id(productId)
                .createdBy(JwtTokenWrapper.getUserName())
                .productName(dto.getName())
                .categoryId(c.get().getId())
                .description(dto.getDescription())
                .tenantId(tenantId)
                .build();

        if (dto.getUoms() != null && !dto.getUoms().isEmpty()) {
            List<Uom> uoms = dto.getUoms().stream()
                    .map(uom -> {
                        Uom u = new Uom();
                        u.setName(uom.name());
                        u.setCreatedBy(createdBy);
                        return u;
                    }).collect(Collectors.toList());

            product.setUoms(uoms);
        }

        if (dto.getVariants() != null && !dto.getVariants().isEmpty()) {
            List<ProductVariant> pv = dto.getVariants().stream()
                    .map(p -> {
                        ProductVariant v = new ProductVariant();
                        v.setName(p.getName());
                        v.setId(UUID.randomUUID().toString());
                        v.setCreatedBy(createdBy);
                        v.setOptions(ProductVariantServiceImpl.createOptions(p, createdBy));
                        return v;
                    }).collect(Collectors.toList());

            product.setVariants(pv);
        }

        if (file != null && !file.isEmpty()) {
            try {
                final String fileName = s3Service.uploadFile(file);
                product.setImageUrl(fileName);
            } catch (Exception ex) {
                log.error(ex.getMessage());
                throw new RuntimeException("Error uploading Product Image, please try again later");
            }
        }

        productRepository.create(product);
        return productId;
    }

    @Override
    public int updateProduct(String id, UpdateProductDTO dto, MultipartFile file) {
        log.info("Updating Product {}", dto);

        final Product product = productMapper.toProductFromUpdateDTO(dto);
        final String tenantId = JwtTokenWrapper.getTenantId();

        product.setTenantId(tenantId);
        product.setId(id);

        return productRepository.updateProduct(product);
    }

    private PagedDTO<ProductResponseDTO> createProductResponseDTO(Page<Product> products) {
        PagedDTO<ProductResponseDTO> pagedDto = new PagedDTO<>();
        pagedDto.setTotalSize((int) products.getTotal());
        pagedDto.setPageNumber(products.getPageNum());
        pagedDto.setPageSize(products.getPageSize());
        pagedDto.setPages(products.getPages());
        pagedDto.setList(products.stream().map(productMapper::toProductResponseDTOFlat).toList());

        return pagedDto;

    }
}
