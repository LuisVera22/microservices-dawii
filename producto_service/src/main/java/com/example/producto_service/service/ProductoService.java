package com.example.producto_service.service;

import com.example.producto_service.entity.Categoria;
import com.example.producto_service.entity.Producto;
import com.example.producto_service.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository repo;
    private final CategoriaService categoriaService;

    public List<Producto> listar() { return repo.findAll(); }

    public Producto obtener(Integer id) { return repo.findById(id).orElse(null); }

    public Producto crear(Producto p) {
        Categoria categoria = categoriaService.obtener(p.getCategoria().getId());
        p.setCategoria(categoria);
        return repo.save(p);
    }

    public Producto actualizar(Integer id, Producto p) {
        Producto actual = obtener(id);
        actual.setNombre(p.getNombre());
        actual.setPrecio(p.getPrecio());
        actual.setStockActual(p.getStockActual());
        actual.setCategoria(categoriaService.obtener(p.getCategoria().getId()));
        return repo.save(actual);
    }
    public List<Producto> buscarPorNombre(String nombre) {
        return repo.findByNombreContainingIgnoreCase(nombre);
    }


    public void eliminar(Integer id) { repo.deleteById(id); }

    public Producto actualizarStock(Integer id, Integer nuevoStock) {
        Producto p = obtener(id);
        p.setStockActual(nuevoStock);
        return repo.save(p);
    }

    public Integer stockDisponible(Integer id) {
        Producto p = obtener(id);
        return (p != null) ? p.getStockActual() : null;
    }
}
