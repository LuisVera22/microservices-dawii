package com.example.ventas_service.client;

import com.example.ventas_service.dto.ClienteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClienteClientService {

    private final RestTemplate restTemplate;

    @Value("${cliente.service.url:http://localhost:8004}")
    private String clienteServiceUrl;

    public ClienteResponse buscarPorDni(String dni) {
        try {
            String url = clienteServiceUrl + "/api/clientes/dni/" + dni;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey("cliente")) {
                Map<String, Object> clienteData = (Map<String, Object>) response.get("cliente");
                return mapToClienteResponse(clienteData);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ClienteResponse buscarPorRuc(String ruc) {
        try {
            String url = clienteServiceUrl + "/api/clientes/ruc/" + ruc;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey("cliente")) {
                Map<String, Object> clienteData = (Map<String, Object>) response.get("cliente");
                return mapToClienteResponse(clienteData);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ClienteResponse mapToClienteResponse(Map<String, Object> data) {
        ClienteResponse cliente = new ClienteResponse();
        cliente.setId(Long.valueOf(data.get("id").toString()));
        cliente.setNombres((String) data.get("nombres"));
        cliente.setApellidos((String) data.get("apellidos"));
        cliente.setDni((String) data.get("dni"));
        cliente.setRuc((String) data.get("ruc"));
        cliente.setTipoDocumento((String) data.get("tipoDocumento"));
        cliente.setDireccion((String) data.get("direccion"));
        cliente.setTelefono((String) data.get("telefono"));
        cliente.setEmail((String) data.get("email"));
        return cliente;
    }
}