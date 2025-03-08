package io.factorialsystems.msscstore21products.model;


import io.factorialsystems.msscstore21products.dto.CategoryClientDto;
import io.factorialsystems.msscstore21products.security.JwtTokenWrapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Getter
    private String id;

    @Getter
    private String name;

    private String imageUrl;
    private Date createdOn;
    private String createdBy;
    private Boolean deleted;
    private String deletedBy;
    private Date deletedOn;
    private Boolean suspended;

    static public Category createCategory(String name, String imageUrl) {
        Category c = new Category();
        c.id = UUID.randomUUID().toString();
        c.name = name;
        c.imageUrl = imageUrl;
        c.createdBy = JwtTokenWrapper.getUserName();
        return c;
    }

    static public Category createCategoryFromDto(CategoryClientDto dto) {
        Category c = new Category();
        c.id = dto.getId();
        c.name = dto.getName();
        c.imageUrl = dto.getImageUrl();

        return c;
    }

    public CategoryClientDto createClientDto() {
        return new CategoryClientDto(id, name, imageUrl);
    }
}
