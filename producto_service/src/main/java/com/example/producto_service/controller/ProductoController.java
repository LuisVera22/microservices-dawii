package com.example.producto_service.controller;

import com.example.producto_service.entity.Producto;
import com.example.producto_service.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    @GetMapping
    public List<Producto> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public Producto obtener(@PathVariable Integer id) { return service.obtener(id); }

    @GetMapping("/buscar")
    public List<Producto> buscarPorNombre(@RequestParam String nombre) {
        return service.buscarPorNombre(nombre);
    }

    @GetMapping("/{id}/stock-disponible")
    public Integer stock(@PathVariable Integer id) { return service.stockDisponible(id); }

    @PostMapping
    public Producto crear(@RequestBody Producto p) { return service.crear(p); }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Integer id, @RequestBody Producto p) {
        return service.actualizar(id, p);
    }

    @PutMapping("/{id}/stock")
    public Producto actualizarStock(@PathVariable Integer id, @RequestParam Integer nuevoStock) {
        return service.actualizarStock(id, nuevoStock);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) { service.eliminar(id); }
}
