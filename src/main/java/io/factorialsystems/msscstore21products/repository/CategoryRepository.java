package io.factorialsystems.msscstore21products.repository;

import com.github.pagehelper.Page;
import io.factorialsystems.msscstore21products.model.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CategoryRepository {
    void save(Category category);
    void update (Category category);
    Category findByIdAndTenantId(Map<String, String> params);
    Page<Category> findAllForTenant(String id);
    Page<Category> searchForTenant(Map<String, String> params);
    Category findById(String id);
}
