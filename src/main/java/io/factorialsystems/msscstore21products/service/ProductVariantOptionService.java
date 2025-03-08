package io.factorialsystems.msscstore21products.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.factorialsystems.msscstore21products.dto.PagedDto;
import io.factorialsystems.msscstore21products.dto.ProductVariantOptionClientDto;
import io.factorialsystems.msscstore21products.mapper.ProductVariantOptionMapper;
import io.factorialsystems.msscstore21products.model.ProductVariantOption;
import io.factorialsystems.msscstore21products.repository.ProductVariantOptionRepository;
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
public class ProductVariantOptionService {
    private final ProductVariantOptionMapper variantMapper;
    private final ProductVariantOptionRepository variantRepository;

    public ProductVariantOptionClientDto findById(String id) {
        log.info("Find Product Variant Option : {}", id);
        return variantMapper.toClientDto(variantRepository.findById(id));
    }

    public void clientSave (ProductVariantOptionClientDto dto) {
        ProductVariantOption p = variantMapper.toModel(dto);
        p.setCreatedBy(JwtTokenWrapper.getUserName());
        p.setId(UUID.randomUUID().toString());

        log.info("Saving Product Variant Option: {}",p);

        variantRepository.save(p);
    }

    public PagedDto<ProductVariantOptionClientDto> findAll(Integer pageNumber, Integer pageSize) {
        log.info("Find All Product Variant Option, PageNumber {}, PageSize {}", pageNumber, pageSize);

        try (var ignored = PageHelper.startPage(pageNumber, pageSize)) {
            return createClientDto(variantRepository.findAll());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    public PagedDto<ProductVariantOptionClientDto> search(Integer pageNumber, Integer pageSize, String search) {
        log.info("Search Product Variant Option, PageNumber {}, PageSize {}, Search {}", pageNumber, pageSize, search);

        try (var ignored = PageHelper.startPage(pageNumber, pageSize)) {
            return createClientDto(variantRepository.search(search));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void clientUpdate(String id, ProductVariantOptionClientDto dto) {
        log.info("Update Product Variant Option {}", id);

        ProductVariantOption template = variantMapper.toModel(dto);
        template.setId(id);

        variantRepository.update(template);
    }

    public void clientDelete(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("deletedBy", JwtTokenWrapper.getUserName());

        log.info("Delete ProductVariantOption {}, Deleted By {}", id, JwtTokenWrapper.getUserName());

        variantRepository.delete(map);
    }

    public void clientSuspend(String id) {
        log.info("Suspend ProductVariantOption {}", id);
        variantRepository.suspend(id);
    }

    public void clientUnsuspend(String id) {
        log.info("Unsuspend ProductVariantOption {}", id);
        variantRepository.unsuspend(id);
    }

    private PagedDto<ProductVariantOptionClientDto> createClientDto(Page<ProductVariantOption> variants) {
        List<ProductVariantOption> result = variants.getResult();

        PagedDto<ProductVariantOptionClientDto> pagedDto = new PagedDto<>();
        pagedDto.setTotalSize((int) variants.getTotal());
        pagedDto.setPageNumber(variants.getPageNum());
        pagedDto.setPageSize(variants.getPageSize());
        pagedDto.setPages(variants.getPages());
        pagedDto.setList(result.stream().map(variantMapper::toClientDto).toList());

        return pagedDto;
    }
}
