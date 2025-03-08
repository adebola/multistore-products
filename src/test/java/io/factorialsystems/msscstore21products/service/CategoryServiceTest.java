package io.factorialsystems.msscstore21products.service;


import io.factorialsystems.msscstore21products.dto.CategoryClientDto;
import io.factorialsystems.msscstore21products.dto.PagedDto;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@CommonsLog
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void clientSave() {
        CategoryClientDto dto = new CategoryClientDto();
        dto.setName("Shirts");
        dto.setImageUrl("http://www.nag.co.uk");
        categoryService.clientSave(dto);

        log.info(categoryService.findAll(1, 20));
    }

    @Test
    void clientUpdate() {
        final String id = "8e963964-f1a7-11ed-a880-e4ce5d66e85a";
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);

        Optional<CategoryClientDto> optional = categoryService.findById(id);
        assertThat(optional.isPresent()).isEqualTo(true);

        CategoryClientDto dto = optional.get();

        dto.setName(generatedString);
        categoryService.clientUpdate(id, dto);

        assertEquals(categoryService.findById(id).get().getName(), generatedString);
    }

    @Test
    void findAll() {
        PagedDto<CategoryClientDto> all = categoryService.findAll(1, 20);
        assertNotNull(all);
        assert(all.getPageSize() > 0);
        log.info(all.getList());
    }

    @Test
    void search() {
        PagedDto<CategoryClientDto> s = categoryService.search(1, 20, "s");
        assertNotNull(s);
        assert (s.getPageSize() > 0);
        log.info(s.getList());
    }

    @Test
    void clientDelete() {
    }

    @Test
    void clientSuspend() {
    }

    @Test
    void clientUnsuspend() {
    }
}