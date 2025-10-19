package com.service.productos.controller;

import com.service.productos.entity.Categoria;
import com.service.productos.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> getAll(){
        List<Categoria> categorias = categoriaService.getAll();
        if(categorias.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getById(@PathVariable("id") int id){
        Categoria categoria = categoriaService.getById(id);
        if(categoria == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<Categoria> save(@RequestBody Categoria categoria){
        Categoria newCategoria = categoriaService.save(categoria);
        return ResponseEntity.ok(newCategoria);
    }

}
