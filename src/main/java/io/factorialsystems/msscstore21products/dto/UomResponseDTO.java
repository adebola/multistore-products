package io.factorialsystems.msscstore21products.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UomResponseDTO {
    private String id;
    private String name;
    private Boolean disabled;
    private String createdBy;
    private Instant createdOn;

    public UomResponseDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
