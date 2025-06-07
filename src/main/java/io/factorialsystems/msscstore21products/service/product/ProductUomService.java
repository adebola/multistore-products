package io.factorialsystems.msscstore21products.service.product;

public interface ProductUomService {
    void addUomToProduct(String productId, String uomName);
    void removeUomFromProduct(String productId, String uomId);
    void editUom(String productId, String uomId, String newUomName);
    void disableUom(String productId, String uomId);
    void enableUom(String productId, String uomId);
}
