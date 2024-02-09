package com.app.orderservice.config;

import com.app.orderservice.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerConfig {
    private final KafkaTemplate<String, OrderDto> orderKafkaTemplate;

    public void sendMessage(OrderDto orderDtoObj) {
        log.info("Inside sendMessage of KafkaProducerConfig");
        log.info("{}", orderDtoObj);
        Message<OrderDto> orderDtoMessage = MessageBuilder
                .withPayload(orderDtoObj)
                .setHeader(KafkaHeaders.TOPIC, "order-service-topic")
                .build();
        orderKafkaTemplate.send(orderDtoMessage);
    }

}
