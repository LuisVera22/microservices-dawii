package com.administrativo.kardex_service.repository;

import com.administrativo.kardex_service.entity.Kardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface KardexRepository extends JpaRepository<Kardex, Integer> {
    List<Kardex> findByIdProducto(int idProducto);
    List<Kardex> findByTipoMovimiento(String tipoMovimiento);
    List<Kardex> findByFechaMovimientoBetween(LocalDateTime inicio, LocalDateTime fin);

}
