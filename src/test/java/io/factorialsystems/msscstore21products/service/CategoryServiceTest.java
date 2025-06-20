package io.factorialsystems.msscstore21products.service;


import io.factorialsystems.msscstore21products.dto.CategoryDTO;
import io.factorialsystems.msscstore21products.dto.PagedDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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
    private CategoryServiceImpl categoryService;

    @Test
    @Transactional
    @Rollback
    void clientSave() {
        CategoryDTO dto = new CategoryDTO();
        dto.setName("Shirts");
        dto.setImageUrl("http://www.nag.co.uk");
        categoryService.clientSave(dto, null);

        log.info(categoryService.findAll(1, 20));
    }

    @Test
    void clientUpdate() {
        final String id = "8e963964-f1a7-11ed-a880-e4ce5d66e85a";
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);

        Optional<CategoryDTO> optional = categoryService.findById(id);
        assertThat(optional.isPresent()).isEqualTo(true);

        CategoryDTO dto = optional.get();

        dto.setName(generatedString);
        categoryService.clientUpdate(id, dto, null);

        assertEquals(categoryService.findById(id).get().getName(), generatedString);
    }

    @Test
    void findAll() {
        PagedDTO<CategoryDTO> all = categoryService.findAll(1, 20);
        assertNotNull(all);
        assert(all.getPageSize() > 0);
        log.info(all.getList());
    }

    @Test
    void search() {
        PagedDTO<CategoryDTO> s = categoryService.search(1, 20, "s");
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