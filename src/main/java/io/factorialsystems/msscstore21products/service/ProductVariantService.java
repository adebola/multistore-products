package io.factorialsystems.msscstore21products.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.factorialsystems.msscstore21products.dto.PagedDto;
import io.factorialsystems.msscstore21products.dto.ProductVariantClientDto;
import io.factorialsystems.msscstore21products.mapper.ProductVariantMapper;
import io.factorialsystems.msscstore21products.model.ProductVariant;
import io.factorialsystems.msscstore21products.repository.ProductVariantRepository;
import io.factorialsystems.msscstore21products.security.JwtTokenWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductVariantService {
    private final ProductVariantMapper variantMapper;
    private final ProductVariantRepository variantRepository;

    public ProductVariantClientDto findById(String id) {
        log.info("Find Product Variant : {}", id);
        return variantMapper.toClientDto(variantRepository.findById(id));
    }

    public void clientSave (ProductVariantClientDto dto) {
        ProductVariant p = variantMapper.toModel(dto);
        p.setCreatedBy(JwtTokenWrapper.getUserName());
        p.setId(UUID.randomUUID().toString());

        log.info("Saving Product Variant : {}", p);

        variantRepository.save(p);
    }

    public PagedDto<ProductVariantClientDto> findAll(Integer pageNumber, Integer pageSize) {
        log.info("Find All Product Variant, PageNumber {}, PageSize {}", pageNumber, pageSize);

        try (var ignored = PageHelper.startPage(pageNumber, pageSize)) {
            return createClientDto(variantRepository.findAll());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    public PagedDto<ProductVariantClientDto> search(Integer pageNumber, Integer pageSize, String search) {
        log.info("Search Product Variant, PageNumber {}, PageSize {}, Search {}", pageNumber, pageSize, search);

        try (var ignored = PageHelper.startPage(pageNumber, pageSize)) {
            return createClientDto(variantRepository.search(search));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void clientUpdate(String id, ProductVariantClientDto dto) {
        log.info("Update Product Variant {}", id);

        ProductVariant template = variantMapper.toModel(dto);
        template.setId(id);

        variantRepository.update(template);
    }

    public void clientDelete(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("deletedBy", JwtTokenWrapper.getUserName());
        log.info("Delete ProductVariant {}, Deleted By {}", id, JwtTokenWrapper.getUserName());

        variantRepository.delete(map);
    }

    public void clientSuspend(String id) {
        log.info("Suspend ProductVariant {}", id);
        variantRepository.suspend(id);
    }

    public void clientUnsuspend(String id) {
        log.info("Un-Suspend ProductVariant {}", id);
        variantRepository.unsuspend(id);
    }

    private PagedDto<ProductVariantClientDto> createClientDto(Page<ProductVariant> variants) {
        List<ProductVariant> result = variants.getResult();

        PagedDto<ProductVariantClientDto> pagedDto = new PagedDto<>();
        pagedDto.setTotalSize((int) variants.getTotal());
        pagedDto.setPageNumber(variants.getPageNum());
        pagedDto.setPageSize(variants.getPageSize());
        pagedDto.setPages(variants.getPages());
        pagedDto.setList(result.stream().map(variantMapper::toClientDto).toList());

        return pagedDto;
    }
}
