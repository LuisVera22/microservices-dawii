package com.administrativo.kardex_service.controller;

import com.administrativo.kardex_service.entity.Kardex;
import com.administrativo.kardex_service.service.KardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/kardex")
public class KardexController {

    @Autowired
    KardexService kardexService;

    @PostMapping()
    public ResponseEntity<Kardex> save(@RequestBody Kardex car) {
        Kardex kardexNew = kardexService.save(car);
        return ResponseEntity.ok(kardexNew);
    }

    @GetMapping
    public ResponseEntity<List<Kardex>> getAll() {
        List<Kardex> lista = kardexService.getAll();
        if(lista.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/byproducto/{idProducto}")
    public ResponseEntity<List<Kardex>> getByProductoId(@PathVariable("idProducto") int idProducto) {
        List<Kardex> lista = kardexService.getByIdProducto(idProducto);
        if(lista.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/byusuario/{idUsuario}")
    public ResponseEntity<List<Kardex>> getByUsuarioId(@PathVariable("idUsuario") int idUsuario) {
        List<Kardex> lista = kardexService.getByIdUsuario(idUsuario);
        if(lista.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/byfecha/{fecha}")
    public ResponseEntity<List<Kardex>> getByDate(@PathVariable("fecha") LocalDate fecha) {
        List<Kardex> lista = kardexService.getByFecha(fecha);
        if(lista.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/bymovimiento/{tipoMovimiento}")
    public ResponseEntity<List<Kardex>> getByMovimiento(@PathVariable("tipoMovimiento") String tipoMovimiento) {
        List<Kardex> lista = kardexService.getByTipoMovimiento(tipoMovimiento);
        if(lista.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }
}
