package com.service.productos.service;

import com.service.productos.entity.Producto;
import com.service.productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAll(){
        return productoRepository.findAll();
    }

    public Producto getById(int id){
        return productoRepository.findById(id).orElse(null);
    }

    public Producto updateStock(int id, int stock){
        Producto producto = productoRepository.findById(id).orElse(null);
        if(producto==null){
            return null;
        }
        producto.setStockActual(stock);
        return productoRepository.save(producto);
    }

    public Producto save(Producto product){
        return productoRepository.save(product);
    }

    public List<Producto> getByIdCategoria(int idCategoria){
        return productoRepository.buscarPorIdCategoria(idCategoria);
    }
}
