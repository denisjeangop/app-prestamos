package com.djg.backend.service.serviceImpl;

import com.djg.backend.model.dto.PrestamoRequestDto;
import com.djg.backend.model.dto.PrestamoResponseDto;
import com.djg.backend.model.entity.PrestamoProcessEntity;
import com.djg.backend.repository.PrestamoRepository;
import com.djg.backend.service.PrestamoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Implementación del servicio de préstamos.
 * Calcula el resultado de una solicitud de préstamo según el TAE definido
 * y persiste el resultado en la base de datos.
 */
@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class PrestamoServiceImpl implements PrestamoService {

    /** Tasa Anual Equivalente fija para el cálculo de préstamos. */
    private static final double TAE = 9.67;

    private final PrestamoRepository prestamoRepository;

    /**
     * Calcula los intereses y el total a devolver de un préstamo.
     *
     * @param monto  Monto solicitado.
     * @param meses  Plazo en meses.
     * @return       DTO con el resultado del cálculo.
     */
    @Override
    public PrestamoResponseDto calcularPrestamo(double monto, int meses) {
        double intereses = monto * (TAE / 100) * (meses / 12.0);
        double total = monto + intereses;
        double cuotaMensual = total / meses;
        
        String mensaje = String.format(
                "Préstamo solicitado: %.2f€, Plazo: %d meses, TAE: %.2f%%, Intereses: %.2f€, Total a devolver: %.2f€",
                monto, meses, TAE, intereses, total
        );
        
        return new PrestamoResponseDto(monto, meses, TAE, intereses, total, mensaje);
    }

    /**
     * Procesa una solicitud de préstamo completa: calcula y persiste en BD.
     * Incluye verificación de idempotencia para evitar duplicados.
     *
     * @param requestDto      datos de la solicitud
     * @param kafkaTopic      tópico de Kafka
     * @param kafkaPartition  partición de Kafka
     * @param kafkaOffset     offset de Kafka
     * @param kafkaMessageKey clave del mensaje
     * @return entidad persistida
     */
    @Override
    @Transactional
    public PrestamoProcessEntity procesarYGuardarPrestamo(PrestamoRequestDto requestDto,
                                                         String kafkaTopic,
                                                         Integer kafkaPartition,
                                                         Long kafkaOffset,
                                                         String kafkaMessageKey) {
        
        // Verificar idempotencia
        if (prestamoRepository.existsByKafkaOffsetAndKafkaPartitionAndKafkaTopic(
                kafkaOffset, kafkaPartition, kafkaTopic)) {
            log.warn("Mensaje ya procesado - Offset: {}, Partition: {}, Topic: {}", 
                    kafkaOffset, kafkaPartition, kafkaTopic);
            return prestamoRepository.findByKafkaOffsetAndKafkaPartition(kafkaOffset, kafkaPartition)
                    .orElseThrow(() -> new IllegalStateException("Error en verificación de idempotencia"));
        }

        try {
            // Calcular préstamo
            PrestamoResponseDto responseDto = calcularPrestamo(requestDto.getMonto(), requestDto.getMeses());
            
            // Crear entidad para persistir
            PrestamoProcessEntity entity = PrestamoProcessEntity.builder()
                    .dni(requestDto.getDni())
                    .monto(BigDecimal.valueOf(requestDto.getMonto()).setScale(2, RoundingMode.HALF_UP))
                    .meses(requestDto.getMeses())
                    .cuotaMensual(BigDecimal.valueOf(responseDto.getTotalDevolver() / requestDto.getMeses()).setScale(2, RoundingMode.HALF_UP))
                    .tasaInteres(BigDecimal.valueOf(responseDto.getTae()).setScale(2, RoundingMode.HALF_UP))
                    .totalPagar(BigDecimal.valueOf(responseDto.getTotalDevolver()).setScale(2, RoundingMode.HALF_UP))
                    .estado(PrestamoProcessEntity.EstadoPrestamo.CALCULADO)
                    .kafkaTopic(kafkaTopic)
                    .kafkaPartition(kafkaPartition)
                    .kafkaOffset(kafkaOffset)
                    .kafkaMessageKey(kafkaMessageKey)
                    .build();

            // Persistir en base de datos
            PrestamoProcessEntity savedEntity = prestamoRepository.save(entity);
            
            log.info("Préstamo procesado y guardado exitosamente - ID: {}, Monto: {}, Meses: {}", 
                    savedEntity.getId(), savedEntity.getMonto(), savedEntity.getMeses());
            
            return savedEntity;
            
        } catch (Exception e) {
            log.error("Error procesando préstamo - Offset: {}, Error: {}", kafkaOffset, e.getMessage(), e);
            
            // Guardar el error para auditoría
            PrestamoProcessEntity errorEntity = PrestamoProcessEntity.builder()
                    .dni(requestDto.getDni())
                    .monto(BigDecimal.valueOf(requestDto.getMonto()).setScale(2, RoundingMode.HALF_UP))
                    .meses(requestDto.getMeses())
                    .estado(PrestamoProcessEntity.EstadoPrestamo.ERROR)
                    .kafkaTopic(kafkaTopic)
                    .kafkaPartition(kafkaPartition)
                    .kafkaOffset(kafkaOffset)
                    .kafkaMessageKey(kafkaMessageKey)
                    .build();
            
            prestamoRepository.save(errorEntity);
            throw new RuntimeException("Error procesando préstamo", e);
        }
    }
}