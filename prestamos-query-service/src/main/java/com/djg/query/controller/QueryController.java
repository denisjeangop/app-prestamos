package com.djg.query.controller;

import com.djg.query.model.dto.BusquedaPrestamoRequestDto;
import com.djg.query.model.dto.PrestamoQueryDto;
import com.djg.query.service.PrestamoQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
@Slf4j
public class QueryController {

    private final PrestamoQueryService prestamoQueryService;

    @PostMapping("/buscar")
    public ResponseEntity<List<PrestamoQueryDto>> getPrestamosByFiltro (@RequestBody BusquedaPrestamoRequestDto busquedaPrestamoRequestDto){
        List<PrestamoQueryDto> prestamoQueryDtoList = this.prestamoQueryService.buscarPrestamos(busquedaPrestamoRequestDto);
        return ResponseEntity.ok(prestamoQueryDtoList);
    }

}
