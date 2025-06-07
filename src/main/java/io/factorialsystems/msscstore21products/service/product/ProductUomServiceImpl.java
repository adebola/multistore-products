package io.factorialsystems.msscstore21products.service.product;

import io.factorialsystems.msscstore21products.repository.ProductRepository;
import io.factorialsystems.msscstore21products.security.JwtTokenWrapper;
import io.factorialsystems.msscstore21products.service.TenantUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductUomServiceImpl implements ProductUomService {
    private final TenantUtils tenantUtils;
    private final ProductRepository productRepository;

    @Override
    public void addUomToProduct(String productId, String uomName)  {
        tenantUtils.checkTenantForProduct(productId);
        final Map<String, String> map =
                Map.of("productId", productId, "uomName", uomName, "createdBy", JwtTokenWrapper.getUserName());
        int result = productRepository.addUom(map);
    }

    @Override
    public void removeUomFromProduct(String productId, String uomId) {
        tenantUtils.checkTenantForProduct(productId);

        if (productRepository.findSingleProductUom(Map.of("productId", productId, "id", uomId)).isEmpty()) {
            final String errorMessage = String.format("UOM with id %s not found for product %s", uomId, productId);
            log.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }

        final Map<String, String> map = 
                Map.of("productId", productId, "id", uomId);
        int result = productRepository.removeUom(map);
    }

    @Override
    public void editUom(String productId, String uomId, String newUomName) {
        tenantUtils.checkTenantForProduct(productId);
        log.info("editing UOM with id {} for product {} New Name {}", uomId, productId,newUomName);

        final Map<String, String> map =
                Map.of("productId", productId, "id", uomId, "uomName", newUomName);
        int result = productRepository.editUom(map);
    }

    @Override
    public void disableUom(String productId, String uomId) {
        tenantUtils.checkTenantForProduct(productId);
        log.info("disabling UOM with id {} for product {}", uomId, productId);

        final Map<String, String> map =
                Map.of("productId", productId, "id", uomId );
        int result = productRepository.disableUom(map);
    }

    @Override
    public void enableUom(String productId, String uomId) {
        tenantUtils.checkTenantForProduct(productId);
        log.info("enabling UOM with id {} for product {}", uomId, productId);

        final Map<String, String> map =
                Map.of("productId", productId, "id", uomId );
        int result = productRepository.enableUom(map);
    }
}
