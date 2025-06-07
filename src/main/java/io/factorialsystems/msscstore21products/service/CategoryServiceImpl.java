package io.factorialsystems.msscstore21products.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.factorialsystems.msscstore21products.dto.CategoryDTO;
import io.factorialsystems.msscstore21products.dto.PagedDTO;
import io.factorialsystems.msscstore21products.model.Category;
import io.factorialsystems.msscstore21products.repository.CategoryRepository;
import io.factorialsystems.msscstore21products.security.JwtTokenWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final S3Service s3Service;
    private final AuditService auditService;
    private final CategoryRepository categoryRepository;

    public static final String EDIT_CATEGORY = "Edit-Category";
    public static final String CREATE_CATEGORY = "Create-Category";

    @Override
    public void clientSave(CategoryDTO categoryDTO, MultipartFile file) {
        Category c = Category.createCategory(
                categoryDTO.getName(),
                categoryDTO.getImageUrl(),
                categoryDTO.getDescription()
        );

        try {
            if (file != null && !file.isEmpty()) {
                final String fileName = s3Service.uploadFile(file);
                c.setImageUrl(fileName);
            }

            categoryRepository.save(c);
            final String auditMessage = String.format("Category %s created by %s for Tenant %s",
                    c.getName(), JwtTokenWrapper.getUserName(), JwtTokenWrapper.getTenantId());
            auditService.audit(CREATE_CATEGORY, auditMessage);
            log.info("Created and Saved category {} Successfully", c.getName());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void clientUpdate(String id, CategoryDTO categoryDTO, MultipartFile file) {

        try {
            Category existingCategory = categoryRepository.findById(id);
            if (existingCategory == null || !existingCategory.getTenantId().equals(JwtTokenWrapper.getTenantId())) {
                throw new RuntimeException("Category not found");
            }

            existingCategory.setName(categoryDTO.getName());
            existingCategory.setImageUrl(categoryDTO.getImageUrl());
            existingCategory.setDescription(categoryDTO.getDescription());
            existingCategory.setDisabled(categoryDTO.getDisabled());

            if (file != null && !file.isEmpty()) {
                final String fileName = s3Service.uploadFile(file);
                existingCategory.setImageUrl(fileName);
            }

            categoryRepository.update(existingCategory);
            final String auditMessage = String.format("Category %s updated by %s for Tenant %s",
                    existingCategory.getName(), JwtTokenWrapper.getUserName(), JwtTokenWrapper.getTenantId());
            auditService.audit(EDIT_CATEGORY, auditMessage);
            log.info("Updated and Saved category {} Successfully", existingCategory.getName());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public PagedDTO<CategoryDTO> findAll(Integer pageNumber, Integer pageSize) {
        log.info("Find All Categories PageNumber : {}, PageSize {}", pageNumber, pageSize);

        try (var ignored = PageHelper.startPage(pageNumber, pageSize)) {
            return createClientDto(categoryRepository.findAllForTenant(JwtTokenWrapper.getTenantId()));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public Optional<CategoryDTO> findById(String id) {
        log.info("Find Category by Id {}", id);
        final Map<String, String> m =
                Map.of("id", id, "tenantId", Objects.requireNonNull(JwtTokenWrapper.getTenantId()));
        Optional<Category> c = categoryRepository.findByIdAndTenantId(m);
        return c.map(Category::createClientDto);
    }

    @Override
    public PagedDTO<CategoryDTO> search(Integer pageNumber, Integer pageSize, String search) {
        log.info("Search Category PageNumber {}, PageSize {}, search String {}", pageNumber, pageSize, search);

        try (var ignored = PageHelper.startPage(pageNumber, pageSize)) {
            final Map<String, String> m =
                    Map.of("search", search, "tenantId", Objects.requireNonNull(JwtTokenWrapper.getTenantId()));
            return createClientDto(categoryRepository.searchForTenant(m));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }


    private PagedDTO<CategoryDTO> createClientDto(Page<Category> categories) {
        List<Category> result = categories.getResult();

        PagedDTO<CategoryDTO> pagedDto = new PagedDTO<>();
        pagedDto.setTotalSize((int) categories.getTotal());
        pagedDto.setPageNumber(categories.getPageNum());
        pagedDto.setPageSize(categories.getPageSize());
        pagedDto.setPages(categories.getPages());
        pagedDto.setList(result.stream().map(Category::createClientDto).toList());

        return pagedDto;
    }
}
