package io.factorialsystems.msscstore21products.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatusMessageDto {
    private Integer status;
    private String message;
}