package io.factorialsystems.msscstore21products.controller;


import io.factorialsystems.msscstore21products.dto.CategoryDTO;
import io.factorialsystems.msscstore21products.dto.MessageDTO;
import io.factorialsystems.msscstore21products.dto.PagedDTO;
import io.factorialsystems.msscstore21products.service.CategoryService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerToken")
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class)))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDTO.class))})
    })
    @Operation(summary = "Get All Categories", description = "Get a Pageful of Categories, default values PageNumber =1, PageSize = 20")
    public PagedDTO<CategoryDTO> findAllForTenant(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = Constants.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }

        return categoryService.findAll(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDTO.class))})
    })
    @Operation(summary = "Get Single Category", description = "Get Single Category By Id")
    public ResponseEntity<?> findByIdAndTenant(@PathVariable("id") String id) {
        Optional<CategoryDTO> categoryClientDto = categoryService.findById(id);

        if (categoryClientDto.isEmpty()) {
            final String errorMessage = String.format("Category with Id: %s Not Found", id);
           return ResponseEntity.badRequest().body(new MessageDTO(HttpStatus.BAD_REQUEST.value(), errorMessage));
        }

        return ResponseEntity.ok(categoryClientDto.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDTO.class))})
    })
    @Operation(summary = "Create category", description = "Create category")
    @PreAuthorize("hasAnyRole('STORE_ADMIN', 'STORE_PRODUCT_ADMIN')")
    public void create(@Valid @RequestPart("category") CategoryDTO dto,
                       @RequestPart(value = "file", required = false) MultipartFile file) {

        categoryService.clientSave(dto, file);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Accepted", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDTO.class))})
    })
    @Operation(summary = "Update category", description = "Update Category")
    @PreAuthorize("hasAnyRole('STORE_ADMIN', 'STORE_PRODUCT_ADMIN')")
    public void update(@PathVariable("id") String id,
                       @Valid @RequestPart("category") CategoryDTO dto,
                       @RequestPart(value = "file", required = false) MultipartFile file) {
        categoryService.clientUpdate(id, dto, file);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CategoryDTO.class)))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDTO.class))})
    })
    @Operation(summary = "Search Category", description = "Search Category by Name")
    public PagedDTO<CategoryDTO> search(@RequestParam(value = "search") String search,
                                        @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = Constants.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }

        return categoryService.search(pageNumber, pageSize, search);
    }
}
