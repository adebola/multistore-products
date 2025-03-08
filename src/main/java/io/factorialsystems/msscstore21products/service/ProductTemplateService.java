package io.factorialsystems.msscstore21products.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.factorialsystems.msscstore21products.dto.PagedDto;
import io.factorialsystems.msscstore21products.dto.ProductTemplateClientDto;
import io.factorialsystems.msscstore21products.mapper.ProductTemplateMapper;
import io.factorialsystems.msscstore21products.model.ProductTemplate;
import io.factorialsystems.msscstore21products.repository.ProductTemplateRepository;
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
public class ProductTemplateService {
    private final ProductTemplateMapper templateMapper;
    private final ProductTemplateRepository templateRepository;

    public void clientSave (ProductTemplateClientDto dto) {
        ProductTemplate p = templateMapper.toModel(dto);
        p.setCreatedBy(JwtTokenWrapper.getUserName());
        p.setId(UUID.randomUUID().toString());

        log.info("Saving product template {}", p);
        templateRepository.save(p);
    }
    public PagedDto<ProductTemplateClientDto> findAll(Integer pageNumber, Integer pageSize) {
        log.info("Find All Product templates PageNumber {}, PageSize {}", pageNumber, pageSize);

        try (var ignored = PageHelper.startPage(pageNumber, pageSize)) {
            return createClientDto(templateRepository.findAll());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    public PagedDto<ProductTemplateClientDto> search(Integer pageNumber, Integer pageSize, String search) {
        log.info("Searching Product templates : {}", search);

        try (var ignored = PageHelper.startPage(pageNumber, pageSize)) {
            return createClientDto(templateRepository.search(search));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    public ProductTemplateClientDto findById(String id) {
        log.info("Find Product Template : {}", id);
        return templateMapper.toClientDTO(templateRepository.findById(id));
    }

    public void clientUpdate(String id, ProductTemplateClientDto dto) {
        ProductTemplate template = templateMapper.toModel(dto);
        template.setId(id);

        log.info("Update Product Template {}", template);
        templateRepository.update(template);
    }

    public void clientDelete(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("deletedBy", JwtTokenWrapper.getUserName());

        log.info("Deleting Product Template : {}, deletedBy {}", id, JwtTokenWrapper.getUserName());
        templateRepository.delete(map);
    }

    public void clientSuspend(String id) {
        log.info("Suspending Product Template : {}", id);
        templateRepository.suspend(id);
    }

    public void clientUnsuspend(String id) {
        log.info("Un-suspending Product Template : {}", id);
        templateRepository.unsuspend(id);
    }

    private PagedDto<ProductTemplateClientDto> createClientDto(Page<ProductTemplate> products) {
        List<ProductTemplate> result = products.getResult();

        PagedDto<ProductTemplateClientDto> pagedDto = new PagedDto<>();
        pagedDto.setTotalSize((int) products.getTotal());
        pagedDto.setPageNumber(products.getPageNum());
        pagedDto.setPageSize(products.getPageSize());
        pagedDto.setPages(products.getPages());
        pagedDto.setList(result.stream().map(templateMapper::toClientDTO).toList());

        return pagedDto;
    }
}
