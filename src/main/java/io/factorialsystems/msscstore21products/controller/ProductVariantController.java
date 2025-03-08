package io.factorialsystems.msscstore21products.controller;

import io.factorialsystems.msscstore21products.dto.MessageDto;
import io.factorialsystems.msscstore21products.dto.PagedDto;
import io.factorialsystems.msscstore21products.dto.ProductVariantClientDto;
import io.factorialsystems.msscstore21products.service.ProductVariantService;
import io.factorialsystems.msscstore21products.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerToken")
@RequestMapping("/api/v1/product/variant")
public class ProductVariantController {
    private final ProductVariantService productVariantService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductVariantClientDto.class)))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Get List of Product Variants", description = "Retrieves a List of Product Variants from the database by default PageNumber = 1 and PageSize = 20")
    public PagedDto<ProductVariantClientDto> findAll(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = Constants.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }

        return productVariantService.findAll(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductVariantClientDto.class))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Get Single Product Variant", description = "Get Single Product Variant")
    public ProductVariantClientDto findById(@PathVariable("id") String id) {
        return productVariantService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Create Product variant", description = "Create Single Product Variant")
    public void create(@Valid @RequestBody ProductVariantClientDto dto) {
        productVariantService.clientSave(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Accepted", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Update Product variant", description = "Update Single Product Variant")
    public void update(@PathVariable("id") String id, @Valid @RequestBody ProductVariantClientDto dto) {
        productVariantService.clientUpdate(id, dto);
    }

    @PutMapping("/suspend/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "No Content", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Suspend Product variant", description = "Suspend Single Product Variant")
    public void suspend(@PathVariable("id") String id) {
        productVariantService.clientSuspend(id);
    }

    @PutMapping("/unsuspend/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "No Content", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Un-Suspend Product variant", description = "Un-Suspend Single Product Variant")
    public void unsuspend(@PathVariable("id") String id) {
        productVariantService.clientUnsuspend(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "No Content", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Delete Product variant", description = "Delete Single Product Variant")
    public void delete(@PathVariable("id") String id) {
        productVariantService.clientDelete(id);
    }

    @GetMapping("/search/{search}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductVariantClientDto.class)))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MessageDto.class)))})
    })
    @Operation(summary = "Search Product Variant", description = "Search Product Variant by Name")
    public PagedDto<ProductVariantClientDto> search(@PathVariable("search") String search,
                                                     @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = Constants.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }

        return productVariantService.search(pageNumber, pageSize, search);
    }
}


//@ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "All Employees returned", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Employee.class)))}),
//        @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema(hidden = true))} ),
//        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(schema = @Schema(hidden = true))} )
//})