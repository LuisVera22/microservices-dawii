package com.service.productos.repository;

import com.service.productos.entity.Categoria;
import com.service.productos.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {
    @Query("SELECT p FROM Producto p WHERE p.categoria.id_categoria = :idCategoria")
    List<Producto> buscarPorIdCategoria(@Param("idCategoria") int idCategoria);
}
