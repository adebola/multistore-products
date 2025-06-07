package io.factorialsystems.msscstore21products.repository;

import io.factorialsystems.msscstore21products.model.SKUProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface SKUProductRepository {
    void save(SKUProduct skuProduct);
    void update(SKUProduct skuProduct);
    Optional<SKUProduct> findByIdAndTenantId(Map<String, String> map);
    Optional<SKUProduct> findById(String id);
    List<SKUProduct> findAllByTenantId(String id);
}
