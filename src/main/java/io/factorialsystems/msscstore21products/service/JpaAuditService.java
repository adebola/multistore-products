package io.factorialsystems.msscstore21products.service;

import io.factorialsystems.msscstore21products.dto.AuditRequestDTO;
import io.factorialsystems.msscstore21products.security.JwtTokenWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaAuditService {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.audit.key}")
    private String routingKey;
    private final RabbitTemplate rabbitTemplate;

    public void audit(String action, String message) {
        AuditRequestDTO request = new AuditRequestDTO(
                action,
                message,
                JwtTokenWrapper.getUserName(),
                JwtTokenWrapper.getTenantId()
        );

        rabbitTemplate.convertAndSend(exchange, routingKey, request);
    }
}
