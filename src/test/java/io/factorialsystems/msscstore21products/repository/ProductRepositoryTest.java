package io.factorialsystems.msscstore21products.repository;

import com.github.pagehelper.PageHelper;
import io.factorialsystems.msscstore21products.model.Product;
import io.factorialsystems.msscstore21products.model.ProductVariant;
import io.factorialsystems.msscstore21products.model.Uom;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findById() {
        final String id = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
        Optional<Product> p = this.productRepository.findById(id);
        assertThat(p).isNotEmpty();
        assertThat(p.get().getId()).isEqualTo(id);
        log.info(p.toString());
    }

    @Test
    void findByIdAndTenantId() {
        final String id = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
        final String tenantId = "ac934f2a-e4bb-11ef-a162-8de2c73cd885";;

        Map<String, Object> m = Map.of("id", id, "tenantId", tenantId);
        List<Product> p = this.productRepository.findByIdAndTenantId(m);
        assertThat(p).isNotEmpty();
        assertThat(p.size()).isEqualTo(1);
        assertThat(p.getFirst().getId()).isEqualTo(id);
        log.info(p.toString());
    }

    @Test
    void findByNameAndTenantId() {
        final String name = "Chicken Cut4";
        final String tenantId = "ac934f2a-e4bb-11ef-a162-8de2c73cd885";

        Map<String, String> m = Map.of("name", name, "tenantId", tenantId);
        Optional<Product> p = this.productRepository.findByNameAndTenantId(m);
        assertThat(p).isNotEmpty();
        log.info(p.get().toString());
    }

    @Test
    void findForTenant() {
        final String tenantId = "ac934f2a-e4bb-11ef-a162-8de2c73cd885";
        try(var ignored = PageHelper.startPage(1, 20)) {
            var p = this.productRepository.findForTenant(tenantId);
            assertThat(p).isNotEmpty();
            assertThat(p.size()).isGreaterThan(3);
            assertThat(p.getFirst().getTenantId()).isEqualTo(tenantId);
            log.info("Page Size: {}", p.size());
        }
    }

    @Test
    void create() {
        final String categoryId = "16b9c2b8-2b56-11f0-a60a-8480f653b9a9";
        Product product = Product.builder()
                .id(UUID.randomUUID().toString())
                .productName("Test Product")
                .categoryId(categoryId)
                .imageUrl("https://example.com/image.jpg")
                .disabled(false)
                .createdBy("Test User")
                .description("Test Description")
                .tenantId("ac934f2a-e4bb-11ef-a162-8de2c73cd885")
                .build();

        int result = this.productRepository.create(product);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void search() {
        Map<String, String> m = Map.of("search", "chi", "tenantId", "ac934f2a-e4bb-11ef-a162-8de2c73cd885");
        try(var ignored = PageHelper.startPage(1, 20)) {
            var p = this.productRepository.search(m);
            assertThat(p).isNotEmpty();
            assertThat(p.size()).isGreaterThan(0);
            log.info("Page Size: {}", p.size());
        }
    }

    @Test
    void addUom() {
        final String name = "test_11";
        final String productId = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
        final Map<String, String> m
                = Map.of("productId", productId, "uomName", name, "tenantId", "ac934f2a-e4bb-11ef-a162-8de2c73cd885", "createdBy", "adebola");
        int result = this.productRepository.addUom(m);
        assertThat(result).isEqualTo(1);

        productRepository.findProductUoms(productId).stream()
                .filter(uom -> uom.getName().equals(name))
                .findFirst()
                .ifPresentOrElse(
                        uom -> log.info("UOM found: {}", uom),
                        () -> log.error("UOM not found")
                );
    }

    @Test
    void removeUom() {
        final String productId = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
        final String uomId = "test_11";
    }

    @Test
    void editUom() {
        final String productId = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
        final String uomId = "f5376500-2b64-11f0-a60a-8480f653b9a9";
        final String newName = "test_12";
        Map<String, String> m = Map.of("productId", productId, "id", uomId, "uomName", newName);
        int result = this.productRepository.editUom(m);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void findSingleById() {
        final String id = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
        Optional<Product> p = this.productRepository.findSingleById(id);
        assertThat(p).isNotEmpty();
        log.info(p.get().toString());
    }

    @Test
    void findProductUoms() {
        final String productId = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
        List<Uom> uoms = this.productRepository.findProductUoms(productId);
        assertThat(uoms).isNotEmpty();
        assertThat(uoms.size()).isGreaterThan(0);
        log.info("UOMs: {}", uoms);
    }

    @Test
    void findSingleProductUom() {
        final String productId = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
        final String uomId = "f5376500-2b64-11f0-a60a-8480f653b9a9";
        Map<String, String> m = Map.of("productId", productId, "id", uomId);
        Optional<Uom> uom = this.productRepository.findSingleProductUom(m);
        assertThat(uom).isNotEmpty();
        log.info(uom.get().toString());
    }

    @Test
    void findSingleProductVariant() {
        final String productId = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
        final String variantId = "0daa70d8-2f1a-11f0-a60a-8480f653b9a9";
        final String tenantId = "ac934f2a-e4bb-11ef-a162-8de2c73cd885";
        Map<String, String> m = Map.of("productId", productId, "id", variantId, "tenantId", tenantId);

        Optional<ProductVariant> pv = this.productRepository.findSingleProductVariant(m);
        assertThat(pv).isNotEmpty();
        log.info(pv.get().toString());
    }

    @Test
    void findProductVariants() {
        final String productId = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
        final String tenantId = "ac934f2a-e4bb-11ef-a162-8de2c73cd885";
        Map<String, String> m = Map.of("productId", productId, "tenantId", tenantId);
        List<ProductVariant> pv = this.productRepository.findProductVariants(m);
        assertThat(pv).isNotEmpty();
        log.info("Size of Array {}", pv.size());
    }

    @Test
    void addVariant() {
        final String productId = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
        final String tenantId = "ac934f2a-e4bb-11ef-a162-8de2c73cd885";
        ProductVariant variant = new ProductVariant();

        variant.setName("test");
        variant.setCreatedBy("adebola");
        variant.setProductId(productId);
        variant.setId(UUID.randomUUID().toString());

        //variant.setTenantId(tenantId);
        int result = this.productRepository.addVariant(productId, variant);
        assertThat(result).isEqualTo(1);
    }
}