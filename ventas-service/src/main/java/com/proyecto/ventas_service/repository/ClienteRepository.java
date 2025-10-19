package com.proyecto.ventas_service.repository;

import com.proyecto.ventas_service.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
