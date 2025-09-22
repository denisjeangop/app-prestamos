package com.djg.backend.controller;

import com.djg.backend.model.dto.PrestamoRequestDto;
import com.djg.backend.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * Controlador REST para gestionar solicitudes de préstamo.
 * Valida los datos y envía la solicitud al servicio correspondiente.
 */
@RestController
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    /**
     * Recibe y valida una solicitud de préstamo.
     * Si es válida, la envía al servicio; si no, devuelve un error.
     *
     * @param prestamoRequestDto datos de la solicitud de préstamo
     * @return respuesta HTTP con el resultado de la operación
     */
    @PostMapping
    public ResponseEntity<String> enviarPrestamo(@Valid @RequestBody PrestamoRequestDto prestamoRequestDto) {
        prestamoService.enviarPrestamo(prestamoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Solicitud de préstamo enviada correctamente.");
    }




}