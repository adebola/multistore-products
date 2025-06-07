package io.factorialsystems.msscstore21products.service;

import io.factorialsystems.msscstore21products.config.RedisConfig;
import io.factorialsystems.msscstore21products.model.SKUProduct;
import io.factorialsystems.msscstore21products.repository.SKUProductRepository;
import io.factorialsystems.msscstore21products.security.JwtTokenWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SKUProductServiceImpl implements SKUProductService {
    private final SKUProductRepository repository;

    @Override
    @Cacheable(value = RedisConfig.PRODUCT_SKU_CACHE_NAME, key = "#tenantId", unless = "#result == null")
    public List<SKUProduct> getAllProductsForTenant(String tenantId) {
        return repository.findAllByTenantId(JwtTokenWrapper.getTenantId());
    }
}
