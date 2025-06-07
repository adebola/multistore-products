package io.factorialsystems.msscstore21products.service.product;

import io.factorialsystems.msscstore21products.model.Uom;
import io.factorialsystems.msscstore21products.repository.ProductRepository;
import io.factorialsystems.msscstore21products.security.JwtTokenWrapper;
import lombok.extern.apachecommons.CommonsLog;
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

@CommonsLog
@SpringBootTest
class ProductUomServiceImplTest {
    @Autowired
    private ProductUomService productUomService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Rollback
    @Transactional
    void addUomToProduct() {
        try (MockedStatic<JwtTokenWrapper> t = Mockito.mockStatic(JwtTokenWrapper.class)) {
            final String tenantId = "ac934f2a-e4bb-11ef-a162-8de2c73cd885";
            final String userName = "adebola";
            final String productId = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
            final String uomName = "NewUomName_1";

            t.when(JwtTokenWrapper::getUserName).thenReturn(userName);
            assertThat(JwtTokenWrapper.getUserName()).isEqualTo(userName);
            t.when(JwtTokenWrapper::getTenantId).thenReturn(tenantId);
            assertThat(JwtTokenWrapper.getTenantId()).isEqualTo(tenantId);

            productUomService.addUomToProduct(productId,uomName);
            final List<Uom> uoms = productRepository.findProductUoms(productId);
            assertThat(uoms)
                    .isNotNull()
                    .isNotEmpty()
                    .hasSize(5)
                    .extracting(Uom::getName)
                    .contains(uomName);
        }
    }

    @Test
    @Rollback
    @Transactional
    void removeUomFromProduct() {
        try (MockedStatic<JwtTokenWrapper> t = Mockito.mockStatic(JwtTokenWrapper.class)) {
            final String tenantId = "ac934f2a-e4bb-11ef-a162-8de2c73cd885";
            final String userName = "adebola";
            final String productId = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
            final String uomId = "24869fbe-2e59-11f0-a60a-8480f653b9a9";

            t.when(JwtTokenWrapper::getUserName).thenReturn(userName);
            assertThat(JwtTokenWrapper.getUserName()).isEqualTo(userName);
            t.when(JwtTokenWrapper::getTenantId).thenReturn(tenantId);
            assertThat(JwtTokenWrapper.getTenantId()).isEqualTo(tenantId);

            productUomService.removeUomFromProduct(productId, uomId);

            final List<Uom> uoms = productRepository.findProductUoms(productId);
            assertThat(uoms)
                    .isNotNull()
                    .isNotEmpty()
                    .hasSize(4);

            Optional<Uom> uom = uoms.stream().filter(u -> u.getId().equals(uomId))
                    .findFirst();
            assertThat(uom).isNotEmpty();
            assertThat(uom.get().getId()).isEqualTo(uomId);
            assertThat(uom.get().getDisabled()).isTrue();
        }
    }

    @Test
    @Rollback
    @Transactional
    void editUom() {

        try (MockedStatic<JwtTokenWrapper> t = Mockito.mockStatic(JwtTokenWrapper.class)) {
            final String tenantId = "ac934f2a-e4bb-11ef-a162-8de2c73cd885";
            final String userName = "adebola";
            final String productId = "0293a0e6-2b57-11f0-a60a-8480f653b9a9";
            final String uomId = "24869fbe-2e59-11f0-a60a-8480f653b9a9";
            final String uomName = "NewUomName_2";

            t.when(JwtTokenWrapper::getUserName).thenReturn(userName);
            assertThat(JwtTokenWrapper.getUserName()).isEqualTo(userName);
            t.when(JwtTokenWrapper::getTenantId).thenReturn(tenantId);
            assertThat(JwtTokenWrapper.getTenantId()).isEqualTo(tenantId);

            productUomService.editUom(productId, uomId, uomName);

            Map<String, String> m = Map.of("productId", productId, "id", uomId);
            Optional<Uom> uom = productRepository.findSingleProductUom(m);

            assertThat(uom).isNotEmpty();
            assertThat(uom.get().getId()).isEqualTo(uomId);
            assertThat(uom.get().getName()).isEqualTo(uomName);
        }
    }
}