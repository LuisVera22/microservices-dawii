package com.example.cliente_service.repository;

import com.example.cliente_service.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByDniAndActivoTrue(String dni);

    Optional<Cliente> findByRucAndActivoTrue(String ruc);

    List<Cliente> findByTipoDocumento(Cliente.TipoDocumento tipoDocumento);

    List<Cliente> findByActivoTrue();
}
