package com.example.ventas_service.repository;

import com.example.ventas_service.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByTipoComprobante(Venta.TipoComprobante tipoComprobante);

    List<Venta> findByClienteId(Long clienteId);

    @Query("SELECT COUNT(v) FROM Venta v WHERE v.tipoComprobante = :tipo")
    Long countByTipoComprobante(Venta.TipoComprobante tipo);
}