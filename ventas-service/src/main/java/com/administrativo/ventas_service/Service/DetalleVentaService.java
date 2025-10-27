package com.administrativo.ventas_service.Service;

import com.administrativo.ventas_service.Client.ProductoClient;
import com.administrativo.ventas_service.Dto.DetalleVentaDTO;
import com.administrativo.ventas_service.Dto.DetalleVentaRequestDTO;
import com.administrativo.ventas_service.Dto.ProductoDTO;
import com.administrativo.ventas_service.Entity.DetalleVenta;
import com.administrativo.ventas_service.Entity.Venta;
import com.administrativo.ventas_service.Repository.DetalleVentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class DetalleVentaService {

    @Autowired
    private  DetalleVentaRepository detalleVentaRepository;
    @Autowired
    private  ProductoClient productoClient;

    @Transactional
    public DetalleVenta crearDetalleVenta(Venta venta, DetalleVentaRequestDTO dto) {
        // Obtener datos del producto desde el microservicio de productos
        ProductoDTO producto = productoClient.obtenerProductoPorId(dto.getIdProducto());

        // Calcular subtotal
        BigDecimal subtotal = dto.getPrecioUnitario().multiply(BigDecimal.valueOf(dto.getCantidad()));

        // Crear detalle
        DetalleVenta detalle = new DetalleVenta();
        detalle.setVenta(venta);
        detalle.setIdProducto(producto.getIdProducto());
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setSubtotal(subtotal);

        return detalleVentaRepository.save(detalle);
    }

    public List<DetalleVentaDTO> listarDetallesPorVenta(Integer idVenta) {
        System.out.println("🔍 Buscando detalles para venta ID: " + idVenta);

        List<DetalleVenta> detalles = detalleVentaRepository.findByVentaIdVenta(idVenta);
        System.out.println("📦 Detalles encontrados: " + detalles.size());

        return detalles.stream().map(detalle -> {
            // ⚠️ TEMPORAL: Comentar la llamada al microservicio
            // ProductoDTO producto = productoClient.obtenerProductoPorId(detalle.getIdProducto());

            DetalleVentaDTO dto = new DetalleVentaDTO();
            dto.setIdDetalleVenta(detalle.getId_detalle_venta());
            dto.setIdVenta(detalle.getVenta().getIdVenta());
            dto.setIdProducto(detalle.getIdProducto());
            dto.setNombreProducto("Producto " + detalle.getIdProducto()); // Temporal
            dto.setCantidad(detalle.getCantidad());
            dto.setPrecioUnitario(detalle.getPrecioUnitario());
            dto.setSubtotal(detalle.getSubtotal());

            return dto;
        }).toList();
    }

}
