package com.djg.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la respuesta de cálculo de préstamo.
 * <p>
 * Incluye información sobre el monto solicitado, número de meses,
 * TAE, intereses, total a devolver y un mensaje descriptivo.
 * <p>
 * Se utilizan tipos envolventes para permitir valores nulos en la serialización/deserialización.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoResponseDto {
    /** Monto solicitado para el préstamo */
    private Double monto;

    /** Número de meses para el préstamo */
    private Integer meses;

    /** Tasa Anual Equivalente (TAE) aplicada */
    private Double tae;

    /** Intereses generados por el préstamo */
    private Double intereses;

    /** Total a devolver al finalizar el préstamo */
    private Double totalDevolver;

    /** Mensaje descriptivo de la operación */
    private String mensaje;
}