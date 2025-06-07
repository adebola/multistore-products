package io.factorialsystems.msscstore21products.service.product;

import io.factorialsystems.msscstore21products.dto.ProductVariantOptionRequestDTO;
import io.factorialsystems.msscstore21products.dto.ProductVariantRequestDTO;
import io.factorialsystems.msscstore21products.dto.ProductVariantResponseDTO;
import io.factorialsystems.msscstore21products.model.ProductVariant;
import io.factorialsystems.msscstore21products.repository.ProductRepository;
import io.factorialsystems.msscstore21products.security.JwtTokenWrapper;
import io.factorialsystems.msscstore21products.security.TenantContext;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@CommonsLog
@SpringBootTest
class ProductVariantServiceImplTest {
    @Autowired
    private ProductVariantService productVariantService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Rollback
    @Transactional
    void addVariantToProduct() {

        try (
                MockedStatic<JwtTokenWrapper> t = Mockito.mockStatic(JwtTokenWrapper.class);
                MockedStatic<TenantContext> tc = Mockito.mockStatic(TenantContext.class)
        ){
            final String tenantId = "ac934f2a-e4bb-11ef-a162-8de2c73cd885";
            final String userName = "adebola";
            final String productId = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";

            t.when(JwtTokenWrapper::getUserName).thenReturn(userName);
            Assertions.assertThat(JwtTokenWrapper.getUserName()).isEqualTo(userName);

            t.when(JwtTokenWrapper::getTenantId).thenReturn(tenantId);
            Assertions.assertThat(JwtTokenWrapper.getTenantId()).isEqualTo(tenantId);

            tc.when(TenantContext::getCurrentTenant).thenReturn(tenantId);
            Assertions.assertThat(TenantContext.getCurrentTenant()).isEqualTo(tenantId);

            ProductVariantRequestDTO pv1 = new ProductVariantRequestDTO();
            pv1.setName("test1");
            pv1.setOptions(List.of(new ProductVariantOptionRequestDTO("option1"), new ProductVariantOptionRequestDTO("option2")));

            productVariantService.addVariantToProduct(productId, pv1);

            final List<ProductVariantResponseDTO> pvs = productVariantService.getProductVariants(productId);

            assertThat(pvs).hasSize(3);
        }
    }

    @Test
    @Rollback
    @Transactional
    void removeVariantFromProduct() {
        final String tenantId = "ac934f2a-e4bb-11ef-a162-8de2c73cd885";
        final String productId = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
        final String variantId = "0daa70d8-2f1a-11f0-a60a-8480f653b9a9";

        try (MockedStatic<JwtTokenWrapper> t = Mockito.mockStatic(JwtTokenWrapper.class)) {
            t.when(JwtTokenWrapper::getTenantId).thenReturn(tenantId);
            Assertions.assertThat(JwtTokenWrapper.getTenantId()).isEqualTo(tenantId);

            int result = productVariantService.removeVariantFromProduct(productId, variantId);

            // Check if the variant is removed
            Map<String, String> m = Map.of("productId", productId, "id", variantId, "tenantId", tenantId);
            final Optional<ProductVariant> pv = productRepository.findSingleProductVariant(m);

            assertThat(pv).isPresent();
            assertThat(pv.get().getId()).isEqualTo(variantId);
            assertThat(pv.get().getDisabled()).isTrue();

            log.info("Removed variant {} from product {}", variantId, productId);
        }
    }

    @Test
    @Rollback
    @Transactional
    void editVariant() {
        final String tenantId = "ac934f2a-e4bb-11ef-a162-8de2c73cd885";
        final String productId = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
        final String variantId = "0daa70d8-2f1a-11f0-a60a-8480f653b9a9";

        try (MockedStatic<JwtTokenWrapper> t = Mockito.mockStatic(JwtTokenWrapper.class)) {
            t.when(JwtTokenWrapper::getTenantId).thenReturn(tenantId);
            Assertions.assertThat(JwtTokenWrapper.getTenantId()).isEqualTo(tenantId);


        }
    }

    @Test
    void findProductVariants() {
        try (MockedStatic<TenantContext> t = Mockito.mockStatic(TenantContext.class)) {
            final String tenantId = "ac934f2a-e4bb-11ef-a162-8de2c73cd885";
            final String productId = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";

            t.when(TenantContext::getCurrentTenant).thenReturn(tenantId);
            Assertions.assertThat(TenantContext.getCurrentTenant()).isEqualTo(tenantId);

            List<ProductVariantResponseDTO> dto = productVariantService.getProductVariants(productId);
            assertThat(dto).isNotNull();
            assertThat(dto.size()).isEqualTo(2);
        }
    }

    @Test
    void findProductVariant() {
        try (MockedStatic<TenantContext> t = Mockito.mockStatic(TenantContext.class)) {
            final String tenantId = "ac934f2a-e4bb-11ef-a162-8de2c73cd885";
            final String productId = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
            final String variantId = "0daa70d8-2f1a-11f0-a60a-8480f653b9a9";

            t.when(TenantContext::getCurrentTenant).thenReturn(tenantId);
            Assertions.assertThat(TenantContext.getCurrentTenant()).isEqualTo(tenantId);

            ProductVariantResponseDTO dto = productVariantService.getProductVariant(productId, variantId);
            assertThat(dto).isNotNull();
            assertThat(dto.getId()).isEqualTo(variantId);
        }
    }
}