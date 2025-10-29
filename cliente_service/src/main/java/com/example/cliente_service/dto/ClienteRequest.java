package com.example.cliente_service.dto;
import com.example.cliente_service.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequest {
    private String nombres;
    private String apellidos;
    private String dni;
    private String ruc;
    private Cliente.TipoDocumento tipoDocumento;
    private String direccion;
    private String telefono;
    private String email;
}

