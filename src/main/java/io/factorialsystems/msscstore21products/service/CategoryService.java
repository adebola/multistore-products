package io.factorialsystems.msscstore21products.service;

import io.factorialsystems.msscstore21products.dto.CategoryClientDto;
import io.factorialsystems.msscstore21products.dto.PagedDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface CategoryService {
    void clientSave(CategoryClientDto categoryClientDto, MultipartFile file);
    void clientUpdate (String id, CategoryClientDto categoryClientDto, MultipartFile file);
    PagedDto<CategoryClientDto> findAll(Integer pageNumber, Integer pageSize);
    Optional<CategoryClientDto> findById(String id);
    PagedDto<CategoryClientDto> search(Integer pageNumber, Integer pageSize, String searchString);
    void changeImage(String id, MultipartFile file);
}
