package com.example.producto_service.service;

import com.example.producto_service.entity.Categoria;
import com.example.producto_service.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repo;

    public List<Categoria> listar() { return repo.findAll(); }

    public Categoria obtener(Integer id) { return repo.findById(id).orElse(null); }

    public Categoria crear(Categoria c) { return repo.save(c); }

    public Categoria actualizar(Integer id, Categoria c) {
        Categoria actual = obtener(id);
        actual.setNombre(c.getNombre());
        actual.setDescripcion(c.getDescripcion());
        return repo.save(actual);
    }

    public void eliminar(Integer id) { repo.deleteById(id); }
}
