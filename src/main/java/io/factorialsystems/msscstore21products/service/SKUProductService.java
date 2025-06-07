package io.factorialsystems.msscstore21products.service;

import io.factorialsystems.msscstore21products.model.SKUProduct;

import java.util.List;

public interface SKUProductService {
    List<SKUProduct> getAllProductsForTenant(String tenantId);
}
