package com.administrativo.cliente_service.controller;

import com.administrativo.cliente_service.entity.Cliente;
import com.administrativo.cliente_service.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @GetMapping("/all")
    public ResponseEntity<List<Cliente>> getAllClientes(){
        List<Cliente> clientes = clienteService.getAll();
        if(clientes.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable int id){
        Cliente cliente = clienteService.getById(id);
        if(cliente == null){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(cliente);
    }

    @PostMapping("/save")
    public ResponseEntity<Cliente> saveCliente(@RequestBody Cliente cliente){
        try{
            Cliente newCliente = clienteService.save(cliente);
            return ResponseEntity.ok(newCliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
}
