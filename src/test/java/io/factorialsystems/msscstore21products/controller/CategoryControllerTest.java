package io.factorialsystems.msscstore21products.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.factorialsystems.msscstore21products.dto.CategoryDTO;
import io.factorialsystems.msscstore21products.dto.PagedDTO;
import io.factorialsystems.msscstore21products.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryService categoryService;

    @Test
    void createWithoutMultipartFile() throws Exception {
        // Prepare the category DTO
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setName("Test Category");
        categoryDto.setDescription("Test Description");

        // Mock the service call
        doNothing().when(categoryService).clientSave(any(CategoryDTO.class), isNull());

        // Perform the request
        mockMvc.perform(multipart("/api/v1/category")
                        .file(new MockMultipartFile(
                                "category",      // Name of the category parameter
                                "",                    // No original file name for JSON
                                MediaType.APPLICATION_JSON_VALUE, // Content type
                                new ObjectMapper().writeValueAsBytes(categoryDto) // Category DTO as JSON bytes
                        ))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        // Verify the service was called with the correct arguments
        verify(categoryService)
                .clientSave(
                        argThat(dto -> "Test Category".equals(dto.getName())),
                        isNull()
                );
    }

    @Test
    void updateWithoutMultipartFile() throws Exception {
        // Prepare the category DTO
        final String id = UUID.randomUUID().toString();
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setName("Test Category");
        categoryDto.setDescription("Test Description");

        // Mock the service call
        doNothing().when(categoryService).clientUpdate(any(String.class), any(CategoryDTO.class), isNull());

        // Perform the request
        mockMvc.perform(multipart("/api/v1/category/{id}", id)
                        .file(new MockMultipartFile(
                                "category",            // Name of the category parameter
                                "",                    // No original file name for JSON
                                MediaType.APPLICATION_JSON_VALUE, // Content type
                                new ObjectMapper().writeValueAsBytes(categoryDto) // Category DTO as JSON bytes
                        ))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                )
                .andExpect(status().isAccepted());

        // Verify the service was called with the correct arguments
        verify(categoryService)
                .clientUpdate(
                        anyString(),
                        argThat(dto -> "Test Category".equals(dto.getName())),
                        isNull()
                );
    }

    @Test
    void updateWithMultipartFile() throws Exception {
        // Prepare the category DTO
        final String id = UUID.randomUUID().toString();
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setName("Test Category");
        categoryDto.setDescription("Test Description");

        // Mock the service call
        doNothing().when(categoryService).clientUpdate(any(String.class), any(CategoryDTO.class), any(MockMultipartFile.class));

        // Perform the request
        mockMvc.perform(multipart("/api/v1/category/{id}", id)
                                .file(new MockMultipartFile(
                                        "file",          // Name of the file parameter
                                        "test-file.txt",       // Original file name
                                        "image/png",           // Content type
                                        "This is a test file".getBytes() // File content as bytes
                                ))
                                .file(new MockMultipartFile(
                                        "category",            // Name of the category parameter
                                        "",                    // No original file name for JSON
                                        MediaType.APPLICATION_JSON_VALUE, // Content type
                                        new ObjectMapper().writeValueAsBytes(categoryDto) // Category DTO as JSON bytes
                                ))
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .with(request -> {
                                    request.setMethod("PUT");
                                    return request;
                                })
                )
                .andExpect(status().isAccepted());

        // Verify the service was called with the correct arguments
        verify(categoryService)
                .clientUpdate(
                        anyString(),
                        argThat(dto -> "Test Category".equals(dto.getName())),
                        argThat(m -> m.getName().equals("file"))
                );
    }


    @Test
    void createWithMultipartFile() throws Exception {
        // Prepare the category DTO
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setName("Test Category");
        categoryDto.setDescription("Test Description");

        // Mock the service call
        doNothing().when(categoryService).clientSave(any(CategoryDTO.class), any(MockMultipartFile.class));

        // Perform the request
        mockMvc.perform(multipart("/api/v1/category")
                        .file(new MockMultipartFile(
                                "file",                // Name of the file parameter
                                "test-file.txt",       // Original file name
                                "image/png",           // Content type
                                "This is a test file".getBytes() // File content as bytes
                        ))
                        .file(new MockMultipartFile(
                                "category",            // Name of the category parameter
                                "",                    // No original file name for JSON
                                MediaType.APPLICATION_JSON_VALUE, // Content type
                                new ObjectMapper().writeValueAsBytes(categoryDto) // Category DTO as JSON bytes
                        ))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        // Verify the service was called with the correct arguments
        verify(categoryService)
                .clientSave(
                        argThat(dto -> "Test Category".equals(dto.getName())),
                        argThat(Objects::nonNull)
                );
    }

    @Test
    void findAllForTenant() throws Exception {
        CategoryDTO clientDto = new CategoryDTO();
        clientDto.setId(UUID.randomUUID().toString());

        PagedDTO<CategoryDTO> pagedDto = new PagedDTO<>();
        pagedDto.setList(List.of(clientDto));

        given(categoryService.findAll(anyInt(), anyInt())).willReturn(pagedDto);

        final Integer pageSize = 20;
        final Integer pageNumber = 1;

        final MvcResult mvcResult = mockMvc.perform(get("/api/v1/category")
                        .queryParam("pageNumber", String.valueOf(pageNumber))
                        .queryParam("pageSize", String.valueOf(pageSize))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(5)))
                .andReturn();

        log.info("Response {}", mvcResult.getResponse().getContentAsString());
        ArgumentCaptor<Integer> pageNumberArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> pageSizeArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(categoryService).findAll(pageNumberArgumentCaptor.capture(), pageSizeArgumentCaptor.capture());

        assertThat(pageNumberArgumentCaptor.getValue()).isEqualTo(pageNumber);
        assertThat(pageSizeArgumentCaptor.getValue()).isEqualTo(pageSize);
    }

    @Test
    void findByIdAndTenant() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void uploadImage() {
    }

    @Test
    void search() {
    }
}