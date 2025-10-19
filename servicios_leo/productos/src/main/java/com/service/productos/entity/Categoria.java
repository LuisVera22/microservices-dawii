package com.service.productos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_categoria;
    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "categoria",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Producto> productos;
}
