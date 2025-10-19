package com.proyecto.ventas_service.repository;

import com.proyecto.ventas_service.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
