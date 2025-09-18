package com.djg.backend.service;

import com.djg.backend.model.dto.PrestamoRequestDto;
import com.djg.backend.model.dto.PrestamoResponseDto;
import com.djg.backend.model.entity.PrestamoProcessEntity;

/**
 * Servicio para operaciones relacionadas con préstamos.
 */
public interface PrestamoService {

    /**
     * Calcula los intereses y el total a devolver de un préstamo.
     *
     * @param monto  Monto solicitado.
     * @param meses  Plazo en meses.
     * @return       DTO con el resultado del cálculo.
     */
    PrestamoResponseDto calcularPrestamo(double monto, int meses);

    /**
     * Procesa una solicitud de prestamo completa: calcula y persiste en BD.
     * Incluye verificación de idempotencia para evitar duplicados.
     *
     * @param requestDto      datos de la solicitud
     * @param kafkaTopic      tópico de Kafka
     * @param kafkaPartition  partición de Kafka
     * @param kafkaOffset     offset de Kafka
     * @param kafkaMessageKey clave del mensaje
     * @return entidad persistida
     */
    PrestamoProcessEntity procesarYGuardarPrestamo(PrestamoRequestDto requestDto,
                                                  String kafkaTopic,
                                                  Integer kafkaPartition,
                                                  Long kafkaOffset,
                                                  String kafkaMessageKey);
}