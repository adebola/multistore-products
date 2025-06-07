package io.factorialsystems.msscstore21products.model;


import io.factorialsystems.msscstore21products.dto.CategoryDTO;
import io.factorialsystems.msscstore21products.security.JwtTokenWrapper;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;


@Getter
@Setter
public class Category {
    private String id;
    private String name;
    private String imageUrl;
    private String createdBy;
    private Instant createdOn;
    private String description;
    private Boolean disabled;
    private String tenantId;

    static public Category createCategory(String name, String imageUrl, String description) {
        Category c = new Category();
        c.id = UUID.randomUUID().toString();
        c.name = name;
        c.imageUrl = imageUrl;
        c.description = description;
        c.createdBy = JwtTokenWrapper.getUserName();
        c.tenantId = JwtTokenWrapper.getTenantId();
        c.disabled = false;
        return c;
    }

    public CategoryDTO createClientDto() {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setImageUrl(imageUrl);
        dto.setDescription(description);
        dto.setCreatedBy(createdBy);
        dto.setCreatedOn(createdOn);
        dto.setDisabled(disabled);
        return dto;
    }
}
