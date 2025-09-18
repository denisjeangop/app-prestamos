package com.djg.backend.listeners;

import com.djg.backend.model.dto.PrestamoRequestDto;
import com.djg.backend.model.entity.PrestamoProcessEntity;
import com.djg.backend.service.PrestamoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class PrestamoRequestListener {

    private final PrestamoService prestamoService;

    /**
     * Listener para solicitudes de préstamo.
     * Recibe PrestamoRequestDto, calcula la respuesta y persiste en BD.
     * Incluye información de Kafka para idempotencia y auditoría.
     */
    @KafkaListener(
            topics = "prestamo-topic",
            groupId = "prestamo-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenPrestamoRequest(@Payload PrestamoRequestDto requestDto,
                                     @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                     @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                                     @Header(KafkaHeaders.OFFSET) Long offset,
                                     @Header(value = "kafka_messageKey", required = false) String messageKey) {
        
        log.info("PRESTAMO REQUEST LISTENER ::: Recibido mensaje - Topic: {}, Partition: {}, Offset: {}, Key: {}, DTO: {}", 
                topic, partition, offset, messageKey, requestDto);

        try {
            // Procesar y guardar en BD
            PrestamoProcessEntity savedEntity = prestamoService.procesarYGuardarPrestamo(
                    requestDto, topic, partition, offset, messageKey
            );

            log.info("PRESTAMO REQUEST LISTENER ::: Préstamo procesado exitosamente - ID: {}, Estado: {}", 
                    savedEntity.getId(), savedEntity.getEstado());

        } catch (Exception e) {
            log.error("PRESTAMO REQUEST LISTENER ::: Error procesando mensaje - Offset: {}, Error: {}", 
                    offset, e.getMessage(), e);
        }
    }
}