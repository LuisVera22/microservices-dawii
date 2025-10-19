package com.service.productos.controller;

import com.service.productos.entity.Producto;
import com.service.productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> getAll() {
        List<Producto> productos = productoService.getAll();
        if(productos.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") int id) {
        Producto producto = productoService.getById(id);
        if(producto == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(producto);
    }

    @PostMapping()
    public ResponseEntity<Producto> save(@RequestBody Producto producto) {
        Producto newProducto = productoService.save(producto);
        return ResponseEntity.ok(newProducto);
    }

    @PostMapping("/stock/{id}/{stock}")
    public ResponseEntity<Producto> updateStock(@PathVariable("id") int id, @PathVariable("stock") int stock) {
        Producto updatedProducto = productoService.updateStock(id, stock);
        return ResponseEntity.ok(updatedProducto);
    }

    @GetMapping("/bycategoria/{idcategoria}")
    public ResponseEntity<List<Producto>> getByUserId(@PathVariable("idcategoria") int idCategoria) {
        List<Producto> productos = productoService.getByIdCategoria(idCategoria);
        if(productos.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(productos);
    }
}
