package com.djg.backend.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa una solicitud de préstamo.
 * Contiene el monto solicitado y el número de meses para el pago.
 *
 * @author Denis Jean Gopanchuk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoRequestDto {
    /**
     * DNI del cliente solicitante del préstamo.
     */
    @NotNull(message = "El DNI no puede ser nulo.")
    private String dni;
    
    /**
     * Monto total solicitado para el préstamo.
     */
    @NotNull(message = "El monto no puede ser nulo.")
    @Min(value = 1, message = "El monto debe ser mayor que cero.")
    @Max(value = 100_000, message = "No se puede solicitar un préstamo superior a cien mil euros.")
    private Double monto;

    /**
     * Número de meses para pagar el préstamo.
     */
    @NotNull(message = "El plazo no puede ser nulo.")
    @Min(value = 1, message = "El plazo debe ser mayor que cero.")
    @Max(value = 100, message = "No se puede pedir un préstamo superior a cien meses.")
    private Integer meses;
}