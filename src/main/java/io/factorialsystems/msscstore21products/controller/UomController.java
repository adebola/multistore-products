package io.factorialsystems.msscstore21products.controller;


import io.factorialsystems.msscstore21products.dto.MessageDto;
import io.factorialsystems.msscstore21products.dto.PagedDto;
import io.factorialsystems.msscstore21products.dto.UomClientDto;
import io.factorialsystems.msscstore21products.service.UomService;
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
@RequestMapping("/api/v1/product/uom")
public class UomController {
    private final UomService uomService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UomClientDto.class)))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Get All Unit of Measure", description = "Get a Pageful of Unit of Measure, default values PageNumber =1, PageSize = 20")
    public PagedDto<UomClientDto> findAll(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = Constants.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }

        return uomService.findAll(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UomClientDto.class))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Get Single Unit of Measure", description = "Get Single Unit of Measure By Id")
    public UomClientDto findById(@PathVariable("id") String id) {
        return uomService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Create Unit of Measure", description = "Create Unit of Measure")
    public void create(@Valid @RequestBody UomClientDto dto) {
        uomService.clientSave(dto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Accepted", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Update Unit of Measure", description = "Update Unit of Measure")
    public void update(@PathVariable("id") String id, @Valid @RequestBody UomClientDto dto) {
        uomService.clientUpdate(id, dto);
    }

    @PutMapping("/suspend/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Suspend Unit of Measure", description = "Suspend Unit of Measure")
    public void suspend(@PathVariable("id") String id) {
        uomService.clientSuspend(id);
    }

    @PutMapping("/unsuspend/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Un-Suspend Unit of Measure", description = "Un-Suspend Unit of Measure")
    public void unsuspend(@PathVariable("id") String id) {
        uomService.clientUnsuspend(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Delete Unit of Measure", description = "delete Unit of Measure")
    public void delete(@PathVariable("id") String id) {
        uomService.clientDelete(id);
    }

    @GetMapping("/search/{search}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UomClientDto.class)))}),
            @ApiResponse(responseCode = "400", description = "Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MessageDto.class))})
    })
    @Operation(summary = "Search Unit of Measure", description = "Search Unit of Measure by Name")
    public PagedDto<UomClientDto> search(@PathVariable("search") String search,
                                              @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = Constants.DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }

        return uomService.search(pageNumber, pageSize, search);
    }
}
