package io.factorialsystems.msscstore21products.repository;

import com.github.pagehelper.Page;
import io.factorialsystems.msscstore21products.model.ProductVariantOption;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ProductVariantOptionRepository {
    void save(ProductVariantOption pvo);

    void update(ProductVariantOption pvo);

    ProductVariantOption findById(String id);

    Page<ProductVariantOption> findAll();

    void suspend(String id);

    void unsuspend(String id);

    void delete(Map<String, String> parameters);

    ProductVariantOption findByIdOrName(String id);

    Page<ProductVariantOption> search(String search);
}
