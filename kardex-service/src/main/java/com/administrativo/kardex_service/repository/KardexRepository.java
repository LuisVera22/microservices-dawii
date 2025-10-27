package com.administrativo.kardex_service.repository;

import com.administrativo.kardex_service.entity.Kardex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface KardexRepository extends JpaRepository<Kardex, Integer> {

    List<Kardex> findByIdProducto(int idProducto);
    List<Kardex> findByIdUsuario(int idUsuario);
    List<Kardex> findByFechaBetween(LocalDateTime fechaStart, LocalDateTime fechaEnd);
    List<Kardex> findByTipoMovimiento(String tipoMovimiento);
}
