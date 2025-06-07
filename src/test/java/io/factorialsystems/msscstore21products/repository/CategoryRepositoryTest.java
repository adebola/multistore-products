package io.factorialsystems.msscstore21products.repository;

import io.factorialsystems.msscstore21products.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private final String TENANT_ID = "bd965121-7a80-4928-ab79-fd585063d6ab";

    @Test
    void findById() {
        // First, save a Category to find
        Category category = createTestCategory();
        categoryRepository.save(category);

        // Find the Category by ID
        Category foundCategory = categoryRepository.findById(category.getId());

        // Verify the Category was found
        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getId()).isEqualTo(category.getId());
        assertThat(foundCategory.getName()).isEqualTo(category.getName());
        assertThat(foundCategory.getTenantId()).isEqualTo(category.getTenantId());

        log.info("Found Category: {}", foundCategory);
    }

    @Test
    void findAllForTenant() {
        // Save a Category for the tenant
        Category category = createTestCategory();
        categoryRepository.save(category);

        // Find all Categories for the tenant
        var categories = categoryRepository.findAllForTenant(TENANT_ID);

        // Verify Categories were found
        assertThat(categories).isNotNull();
        assertThat(categories.size()).isGreaterThan(0);

        log.info("Found {} Categories for tenant", categories.size());
    }

    @Test
    void findByIdAndTenantId() {
        // Save a Category
        Category category = createTestCategory();
        categoryRepository.save(category);

        // Create parameters for search
        Map<String, String> params = new HashMap<>();
        params.put("id", category.getId());
        params.put("tenantId", TENANT_ID);

        // Find the Category by ID and tenant ID
        Category foundCategory = categoryRepository.findByIdAndTenantId(params).get();

        // Verify the Category was found
        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getId()).isEqualTo(category.getId());
        assertThat(foundCategory.getName()).isEqualTo(category.getName());

        log.info("Found Category by ID and tenant ID: {}", foundCategory);
    }

    @Test
    void searchForTenant() {
        // Save a Category with a unique name
        Category category = createTestCategory();
        String uniqueName = "TestCategory-" + UUID.randomUUID().toString().substring(0, 8);
        category.setName(uniqueName);
        categoryRepository.save(category);

        // Create parameters for search
        Map<String, String> params = new HashMap<>();
        params.put("search", uniqueName);
        params.put("tenantId", TENANT_ID);

        // Search for the Category
        var categories = categoryRepository.searchForTenant(params);

        // Verify the Category was found
        assertThat(categories).isNotNull();
        assertThat(categories.size()).isGreaterThan(0);

        boolean found = false;
        for (Category foundCategory : categories) {
            if (foundCategory.getName().equals(uniqueName)) {
                found = true;
                break;
            }
        }

        assertThat(found).isTrue();
        log.info("Found Category by search: {}", categories.get(0));
    }

    @Test
    @Rollback
    @Transactional
    void save() {
        // Create a new Category
        Category category = createTestCategory();

        // Save the Category
        categoryRepository.save(category);

        // Verify the Category was saved
        Category savedCategory = categoryRepository.findById(category.getId());

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isEqualTo(category.getId());
        assertThat(savedCategory.getName()).isEqualTo(category.getName());
        assertThat(savedCategory.getCreatedOn()).isNotNull();
        assertThat(savedCategory.getCreatedBy()).isEqualTo(category.getCreatedBy());
        assertThat(savedCategory.getDisabled()).isEqualTo(category.getDisabled());
        assertThat(savedCategory.getTenantId()).isEqualTo(category.getTenantId());

        log.info("Saved Category: {}", savedCategory);
    }

    @Test
    @Rollback
    @Transactional
    void update() {
        // First save a Category
        Category category = createTestCategory();
        categoryRepository.save(category);

        // Update the Category
        String updatedName = "Updated Category Name";
        category.setName(updatedName);
        categoryRepository.update(category);

        // Verify the Category was updated
        Category updatedCategory = categoryRepository.findById(category.getId());

        assertThat(updatedCategory).isNotNull();
        assertThat(updatedCategory.getName()).isEqualTo(updatedName);

        log.info("Updated Category: {}", updatedCategory);
    }

    @Test
    @Rollback
    @Transactional
    void failToSave() {
        // Create a Category with null name (which should be required)
        Category category = createTestCategory();
        category.setName(null);

        // Attempt to save the Category, which should fail
        assertThrows(DataIntegrityViolationException.class, () -> {
            categoryRepository.save(category);
        });

        log.info("Successfully failed to save Category with null name");
    }

    private Category createTestCategory() {
        Category category = new Category();
        category.setId(UUID.randomUUID().toString());
        category.setName("Test Category");
        category.setImageUrl("http://example.com/image.jpg");
        category.setDescription("Test Category Description");
        category.setCreatedOn(Instant.now());
        category.setCreatedBy("test-user");
        category.setDisabled(false);
        category.setTenantId(TENANT_ID);
        return category;
    }
}
