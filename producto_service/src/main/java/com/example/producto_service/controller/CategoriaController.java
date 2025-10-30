package com.example.producto_service.controller;

import com.example.producto_service.entity.Categoria;
import com.example.producto_service.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping
    public List<Categoria> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public Categoria obtener(@PathVariable Integer id) { return service.obtener(id); }

    @PostMapping
    public Categoria crear(@RequestBody Categoria c) { return service.crear(c); }

    @PutMapping("/{id}")
    public Categoria actualizar(@PathVariable Integer id, @RequestBody Categoria c) {
        return service.actualizar(id, c);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) { service.eliminar(id); }
}
