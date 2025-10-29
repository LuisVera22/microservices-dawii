package com.example.ventas_service.client;

import com.example.ventas_service.dto.ProductoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductoClientService {

    private final RestTemplate restTemplate;

    @Value("${producto.service.url:http://localhost:8084}")
    private String productoServiceUrl;

    // 🔹 Obtener producto por ID
    public ProductoResponse obtenerPorId(Integer id) {
        try {
            String url = productoServiceUrl + "/api/productos/" + id;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey("producto")) {
                Map<String, Object> data = (Map<String, Object>) response.get("producto");
                return mapToProductoResponse(data);
            } else if (response != null && response.containsKey("id")) {
                // Si el microservicio de producto devuelve el producto directamente
                return mapToProductoResponse(response);
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 🔹 Actualizar stock de producto
    public void actualizarStock(Integer id, Integer nuevoStock) {
        try {
            String url = productoServiceUrl + "/api/productos/" + id + "/stock?nuevoStock=" + nuevoStock;
            restTemplate.put(url, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔹 Convertir Map a ProductoResponse
    private ProductoResponse mapToProductoResponse(Map<String, Object> data) {
        ProductoResponse producto = new ProductoResponse();
        producto.setId(Integer.valueOf(data.get("id").toString()));
        producto.setNombre((String) data.get("nombre"));
        producto.setPrecio(Double.valueOf(data.get("precio").toString()));
        producto.setStockActual(Integer.valueOf(data.get("stockActual").toString()));
        return producto;
    }
}
