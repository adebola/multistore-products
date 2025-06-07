package io.factorialsystems.msscstore21products.repository;

import com.github.pagehelper.Page;
import io.factorialsystems.msscstore21products.model.Product;
import io.factorialsystems.msscstore21products.model.ProductVariant;
import io.factorialsystems.msscstore21products.model.ProductVariantOption;
import io.factorialsystems.msscstore21products.model.Uom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface ProductRepository {
    Optional<Product> findById(String id);

    List<Product> findByIdAndTenantId(Map<String, Object> map);

    Page<Product> findForTenant(String id);

    int create(Product product);

    Page<Product> search(Map<String, String> parameters);

    Optional<Product> findByNameAndTenantId(Map<String, String> map);

    int addUom(Map<String, String> map);

    int removeUom(Map<String, String> map);

    Optional<Product> findSingleById(String id);

    List<Uom> findProductUoms(String productId);

    Optional<Uom> findSingleProductUom(Map<String, String> map);

    Optional<ProductVariant> findSingleProductVariant(Map<String, String> map);

    List<ProductVariant> findProductVariants(Map<String, String> map);

    int editUom(Map<String, String> map);

    int disableUom(Map<String, String> map);

    int enableUom(Map<String, String> map);

    int addVariant(@Param("productId") String productId, @Param("variant") ProductVariant variant);

    int removeVariant(@Param("productId") String productId, @Param("id") String id);

    int editVariant(Map<String, String> map);

    int updateProduct(Product product);

    int disableVariant(Map<String, String> map);

    int enableVariant(Map<String, String> map);

    Optional<ProductVariantOption> findVariantOptionByName(Map<String, String> optionMap);

    int addOptionToVariant(Map<String, String> map);

    int updateVariantOption(Map<String, String> map);

    int enableVariantOption(Map<String, String> m);

    int disableVariantOption(Map<String, String> m);
}
