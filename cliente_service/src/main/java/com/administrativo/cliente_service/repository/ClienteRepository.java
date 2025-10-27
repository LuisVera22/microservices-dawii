package com.administrativo.cliente_service.repository;

import com.administrativo.cliente_service.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
}
