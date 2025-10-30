package com.administrativo.kardex_service.repository;

import com.administrativo.kardex_service.entity.Kardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface KardexRepository extends JpaRepository<Kardex, Integer> {
    List<Kardex> findByIdProducto(int idProducto);
    List<Kardex> findByTipoMovimiento(String tipoMovimiento);
    // ✅ Ahora (usa el nombre real del campo 'fecha')
    List<Kardex> findByFechaBetween(Timestamp inicio, Timestamp fin);

}
