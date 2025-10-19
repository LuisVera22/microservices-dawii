package com.proyecto.ventas_service.repository;

import com.proyecto.ventas_service.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}
