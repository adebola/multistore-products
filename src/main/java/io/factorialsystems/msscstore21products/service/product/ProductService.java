package io.factorialsystems.msscstore21products.service.product;

import io.factorialsystems.msscstore21products.dto.CreateProductDTO;
import io.factorialsystems.msscstore21products.dto.PagedDTO;
import io.factorialsystems.msscstore21products.dto.ProductResponseDTO;
import io.factorialsystems.msscstore21products.dto.UpdateProductDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductResponseDTO findById(String id);
    PagedDTO<ProductResponseDTO> findAll(int pageNumber, int pageSize);
    PagedDTO<ProductResponseDTO> search(int pageNumber, int pageSize, String search);
    String createProduct(CreateProductDTO dto, MultipartFile file);
    int updateProduct(String id, UpdateProductDTO dto, MultipartFile file);
}
