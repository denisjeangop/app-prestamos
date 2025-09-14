package com.djg.backend.listeners;

import com.djg.backend.model.dto.PrestamoRequestDto;
import com.djg.backend.model.dto.PrestamoResponseDto;
import com.djg.backend.service.PrestamoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class PrestamoRequestListener {

    @Autowired
    private PrestamoService prestamoService;

    /**
     * Listener para solicitudes de préstamo.
     * Recibe PrestamoRequestDto y calcula la respuesta.
     */
    @KafkaListener(
            topics = "prestamo-topic",
            groupId = "prestamo-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenPrestamoRequest(PrestamoRequestDto requestDto) {
        log.info("PRESTAMO REQUEST LISTENER ::: Recibido DTO: {}", requestDto);

        PrestamoResponseDto responseDto = prestamoService.calcularPrestamo(
                requestDto.getMonto(), requestDto.getMeses()
        );

        log.info("PRESTAMO REQUEST LISTENER ::: Respuesta calculada: {}", responseDto);
    }
}