package com.example.cliente_service.dto;
import com.example.cliente_service.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponse {
    private Long id;
    private String nombres;
    private String apellidos;
    private String dni;
    private String ruc;
    private Cliente.TipoDocumento tipoDocumento;
    private String direccion;
    private String telefono;
    private String email;
    private Boolean activo;
    private LocalDateTime fechaRegistro;

    public String getNumeroDocumento() {
        return tipoDocumento == Cliente.TipoDocumento.DNI ? dni : ruc;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}