package com.service.productos.service;

import com.service.productos.entity.Categoria;
import com.service.productos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAll(){
        return categoriaRepository.findAll();
    }

    public Categoria getById(int id){
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria save(Categoria categoria){
        Categoria newCategoria = categoriaRepository.save(categoria);
        return newCategoria;
    }
}
