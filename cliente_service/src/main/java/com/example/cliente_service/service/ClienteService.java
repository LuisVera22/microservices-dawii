package com.example.cliente_service.service;

import com.example.cliente_service.dto.ClienteRequest;
import com.example.cliente_service.dto.ClienteResponse;
import com.example.cliente_service.entity.Cliente;
import com.example.cliente_service.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Map<String, Object> crearCliente(ClienteRequest request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Cliente cliente = Cliente.builder()
                    .nombres(request.getNombres())
                    .apellidos(request.getApellidos())
                    .dni(request.getDni())
                    .ruc(request.getRuc())
                    .tipoDocumento(request.getTipoDocumento())
                    .direccion(request.getDireccion())
                    .telefono(request.getTelefono())
                    .email(request.getEmail())
                    .activo(true)
                    .build();

            clienteRepository.save(cliente);

            response.put("message", "Cliente creado exitosamente.");
            response.put("cliente", convertirAResponse(cliente));
        } catch (Exception e) {
            response.put("message", "Ocurrió un error al crear el cliente.");
            e.printStackTrace();
        }

        return response;
    }

    public Map<String, Object> buscarPorDni(String dni) {
        Map<String, Object> response = new HashMap<>();

        Optional<Cliente> cliente = clienteRepository.findByDniAndActivoTrue(dni);

        if (cliente.isEmpty()) {
            response.put("message", "Cliente no encontrado con DNI: " + dni);
            return response;
        }

        response.put("message", "Cliente encontrado.");
        response.put("cliente", convertirAResponse(cliente.get()));

        return response;
    }

    public Map<String, Object> buscarPorRuc(String ruc) {
        Map<String, Object> response = new HashMap<>();

        Optional<Cliente> cliente = clienteRepository.findByRucAndActivoTrue(ruc);

        if (cliente.isEmpty()) {
            response.put("message", "Cliente no encontrado con RUC: " + ruc);
            return response;
        }

        response.put("message", "Cliente encontrado.");
        response.put("cliente", convertirAResponse(cliente.get()));

        return response;
    }

    public Map<String, Object> buscarPorId(Long id) {
        Map<String, Object> response = new HashMap<>();

        Optional<Cliente> cliente = clienteRepository.findById(id);

        if (cliente.isEmpty()) {
            response.put("message", "Cliente no encontrado con ID: " + id);
            return response;
        }

        response.put("message", "Cliente encontrado.");
        response.put("cliente", convertirAResponse(cliente.get()));

        return response;
    }

    public Map<String, Object> listarTodos() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<ClienteResponse> clientes = clienteRepository.findByActivoTrue()
                    .stream()
                    .map(this::convertirAResponse)
                    .collect(Collectors.toList());

            response.put("message", "Listado de clientes.");
            response.put("clientes", clientes);
        } catch (Exception e) {
            response.put("message", "Ocurrió un error al listar los clientes.");
            e.printStackTrace();
        }

        return response;
    }

    public Map<String, Object> listarPorTipoDocumento(Cliente.TipoDocumento tipoDocumento) {
        Map<String, Object> response = new HashMap<>();

        try {
            List<ClienteResponse> clientes = clienteRepository.findByTipoDocumento(tipoDocumento)
                    .stream()
                    .map(this::convertirAResponse)
                    .collect(Collectors.toList());

            response.put("message", "Listado de clientes con tipo de documento: " + tipoDocumento);
            response.put("clientes", clientes);
        } catch (Exception e) {
            response.put("message", "Ocurrió un error al listar los clientes.");
            e.printStackTrace();
        }

        return response;
    }

    public Map<String, Object> actualizarCliente(Long id, ClienteRequest request) {
        Map<String, Object> response = new HashMap<>();

        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isEmpty()) {
            response.put("message", "Cliente no encontrado con ID: " + id);
            return response;
        }

        try {
            Cliente cliente = clienteOptional.get();
            cliente.setNombres(request.getNombres());
            cliente.setApellidos(request.getApellidos());
            cliente.setDireccion(request.getDireccion());
            cliente.setTelefono(request.getTelefono());
            cliente.setEmail(request.getEmail());

            clienteRepository.save(cliente);

            response.put("message", "Cliente actualizado exitosamente.");
            response.put("cliente", convertirAResponse(cliente));
        } catch (Exception e) {
            response.put("message", "Ocurrió un error al actualizar el cliente.");
            e.printStackTrace();
        }

        return response;
    }

    @Transactional
    public Map<String, Object> eliminarCliente(Long id) {
        Map<String, Object> response = new HashMap<>();

        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isEmpty()) {
            response.put("message", "Cliente no encontrado con ID: " + id);
            return response;
        }

        try {
            Cliente cliente = clienteOptional.get();
            cliente.setActivo(false);
            clienteRepository.save(cliente);

            response.put("message", "Cliente eliminado exitosamente.");
        } catch (Exception e) {
            response.put("message", "Ocurrió un error al eliminar el cliente.");
            e.printStackTrace();
        }

        return response;
    }

    private ClienteResponse convertirAResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nombres(cliente.getNombres())
                .apellidos(cliente.getApellidos())
                .dni(cliente.getDni())
                .ruc(cliente.getRuc())
                .tipoDocumento(cliente.getTipoDocumento())
                .direccion(cliente.getDireccion())
                .telefono(cliente.getTelefono())
                .email(cliente.getEmail())
                .activo(cliente.getActivo())
                .fechaRegistro(cliente.getFechaRegistro())
                .build();
    }
}