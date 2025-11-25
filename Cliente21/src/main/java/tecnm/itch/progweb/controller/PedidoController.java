package tecnm.itch.progweb.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tecnm.itch.progweb.Exception.ResourseNotFoundException;
import tecnm.itch.progweb.dto.PedidoDTO;
import tecnm.itch.progweb.entity.Pedido;
import tecnm.itch.progweb.repository.PedidoRepository;
import tecnm.itch.progweb.services.PedidoServices;
import tecnm.itch.progweb.services.TicketService;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/pedido")
public class PedidoController {

    private final PedidoServices pedidoServices;
    private final PedidoRepository pedidoRepo;
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO guardarPedido = pedidoServices.registrarPedido(pedidoDTO);
        return new ResponseEntity<>(guardarPedido, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<PedidoDTO> getPedidoByID(@PathVariable("id") Integer idPedido) {
        return ResponseEntity.ok(pedidoServices.getPedidoById(idPedido));
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getAllPedido() {
        return ResponseEntity.ok(pedidoServices.getAllPedido());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable Integer id, @RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO updated = pedidoServices.actualizarPedido(id, pedidoDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/generar-ticket")
    public ResponseEntity<String> generarTicket(@PathVariable Integer id) {
        Pedido pedido = pedidoRepo.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Pedido no existe ID: " + id));

        String ruta = ticketService.generarYGuardarTicket(pedido);

        pedido.setTicketPath(ruta);
        pedidoRepo.save(pedido);

        return ResponseEntity.ok("Ticket generado correctamente");
    }

    @GetMapping("/{id}/ticket")
    public ResponseEntity<byte[]> descargarTicket(@PathVariable Integer id) {

        Pedido pedido = pedidoRepo.findById(id)
                .orElseThrow(() -> new ResourseNotFoundException("Pedido no existe"));

        String path = pedido.getTicketPath();

        if (path == null)
            throw new ResourseNotFoundException("No existe ticket generado");

        try {
            File file = new File(path);
            byte[] data = Files.readAllBytes(file.toPath());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                    ContentDisposition.builder("attachment").filename(file.getName()).build());

            return new ResponseEntity<>(data, headers, HttpStatus.OK);

        } catch (IOException e) {
            throw new RuntimeException("Error leyendo ticket: " + e.getMessage());
        }
    }
}

