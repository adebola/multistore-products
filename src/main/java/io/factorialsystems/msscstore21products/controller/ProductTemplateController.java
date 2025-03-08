package io.factorialsystems.msscstore21products.controller;


import io.factorialsystems.msscstore21products.dto.MessageDto;
import io.factorialsystems.msscstore21products.dto.PagedDto;
import io.factorialsystems.msscstore21products.dto.ProductClientDto;
import io.factorialsystems.msscstore21products.dto.ProductTemplateClientDto;
import io.factorialsystems.msscstore21products.service.ProductTemplateService;
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
@RequestMapping("/api/v1/product/template")
public class ProductTemplateController {
    private final ProductTemplateService productTemplateService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductTemplateClientDto.class)))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Get List of Product Templates", description = "Retrieves a List of Product Templates from the database by default PageNumber = 1 and PageSize = 20")
    public PagedDto<ProductTemplateClientDto> findAll(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = Constants.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }

        return productTemplateService.findAll(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductClientDto.class))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Get Single Product Template", description = "Get Single Product Template")
    public ProductTemplateClientDto findById(@PathVariable("id") String id) {
        return productTemplateService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Create Product Template", description = "Create Single Product Template")
    public void create(@Valid @RequestBody ProductTemplateClientDto dto) {
        productTemplateService.clientSave(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Accepted", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Update Product Template", description = "Update Product Template")
    public void update(@PathVariable("id") String id, @Valid @RequestBody ProductTemplateClientDto dto) {
        productTemplateService.clientUpdate(id, dto);
    }

    @PutMapping("/suspend/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Suspend product Template", description = "Suspend Product Template")
    public void suspend(@PathVariable("id") String id) {
        productTemplateService.clientSuspend(id);
    }

    @PutMapping("/unsuspend/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Un-Suspend product Template", description = "Un-Suspend Product Template")
    public void unsuspend(@PathVariable("id") String id) {
        productTemplateService.clientUnsuspend(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Delete product Template", description = "Delete Product Template")
    public void delete(@PathVariable("id") String id) {
        productTemplateService.clientDelete(id);
    }

    @GetMapping("/search/{search}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductTemplateClientDto.class)))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Search Product Template", description = "Search Product Template by Name")
    public PagedDto<ProductTemplateClientDto> search(@PathVariable("search") String search,
                                              @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = Constants.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }

        return productTemplateService.search(pageNumber, pageSize, search);
    }
}
