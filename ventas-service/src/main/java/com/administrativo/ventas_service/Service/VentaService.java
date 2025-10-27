package com.administrativo.ventas_service.Service;

import com.administrativo.ventas_service.Client.ClienteClient;
import com.administrativo.ventas_service.Client.ProductoClient;
import com.administrativo.ventas_service.Client.UsuarioClient;
import com.administrativo.ventas_service.Dto.*;
import com.administrativo.ventas_service.Entity.DetalleVenta;
import com.administrativo.ventas_service.Entity.Venta;
import com.administrativo.ventas_service.Repository.VentaRepository;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.jpa.internal.util.ConfigurationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class VentaService {

        @Autowired
        private ProductoClient productoClient;
        @Autowired
        private VentaRepository ventaRepository;

        @Autowired
        private DetalleVentaService detalleVentaService;

        @Autowired
        private ClienteClient clienteClient;

        @Autowired
        private UsuarioClient usuarioClient;

        @Transactional
        public VentaResponse crearVenta(VentaRequestDTO request) {

            if (request.getDetalles() == null || request.getDetalles().isEmpty()) {
                throw new IllegalArgumentException("La venta debe tener al menos un producto");
            }

            ClienteDTO cliente = clienteClient.obtenerClientePorId(request.getIdCliente());
            UsuarioDTO usuario = usuarioClient.obtenerUsuarioPorId(request.getIdUsuario());

            Venta venta = new Venta();
            venta.setIdCliente(request.getIdCliente());
            venta.setIdUsuario(request.getIdUsuario());
            venta.setFechaVenta(LocalDateTime.now());
            venta.setMetodoPago(Venta.MetodoPago.valueOf(request.getMetodoPago()));
            venta.setTotalVenta(BigDecimal.ZERO);

            Venta ventaGuardada = ventaRepository.save(venta);

            BigDecimal totalVenta = BigDecimal.ZERO;

            for (DetalleVentaRequestDTO detalleDTO : request.getDetalles()) {
                DetalleVenta detalle = detalleVentaService.crearDetalleVenta(ventaGuardada, detalleDTO);
                totalVenta = totalVenta.add(detalle.getSubtotal());
            }

            ventaGuardada.setTotalVenta(totalVenta);
            ventaRepository.save(ventaGuardada);
            return new VentaResponse(
                    ventaGuardada.getIdVenta(),
                    ventaGuardada.getFechaVenta(),
                    ventaGuardada.getTotalVenta(),
                    "Venta registrada con éxito"
            );
        }

        public Venta obtenerVenta(Integer id) {
            return ventaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        }

        public List<Venta> listarVentas() {
            return ventaRepository.findAll();
        }

        public byte[] generarComprobante(Venta venta) {
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                PdfWriter writer = new PdfWriter(out);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                var fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
                var fontNormal = PdfFontFactory.createFont(StandardFonts.HELVETICA);


                ClienteDTO cliente = clienteClient.obtenerClientePorId(venta.getIdCliente());


                Paragraph titulo = new Paragraph(       "COMPROBANTE DE VENTA")
                        .setFont(fontBold)
                        .setFontSize(18)
                        .setTextAlignment(TextAlignment.CENTER);
                document.add(titulo);

                document.add(new Paragraph("\n"));

                document.add(new Paragraph("N° Venta: " + venta.getIdVenta()).setFont(fontNormal));
                document.add(new Paragraph("Fecha: " +
                        venta.getFechaVenta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
                document.add(new Paragraph("Cliente: " + cliente.getNombre() + " " + cliente.getApellido()));
                document.add(new Paragraph("Método de Pago: " + venta.getMetodoPago()));
                document.add(new Paragraph("Estado: " + venta.getEstado()));

                document.add(new Paragraph("\nDetalles de Venta:\n"));


                Table table = new Table(4);
                table.addHeaderCell("Producto");
                table.addHeaderCell("Cantidad");
                table.addHeaderCell("Precio Unitario");
                table.addHeaderCell("Subtotal");

                BigDecimal total = BigDecimal.ZERO;

                for (DetalleVenta detalle : venta.getDetalles()) {
                    ProductoDTO producto = productoClient.obtenerProductoPorId(detalle.getIdProducto());

                    BigDecimal subtotal = detalle.getPrecioUnitario().multiply(new BigDecimal(detalle.getCantidad()));
                    total = total.add(subtotal);

                    table.addCell(producto.getNombre());
                    table.addCell(detalle.getCantidad().toString());
                    table.addCell("S/ " + detalle.getPrecioUnitario());
                    table.addCell("S/ " + subtotal);
                }

                document.add(table);
                document.add(new Paragraph("\n"));


                document.add(new Paragraph("TOTAL: S/ " + total)
                        .setFont(fontBold)
                        .setFontSize(14)
                        .setTextAlignment(TextAlignment.RIGHT));

                document.close();

                return out.toByteArray();
            } catch (Exception e) {
                throw new RuntimeException("Error al generar comprobante PDF", e);
            }
        }


}
