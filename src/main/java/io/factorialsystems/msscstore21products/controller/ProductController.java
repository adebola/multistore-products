package io.factorialsystems.msscstore21products.controller;


import io.factorialsystems.msscstore21products.dto.*;
import io.factorialsystems.msscstore21products.service.product.ProductService;
import io.factorialsystems.msscstore21products.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@SecurityRequirement(name = "bearerToken")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
//    @Timed(value = "getproduct.time", description = "Time taken to retrieve products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDTO.class))})
    })
    @Operation(summary = "Get List of Products", description = "Retrieves a List of Products from the database by default PageNumber = 1 and PageSize = 20")
    public PagedDTO<ProductResponseDTO> getProducts(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = Constants.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }

        return productService.findAll(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDTO.class))})
    })
    @Operation(summary = "Get Single Product", description = "Get Single Product")
    public ProductResponseDTO getProductById(@PathVariable("id") String id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDTO.class))})
    })
    @Operation(summary = "Create Product", description = "Create Single Product")
    public void createProduct(@Valid @RequestPart("product") CreateProductDTO dto,
                              @RequestParam(value = "file", required = false) MultipartFile file) {
        productService.createProduct(dto, file);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Accepted", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDTO.class))})
    })
    @Operation(summary = "Update Product", description = "Update Single Product")
    public void updateProduct(@PathVariable("id") String id,
                              @Valid @RequestPart(value = "product") UpdateProductDTO dto,
                              @RequestParam(value = "file", required = false) MultipartFile file) {
        productService.updateProduct(id, dto, file);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDTO.class))})
    })
    @Operation(summary = "Search Products", description = "Search  List of Products from the database by default PageNumber = 1 and PageSize = 20")
    public PagedDTO<ProductResponseDTO> search(@RequestParam(value = "search", required = true) String search,
                                               @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                               @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = Constants.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }

        return productService.search(pageNumber, pageSize, search);
    }
}
