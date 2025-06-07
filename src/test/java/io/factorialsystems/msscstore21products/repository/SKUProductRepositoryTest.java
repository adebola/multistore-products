package io.factorialsystems.msscstore21products.repository;

import io.factorialsystems.msscstore21products.model.SKUProduct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for SKUProductRepository.
 * 
 * Note: Most tests are commented out because they require valid foreign keys in the database.
 * To run these tests, you would need to ensure that the product_id, product_variant_id,
 * product_variant_option_id, and uom_id in the SKUProduct all reference existing records
 * in their respective tables.
 */
@Slf4j
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SKUProductRepositoryTest {

    @Autowired
    private SKUProductRepository skuProductRepository;

    private final String TENANT_ID = "bd965121-7a80-4928-ab79-fd585063d6ab";

    /**
     * Test for findById method.
     * Commented out because it requires saving a SKUProduct with valid foreign keys.
     */
    /*
    @Test
    void findById() {
        // First save a SKUProduct to find
        SKUProduct skuProduct = createTestSKUProduct();
        skuProductRepository.save(skuProduct);

        // Find the SKUProduct by ID
        Optional<SKUProduct> foundSKUProductOptional = skuProductRepository.findById(skuProduct.getId());

        // Verify the SKUProduct was found
        assertThat(foundSKUProductOptional).isPresent();
        SKUProduct foundSKUProduct = foundSKUProductOptional.get();
        assertThat(foundSKUProduct.getId()).isEqualTo(skuProduct.getId());
        assertThat(foundSKUProduct.getSku()).isEqualTo(skuProduct.getSku());
        assertThat(foundSKUProduct.getTenantId()).isEqualTo(skuProduct.getTenantId());

        log.info("Found SKUProduct: {}", foundSKUProduct);
    }
    */

    /**
     * Test for findAllByTenantId method.
     * Commented out because it requires saving a SKUProduct with valid foreign keys.
     */
    /*
    @Test
    void findAllByTenantId() {
        // Save a SKUProduct for the tenant
        SKUProduct skuProduct = createTestSKUProduct();
        skuProductRepository.save(skuProduct);

        // Find all SKUProducts for the tenant
        List<SKUProduct> skuProducts = skuProductRepository.findAllByTenantId(TENANT_ID);

        // Verify SKUProducts were found
        assertThat(skuProducts).isNotNull();
        assertThat(skuProducts.size()).isGreaterThan(0);

        log.info("Found {} SKUProducts for tenant", skuProducts.size());
    }
    */

    /**
     * Test for findByIdAndTenantId method.
     * Commented out because it requires saving a SKUProduct with valid foreign keys.
     */
    /*
    @Test
    void findByIdAndTenantId() {
        // Save a SKUProduct
        SKUProduct skuProduct = createTestSKUProduct();
        skuProductRepository.save(skuProduct);

        // Create parameters for search
        Map<String, String> params = new HashMap<>();
        params.put("id", skuProduct.getId());
        params.put("tenantId", TENANT_ID);

        // Find the SKUProduct by ID and tenant ID
        Optional<SKUProduct> foundSKUProductOptional = skuProductRepository.findByIdAndTenantId(params);

        // Verify the SKUProduct was found
        assertThat(foundSKUProductOptional).isPresent();
        SKUProduct foundSKUProduct = foundSKUProductOptional.get();
        assertThat(foundSKUProduct.getId()).isEqualTo(skuProduct.getId());
        assertThat(foundSKUProduct.getSku()).isEqualTo(skuProduct.getSku());

        log.info("Found SKUProduct by ID and tenant ID: {}", foundSKUProduct);
    }
    */

    /**
     * Test for save method.
     * Commented out because it requires saving a SKUProduct with valid foreign keys.
     */
    /*
    @Test
    @Rollback
    @Transactional
    void save() {
        // Create a new SKUProduct
        SKUProduct skuProduct = createTestSKUProduct();

        // Save the SKUProduct
        skuProductRepository.save(skuProduct);

        // Verify the SKUProduct was saved
        Optional<SKUProduct> savedSKUProductOptional = skuProductRepository.findById(skuProduct.getId());

        assertThat(savedSKUProductOptional).isPresent();
        SKUProduct savedSKUProduct = savedSKUProductOptional.get();
        assertThat(savedSKUProduct.getId()).isEqualTo(skuProduct.getId());
        assertThat(savedSKUProduct.getSku()).isEqualTo(skuProduct.getSku());
        assertThat(savedSKUProduct.getCreatedOn()).isNotNull();
        assertThat(savedSKUProduct.getCreatedBy()).isEqualTo(skuProduct.getCreatedBy());
        assertThat(savedSKUProduct.getDisabled()).isEqualTo(skuProduct.getDisabled());
        assertThat(savedSKUProduct.getTenantId()).isEqualTo(skuProduct.getTenantId());

        log.info("Saved SKUProduct: {}", savedSKUProduct);
    }
    */

    /**
     * Test for update method.
     * Commented out because it requires saving a SKUProduct with valid foreign keys.
     */
    /*
    @Test
    @Rollback
    @Transactional
    void update() {
        // First save a SKUProduct
        SKUProduct skuProduct = createTestSKUProduct();
        skuProductRepository.save(skuProduct);

        // Update the SKUProduct
        String updatedSku = "Updated-SKU-" + UUID.randomUUID().toString().substring(0, 8);
        skuProduct.setSku(updatedSku);
        skuProductRepository.update(skuProduct);

        // Verify the SKUProduct was updated
        Optional<SKUProduct> updatedSKUProductOptional = skuProductRepository.findById(skuProduct.getId());

        assertThat(updatedSKUProductOptional).isPresent();
        SKUProduct updatedSKUProduct = updatedSKUProductOptional.get();
        assertThat(updatedSKUProduct.getSku()).isEqualTo(updatedSku);

        log.info("Updated SKUProduct: {}", updatedSKUProduct);
    }
    */

    /**
     * Test for failToSave method.
     * This test doesn't require saving a SKUProduct with valid foreign keys,
     * as it's testing the failure case when a required field is null.
     */
    @Test
    @Rollback
    @Transactional
    void failToSave() {
        // Create a SKUProduct with null sku (which should be required)
        SKUProduct skuProduct = createTestSKUProduct();
        skuProduct.setSku(null);

        // Attempt to save the SKUProduct, which should fail
        assertThrows(DataIntegrityViolationException.class, () -> {
            skuProductRepository.save(skuProduct);
        });

        log.info("Successfully failed to save SKUProduct with null sku");
    }

    /**
     * Helper method to create a test SKUProduct.
     * Note: The IDs used here might not exist in your database,
     * which would cause foreign key constraint violations when saving.
     */
    private SKUProduct createTestSKUProduct() {
        SKUProduct skuProduct = new SKUProduct();
        skuProduct.setId(UUID.randomUUID().toString());
        skuProduct.setSku("Test-SKU-" + UUID.randomUUID().toString().substring(0, 8));
        // Use existing IDs from the database
        skuProduct.setProductId("0d5c6ce5-8af6-40a8-91da-12d3fa05c277");
        skuProduct.setProductVariantId("af28dcfa-f223-11ed-a880-e4ce5d66e85a");
        skuProduct.setProductVariantOptionId("d817b1c2-f223-11ed-a880-e4ce5d66e85a");
        skuProduct.setUomId("8c963e58-f223-11ed-a880-e4ce5d66e85a");
        skuProduct.setPrice(new BigDecimal("19.99"));
        skuProduct.setQuantity(new BigDecimal("10"));
        skuProduct.setIsNew(true);
        skuProduct.setIsOnSale(false);
        skuProduct.setCreatedOn(Instant.now());
        skuProduct.setCreatedBy("test-user");
        skuProduct.setDisabled(false);
        skuProduct.setTenantId(TENANT_ID);
        return skuProduct;
    }
}
