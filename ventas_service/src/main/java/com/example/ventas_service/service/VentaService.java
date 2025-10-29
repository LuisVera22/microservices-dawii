package com.example.ventas_service.service;

import com.example.ventas_service.client.ClienteClientService;
import com.example.ventas_service.client.ProductoClientService;
import com.example.ventas_service.client.KardexClientService;
import com.example.ventas_service.dto.ClienteResponse;
import com.example.ventas_service.dto.ProductoResponse;
import com.example.ventas_service.dto.DetalleVentaResponse;
import com.example.ventas_service.dto.VentaRequest;
import com.example.ventas_service.dto.VentaResponse;
import com.example.ventas_service.entity.DetalleVenta;
import com.example.ventas_service.entity.Venta;
import com.example.ventas_service.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteClientService clienteClientService;
    private final ProductoClientService productoClientService;
    private final KardexClientService kardexClientService;
    private final PdfService pdfService;

    // 🔹 Crear venta completa (cliente + producto + kardex)
    public Map<String, Object> crearVenta(VentaRequest request) {
        Map<String, Object> response = new HashMap<>();

        try {
            // === 1️⃣ Buscar cliente por DNI o RUC ===
            ClienteResponse cliente;
            Venta.TipoComprobante tipoComprobante;

            if (request.getDocumento().length() == 8) {
                cliente = clienteClientService.buscarPorDni(request.getDocumento());
                tipoComprobante = Venta.TipoComprobante.BOLETA;
            } else {
                cliente = clienteClientService.buscarPorRuc(request.getDocumento());
                tipoComprobante = Venta.TipoComprobante.FACTURA;
            }

            if (cliente == null) {
                response.put("message", "Cliente no encontrado con documento: " + request.getDocumento());
                return response;
            }

            // === 2️⃣ Calcular subtotal, IGV y total ===
            double subtotal = 0.0;
            List<DetalleVenta> detalles = new ArrayList<>();

            for (var item : request.getDetalles()) {
                ProductoResponse producto = productoClientService.obtenerPorId(item.getProductoId());
                double precioUnitario = producto.getPrecio();
                double subTotalItem = item.getCantidad() * precioUnitario;
                subtotal += subTotalItem;

                DetalleVenta detalle = DetalleVenta.builder()
                        .producto(producto.getNombre())
                        .cantidad(item.getCantidad())
                        .precioUnitario(precioUnitario)
                        .subtotal(subTotalItem)
                        .build();

                detalles.add(detalle);
            }

            double igv = subtotal * 0.18;
            double total = subtotal + igv;

            // === 3️⃣ Crear la venta ===
            Venta venta = Venta.builder()
                    .clienteId(cliente.getId())
                    .clienteNombres(cliente.getNombreCompleto())
                    .clienteDocumento(request.getDocumento())
                    .tipoComprobante(tipoComprobante)
                    .numeroComprobante(generarNumeroComprobante(tipoComprobante))
                    .subtotal(subtotal)
                    .igv(igv)
                    .total(total)
                    .build();

            // Asignar detalles y guardar
            detalles.forEach(d -> d.setVenta(venta));
            venta.setDetalles(detalles);
            ventaRepository.save(venta);

            // === 4️⃣ Actualizar stock y registrar salida en Kardex ===
            for (var item : request.getDetalles()) {
                ProductoResponse producto = productoClientService.obtenerPorId(item.getProductoId());
                Integer stockAnterior = producto.getStockActual();
                Integer stockNuevo = stockAnterior - item.getCantidad();

                // Actualiza el stock en producto-service
                productoClientService.actualizarStock(item.getProductoId(), stockNuevo);

                // Registra salida en kardex-service (✅ sin idUsuario)
                kardexClientService.registrarSalida(
                        item.getProductoId(),
                        item.getCantidad(),
                        stockAnterior,
                        stockNuevo,
                        venta.getId().intValue() // referencia a la venta
                );
            }

            response.put("message", tipoComprobante + " generada exitosamente.");
            response.put("venta", convertirAResponse(venta));

        } catch (Exception e) {
            response.put("message", "Ocurrió un error al crear la venta: " + e.getMessage());
            e.printStackTrace();
        }

        return response;
    }

    // 🔹 Listar todas las ventas
    public Map<String, Object> listarTodasLasVentas() {
        Map<String, Object> response = new HashMap<>();
        List<VentaResponse> ventas = ventaRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
        response.put("message", "Listado de todas las ventas.");
        response.put("ventas", ventas);
        return response;
    }

    // 🔹 Listar solo boletas
    public Map<String, Object> listarBoletas() {
        Map<String, Object> response = new HashMap<>();
        List<VentaResponse> boletas = ventaRepository.findByTipoComprobante(Venta.TipoComprobante.BOLETA)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
        response.put("message", "Listado de boletas.");
        response.put("boletas", boletas);
        return response;
    }

    // 🔹 Listar solo facturas
    public Map<String, Object> listarFacturas() {
        Map<String, Object> response = new HashMap<>();
        List<VentaResponse> facturas = ventaRepository.findByTipoComprobante(Venta.TipoComprobante.FACTURA)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
        response.put("message", "Listado de facturas.");
        response.put("facturas", facturas);
        return response;
    }

    // 🔹 Buscar venta por ID
    public Map<String, Object> buscarPorId(Long id) {
        Map<String, Object> response = new HashMap<>();
        var venta = ventaRepository.findById(id);
        if (venta.isEmpty()) {
            response.put("message", "Venta no encontrada con ID: " + id);
            return response;
        }
        response.put("venta", convertirAResponse(venta.get()));
        return response;
    }

    // 🔹 Generar PDF de comprobante
    public byte[] generarPdf(Long ventaId) {
        var ventaOptional = ventaRepository.findById(ventaId);
        if (ventaOptional.isEmpty()) return null;
        return pdfService.generarComprobantePdf(ventaOptional.get());
    }

    // 🔹 Métodos auxiliares
    private String generarNumeroComprobante(Venta.TipoComprobante tipo) {
        String prefijo = tipo == Venta.TipoComprobante.BOLETA ? "B001-" : "F001-";
        Long cantidad = ventaRepository.countByTipoComprobante(tipo);
        return prefijo + String.format("%08d", cantidad + 1);
    }

    private VentaResponse convertirAResponse(Venta venta) {
        List<DetalleVentaResponse> detallesResponse = venta.getDetalles().stream()
                .map(d -> DetalleVentaResponse.builder()
                        .id(d.getId())
                        .producto(d.getProducto())
                        .cantidad(d.getCantidad())
                        .precioUnitario(d.getPrecioUnitario())
                        .subtotal(d.getSubtotal())
                        .build())
                .collect(Collectors.toList());

        return VentaResponse.builder()
                .id(venta.getId())
                .clienteId(venta.getClienteId())
                .clienteNombres(venta.getClienteNombres())
                .clienteDocumento(venta.getClienteDocumento())
                .tipoComprobante(venta.getTipoComprobante())
                .numeroComprobante(venta.getNumeroComprobante())
                .detalles(detallesResponse)
                .subtotal(venta.getSubtotal())
                .igv(venta.getIgv())
                .total(venta.getTotal())
                .fechaVenta(venta.getFechaVenta())
                .build();
    }
}
