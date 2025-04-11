package io.factorialsystems.msscstore21products.dto;

public record AuditRequestDTO(String action, String message, String userName, String tenantId) { }
