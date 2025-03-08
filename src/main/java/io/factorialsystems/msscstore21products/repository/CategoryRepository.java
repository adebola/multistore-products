package io.factorialsystems.msscstore21products.repository;

import com.github.pagehelper.Page;
import io.factorialsystems.msscstore21products.model.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CategoryRepository {
    void save(Category category);
    void update (Category category);
    Category findById(String id);
    Page<Category> findAll();
    Page<Category> search(String search);
    void suspend(String id);
    void unsuspend(String id);
    void delete (Map<String, String> parameters);
}
