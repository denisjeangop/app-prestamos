package com.djg.query.repository;

import com.djg.query.model.dto.PrestamoQueryDto;
import com.djg.query.model.entity.PrestamoQueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para consultas de préstamos procesados.
 * Implementa el patrón CQRS - Query Side con operaciones de solo lectura.
 * Utiliza consultas JPQL optimizadas para filtros dinámicos.
 *
 * @author Denis Jean Gopanchuk
 */
@Repository
public interface PrestamoQueryRepository extends JpaRepository<PrestamoQueryEntity, Long> {

    /**
     * Busca préstamos aplicando filtros opcionales de identificación y estado.
     * Los parámetros null son ignorados en la consulta.
     *
     * @param identificacion DNI del cliente (opcional)
     * @param estado Estado del préstamo (opcional)
     * @return Lista de préstamos que coinciden con los filtros
     */
    @Query("SELECT p FROM PrestamoQueryEntity p WHERE " +
           "(:identificacion IS NULL OR p.dni = :identificacion) AND " +
           "(:estado IS NULL OR p.estado = :estado) " +
           "ORDER BY p.fechaCreacion DESC")
       List<PrestamoQueryEntity> buscarConFiltros(
        @Param("identificacion") String identificacion,
        @Param("estado") PrestamoQueryEntity.EstadoPrestamo estado
    );

    /**
     * Encuentra todos los préstamos de un cliente específico.
     *
     * @param dni DNI del cliente
     * @return Lista de préstamos del cliente ordenados por fecha de creación
     */
    @Query("SELECT p FROM PrestamoQueryEntity p WHERE p.dni = :dni ORDER BY p.fechaCreacion DESC")
    List<PrestamoQueryEntity> findByDniOrderByFechaCreacionDesc(@Param("dni") String dni);

    /**
     * Encuentra todos los préstamos por estado específico.
     *
     * @param estado Estado del préstamo
     * @return Lista de préstamos con el estado especificado
     */
    @Query("SELECT p FROM PrestamoQueryEntity p WHERE p.estado = :estado ORDER BY p.fechaCreacion DESC")
    List<PrestamoQueryEntity> findByEstadoOrderByFechaCreacionDesc(@Param("estado") PrestamoQueryEntity.EstadoPrestamo estado);
}