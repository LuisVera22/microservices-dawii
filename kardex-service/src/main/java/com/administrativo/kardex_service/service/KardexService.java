package com.administrativo.kardex_service.service;

import com.administrativo.kardex_service.entity.Kardex;
import com.administrativo.kardex_service.repository.KardexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class KardexService {

    @Autowired
    KardexRepository kardexRepo;

    public Kardex save(Kardex car) {
        return kardexRepo.save(car);
    }

    public List<Kardex> getAll() {
        return kardexRepo.findAll();
    }

    public List<Kardex> getByIdProducto(int idProducto) {
        return kardexRepo.findByIdProducto(idProducto);
    }



    public List<Kardex> getByFecha(LocalDate fecha) {
        // 🔹 Convertimos LocalDate a Timestamp
        LocalDateTime inicioDelDia = fecha.atStartOfDay();
        LocalDateTime finDelDia = fecha.atTime(LocalTime.MAX);

        Timestamp inicioTimestamp = Timestamp.valueOf(inicioDelDia);
        Timestamp finTimestamp = Timestamp.valueOf(finDelDia);

        return kardexRepo.findByFechaBetween(inicioTimestamp, finTimestamp);
    }

    public List<Kardex> getByTipoMovimiento(String tipoMovimiento) {
        return kardexRepo.findByTipoMovimiento(tipoMovimiento);
    }
}
