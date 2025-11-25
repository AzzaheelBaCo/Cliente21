package tecnm.itch.progweb.services;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

//TicketService.java
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import lombok.AllArgsConstructor;
import tecnm.itch.progweb.dto.ProductoDTO;
import tecnm.itch.progweb.entity.Pedido;
import tecnm.itch.progweb.feign.ProductoFeign;

@Service
@AllArgsConstructor
public class TicketService {

 private final ProductoFeign productoFei; // Usamos el feign para obtener detalles

 private final String baseFolder = "C:/uploads/tickets/";

 public String generarYGuardarTicket(Pedido pedido) {
     try {
         // Asegurar carpeta
         File dir = new File(baseFolder);
         if (!dir.exists()) dir.mkdirs();

         // Nombre de archivo con timestamp y id
         String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
         String filename = "ticket_pedido_" + pedido.getIdPedido() + "_" + timestamp + ".pdf";
         String fullPath = baseFolder + filename;

         // Crear documento
         Document document = new Document(PageSize.A6.rotate(), 20, 20, 20, 20); // tamaño compacto estilo ticket
         PdfWriter.getInstance(document, new FileOutputStream(fullPath));
         document.open();

         // Fuentes
         Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
         Font bold = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
         Font normal = new Font(Font.FontFamily.HELVETICA, 9);

         // Encabezado
         Paragraph header = new Paragraph("RESTAURANTE NOMBRE", titleFont);
         header.setAlignment(Element.ALIGN_CENTER);
         document.add(header);

         Paragraph sub = new Paragraph("TICKET DE PEDIDO", bold);
         sub.setAlignment(Element.ALIGN_CENTER);
         document.add(sub);

         document.add(new Paragraph(" "));
         document.add(new LineSeparator());
         document.add(new Paragraph("Pedido #: " + pedido.getIdPedido(), bold));
         document.add(new Paragraph("Cliente: " + (pedido.getCliente() != null ? pedido.getCliente().getNombrecliente() : "Sin cliente"), normal));
         document.add(new Paragraph("Fecha: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), normal));
         document.add(new Paragraph(" "));
         document.add(new LineSeparator());

         // Tabla de productos
         PdfPTable table = new PdfPTable(new float[]{6, 2});
         table.setWidthPercentage(100);
         PdfPCell cell;

         cell = new PdfPCell(new Phrase("Producto", bold));
         cell.setBorder(Rectangle.NO_BORDER);
         table.addCell(cell);

         cell = new PdfPCell(new Phrase("Precio", bold));
         cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
         cell.setBorder(Rectangle.NO_BORDER);
         table.addCell(cell);

         double total = 0.0;

         // recorrer productosIds y obtener datos via feign
         List<Integer> productoIds = pedido.getProductosIds();
         if (productoIds != null && !productoIds.isEmpty()) {
             for (Integer idProd : productoIds) {
                 try {
                     ProductoDTO prod = productoFei.obtenerPorId(idProd); // asumo este método existe
                     String nombre = prod != null ? prod.getNombre() : ("Producto#" + idProd);
                     Double precio = prod != null ? (prod.getPrecio() != null ? prod.getPrecio() : 0.0) : 0.0;

                     PdfPCell pName = new PdfPCell(new Phrase(nombre, normal));
                     pName.setBorder(Rectangle.NO_BORDER);
                     table.addCell(pName);

                     PdfPCell pPrice = new PdfPCell(new Phrase(String.format("$%.2f", precio), normal));
                     pPrice.setHorizontalAlignment(Element.ALIGN_RIGHT);
                     pPrice.setBorder(Rectangle.NO_BORDER);
                     table.addCell(pPrice);

                     total += precio;
                 } catch (Exception ex) {
                     // Si falla obtener producto, mostrar id
                     PdfPCell pName = new PdfPCell(new Phrase("Producto#" + idProd, normal));
                     pName.setBorder(Rectangle.NO_BORDER);
                     table.addCell(pName);

                     PdfPCell pPrice = new PdfPCell(new Phrase("$0.00", normal));
                     pPrice.setHorizontalAlignment(Element.ALIGN_RIGHT);
                     pPrice.setBorder(Rectangle.NO_BORDER);
                     table.addCell(pPrice);
                 }
             }
         } else {
             PdfPCell pName = new PdfPCell(new Phrase("Sin productos", normal));
             pName.setBorder(Rectangle.NO_BORDER);
             table.addCell(pName);

             PdfPCell pPrice = new PdfPCell(new Phrase("$0.00", normal));
             pPrice.setHorizontalAlignment(Element.ALIGN_RIGHT);
             pPrice.setBorder(Rectangle.NO_BORDER);
             table.addCell(pPrice);
         }

         document.add(table);
         document.add(new LineSeparator());
         Paragraph totalP = new Paragraph("TOTAL: " + String.format("$%.2f", total), bold);
         totalP.setAlignment(Element.ALIGN_RIGHT);
         document.add(totalP);

         document.add(new Paragraph(" "));
         document.add(new Paragraph("¡Gracias por su compra!", normal));
         document.close();

         return fullPath;
     } catch (Exception e) {
         throw new RuntimeException("Error generando ticket: " + e.getMessage(), e);
     }
 }
}
