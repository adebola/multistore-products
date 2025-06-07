package io.factorialsystems.msscstore21products.service;

import io.factorialsystems.msscstore21products.dto.CategoryDTO;
import io.factorialsystems.msscstore21products.dto.PagedDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface CategoryService {
    void clientSave(CategoryDTO categoryDTO, MultipartFile file);
    void clientUpdate (String id, CategoryDTO categoryDTO, MultipartFile file);
    PagedDTO<CategoryDTO> findAll(Integer pageNumber, Integer pageSize);
    Optional<CategoryDTO> findById(String id);
    PagedDTO<CategoryDTO> search(Integer pageNumber, Integer pageSize, String searchString);
}
