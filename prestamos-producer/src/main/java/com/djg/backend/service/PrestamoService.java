package com.djg.backend.service;

import com.djg.backend.model.dto.PrestamoRequestDto;

/**
 * Interfaz para el servicio de préstamos.
 * Define el contrato para enviar solicitudes de préstamo.
 */
public interface PrestamoService {
    /**
     * Envía una solicitud de préstamo.
     *
     * @param prestamo datos de la solicitud de préstamo
     */
    void enviarPrestamo(PrestamoRequestDto prestamo);
}