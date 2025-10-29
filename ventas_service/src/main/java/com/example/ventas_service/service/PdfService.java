package com.example.ventas_service.service;

import com.example.ventas_service.entity.DetalleVenta;
import com.example.ventas_service.entity.Venta;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

    public byte[] generarComprobantePdf(Venta venta) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);

            document.open();

            // Título
            Paragraph titulo = new Paragraph(venta.getTipoComprobante().toString(), TITLE_FONT);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            // Número de comprobante
            Paragraph numero = new Paragraph("N° " + venta.getNumeroComprobante(), HEADER_FONT);
            numero.setAlignment(Element.ALIGN_CENTER);
            numero.setSpacingAfter(20);
            document.add(numero);

            // Datos del cliente
            document.add(new Paragraph("DATOS DEL CLIENTE", HEADER_FONT));
            document.add(new Paragraph("Nombre: " + venta.getClienteNombres(), NORMAL_FONT));
            document.add(new Paragraph("Documento: " + venta.getClienteDocumento(), NORMAL_FONT));
            document.add(new Paragraph("Fecha: " + venta.getFechaVenta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), NORMAL_FONT));
            document.add(new Paragraph(" "));

            // Detalle de productos
            document.add(new Paragraph("DETALLE DE PRODUCTOS", HEADER_FONT));
            document.add(new Paragraph(" "));

            // Tabla de productos
            PdfPTable tableProductos = new PdfPTable(4);
            tableProductos.setWidthPercentage(100);
            tableProductos.setWidths(new float[]{3, 1, 1.5f, 1.5f});

            // Headers de tabla
            addTableHeader(tableProductos, "Producto");
            addTableHeader(tableProductos, "Cant.");
            addTableHeader(tableProductos, "P. Unit.");
            addTableHeader(tableProductos, "Subtotal");

            // Filas de productos
            for (DetalleVenta detalle : venta.getDetalles()) {
                addTableCell(tableProductos, detalle.getProducto());
                addTableCell(tableProductos, String.valueOf(detalle.getCantidad()));
                addTableCell(tableProductos, "S/ " + String.format("%.2f", detalle.getPrecioUnitario()));
                addTableCell(tableProductos, "S/ " + String.format("%.2f", detalle.getSubtotal()));
            }

            document.add(tableProductos);
            document.add(new Paragraph(" "));

            // Tabla de totales
            PdfPTable tableTotales = new PdfPTable(2);
            tableTotales.setWidthPercentage(50);
            tableTotales.setHorizontalAlignment(Element.ALIGN_RIGHT);

            addCell(tableTotales, "Subtotal:", true);
            addCell(tableTotales, "S/ " + String.format("%.2f", venta.getSubtotal()), false);

            addCell(tableTotales, "IGV (18%):", true);
            addCell(tableTotales, "S/ " + String.format("%.2f", venta.getIgv()), false);

            addCell(tableTotales, "TOTAL:", true);
            addCell(tableTotales, "S/ " + String.format("%.2f", venta.getTotal()), false);

            document.add(tableTotales);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addTableHeader(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, HEADER_FONT));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);
    }

    private void addTableCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, NORMAL_FONT));
        cell.setPadding(5);
        table.addCell(cell);
    }

    private void addCell(PdfPTable table, String text, boolean isHeader) {
        PdfPCell cell = new PdfPCell(new Phrase(text, isHeader ? HEADER_FONT : NORMAL_FONT));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);
    }
}
