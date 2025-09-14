package com.djg.backend.service.serviceImpl;

import com.djg.backend.model.dto.PrestamoRequestDto;
import com.djg.backend.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de préstamos.
 * Envía la solicitud de préstamo al topic de Kafka correspondiente.
 */
@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private KafkaTemplate<String, PrestamoRequestDto> kafkaTemplate;

    /**
     * Envía el DTO de préstamo al topic "prestamo-topic" en Kafka.
     *
     * @param prestamoRequestDto datos de la solicitud de préstamo
     */
    @Override
    public void enviarPrestamo(PrestamoRequestDto prestamoRequestDto) {
        kafkaTemplate.send("prestamo-topic", prestamoRequestDto);
    }
}