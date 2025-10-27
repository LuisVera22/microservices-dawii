package com.administrativo.ventas_service.Repository;

import com.administrativo.ventas_service.Entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer>{


}
