package com.example.cliente_service.controller;

import com.example.cliente_service.dto.ClienteRequest;
import com.example.cliente_service.entity.Cliente;
import com.example.cliente_service.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearCliente(@RequestBody ClienteRequest request) {
        return ResponseEntity.ok(clienteService.crearCliente(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerClientePorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<Map<String, Object>> obtenerClientePorDni(@PathVariable String dni) {
        return ResponseEntity.ok(clienteService.buscarPorDni(dni));
    }

    @GetMapping("/ruc/{ruc}")
    public ResponseEntity<Map<String, Object>> obtenerClientePorRuc(@PathVariable String ruc) {
        return ResponseEntity.ok(clienteService.buscarPorRuc(ruc));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/tipo/{tipoDocumento}")
    public ResponseEntity<Map<String, Object>> listarClientesPorTipo(@PathVariable Cliente.TipoDocumento tipoDocumento) {
        return ResponseEntity.ok(clienteService.listarPorTipoDocumento(tipoDocumento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarCliente(
            @PathVariable Long id,
            @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(clienteService.actualizarCliente(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarCliente(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.eliminarCliente(id));
    }
}