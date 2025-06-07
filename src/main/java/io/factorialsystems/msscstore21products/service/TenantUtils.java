package io.factorialsystems.msscstore21products.service;

import io.factorialsystems.msscstore21products.exception.WrongTenantException;
import io.factorialsystems.msscstore21products.model.Product;
import io.factorialsystems.msscstore21products.repository.ProductRepository;
import io.factorialsystems.msscstore21products.security.JwtTokenWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TenantUtils {
    private final ProductRepository productRepository;

    public void checkTenantForProduct(String productId) {
        final Optional<Product> product = productRepository.findSingleById(productId);

        if (product.isEmpty()) {
            log.error("Product with id {} not found", productId);
            throw new RuntimeException(String.format("Product with id %s not found", productId));
        }

        if (!product.get().getTenantId().equals(JwtTokenWrapper.getTenantId())) {
            log.error("Product with id {} not found for tenant {}", productId, JwtTokenWrapper.getTenantId());
            throw new WrongTenantException(String.format("Product with id %s not found for tenant %s", productId, JwtTokenWrapper.getTenantId()));
        }
    }
}
