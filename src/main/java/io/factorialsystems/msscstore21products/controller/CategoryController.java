package io.factorialsystems.msscstore21products.controller;


import io.factorialsystems.msscstore21products.dto.CategoryClientDto;
import io.factorialsystems.msscstore21products.dto.MessageDto;
import io.factorialsystems.msscstore21products.dto.PagedDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerToken")
@RequestMapping("/api/v1/product/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CategoryClientDto.class)))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Get All Categories", description = "Get a Pageful of Categories, default values PageNumber =1, PageSize = 20")
    public PagedDto<CategoryClientDto> findAll(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
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
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryClientDto.class))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Get Single Category", description = "Get Single Category By Id")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        Optional<CategoryClientDto> categoryClientDto = categoryService.findById(id);

        if (categoryClientDto.isEmpty()) {
            final String errorMessage = String.format("Category with Id: %s Not Found", id);
           return ResponseEntity.badRequest().body(new MessageDto(HttpStatus.BAD_REQUEST.value(), errorMessage));
        }

        return ResponseEntity.ok(categoryClientDto.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Create category", description = "Create Category")
    public void create(@Valid @RequestBody CategoryClientDto dto) {
        categoryService.clientSave(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Accepted", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Update category", description = "Update Category")
    public void update(@PathVariable("id") String id, @Valid @RequestBody CategoryClientDto dto) {
        categoryService.clientUpdate(id, dto);
    }

    @PutMapping("/suspend/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Suspend category", description = "Suspend Category")
    public void suspend(@PathVariable("id") String id) {
        categoryService.clientSuspend(id);
    }

    @PutMapping("/unsuspend/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Un-Suspend category", description = "Un-Suspend Category")
    public void unsuspend(@PathVariable("id") String id) {
        categoryService.clientUnsuspend(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Delete category", description = "Delete Category")
    public void delete(@PathVariable("id") String id) {
        categoryService.clientDelete(id);
    }

    @GetMapping("/search/{search}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CategoryClientDto.class)))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Search Category", description = "Search Category by Name")
    public PagedDto<CategoryClientDto> search(@PathVariable("search") String search,
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
