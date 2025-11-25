package tecnm.itch.progweb.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import tecnm.itch.progweb.Exception.ResourseNotFoundException;
import tecnm.itch.progweb.dto.PedidoDTO;
import tecnm.itch.progweb.dto.ProductoDTO;
import tecnm.itch.progweb.dto.ReservaDTO;
import tecnm.itch.progweb.entity.Cliente;
import tecnm.itch.progweb.entity.Pedido;
import tecnm.itch.progweb.feign.ProductoFeign;
import tecnm.itch.progweb.feign.ReservaFeign;
import tecnm.itch.progweb.mapper.PedidoMapper;
import tecnm.itch.progweb.repository.ClienteRepository;
import tecnm.itch.progweb.repository.PedidoRepository;
import tecnm.itch.progweb.services.PedidoServices;
import tecnm.itch.progweb.services.TicketService;

@Service
@AllArgsConstructor
public class PedidoServicesImpl implements PedidoServices {

    private final PedidoRepository pedidoRepo;
    private final ProductoFeign productoFei;
    private final ReservaFeign reservaFeign;
    private final ClienteRepository clienteRepo;
    private final TicketService ticketService;

    @Override
    public PedidoDTO registrarPedido(PedidoDTO pedidoDTO) {

        // ✅ Validar cliente
        if (pedidoDTO.getIdCliente() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente es requerido");
        }

        Cliente cliente = clienteRepo.findById(pedidoDTO.getIdCliente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "El cliente no existe"));

        // ✅ Validar productos
        if (pedidoDTO.getProductosIds() != null && !pedidoDTO.getProductosIds().isEmpty()) {
            List<ProductoDTO> productos = productoFei.getProductosByIds(pedidoDTO.getProductosIds());
            if (productos == null || productos.size() != pedidoDTO.getProductosIds().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Algún producto no existe");
            }
        }

        // ✅ Validar reservas
        if (pedidoDTO.getReservasIds() != null && !pedidoDTO.getReservasIds().isEmpty()) {
            for (Integer idRes : pedidoDTO.getReservasIds()) {
                try {
                    ReservaDTO r = reservaFeign.obtenerRPorId(idRes);
                    if (r == null) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva " + idRes + " no existe");
                    }
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva " + idRes + " no existe");
                }
            }
        }

        // ✅ Mapear y guardar
        Pedido pedido = PedidoMapper.maptoPedido(pedidoDTO);
        pedido.setCliente(cliente);
        Pedido saved = pedidoRepo.save(pedido);

        // ✅ Generar ticket
        String path = ticketService.generarYGuardarTicket(saved);
        saved.setTicketPath(path);
        saved = pedidoRepo.save(saved);

        // ✅ Crear respuesta enriquecida
        PedidoDTO dto = PedidoMapper.maptoPedidoDTO(saved);

        if (saved.getProductosIds() != null)
            dto.setProductos(productoFei.getProductosByIds(saved.getProductosIds()));

        if (saved.getReservasIds() != null)
            dto.setReservas(saved.getReservasIds().stream()
                    .map(id -> {
                        try { return reservaFeign.obtenerRPorId(id); }
                        catch (Exception e) { return null; }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));

        return dto;
    }

    @Override
    public PedidoDTO getPedidoById(Integer idPedido) {

        Pedido pedido = pedidoRepo.findById(idPedido)
                .orElseThrow(() -> new ResourseNotFoundException("Pedido no existe ID: " + idPedido));

        PedidoDTO dto = PedidoMapper.maptoPedidoDTO(pedido);

        if (pedido.getProductosIds() != null)
            dto.setProductos(productoFei.getProductosByIds(pedido.getProductosIds()));

        if (pedido.getReservasIds() != null)
            dto.setReservas(pedido.getReservasIds().stream()
                    .map(id -> {
                        try { return reservaFeign.obtenerRPorId(id); }
                        catch (Exception e) { return null; }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));

        return dto;
    }

    @Override
    public List<PedidoDTO> getAllPedido() {
        return pedidoRepo.findAll().stream().map(p -> getPedidoById(p.getIdPedido())).collect(Collectors.toList());
    }

    @Override
    public PedidoDTO actualizarPedido(Integer idPedido, PedidoDTO pedidoDTO) {

        Pedido pedidoExist = pedidoRepo.findById(idPedido)
                .orElseThrow(() -> new ResourseNotFoundException("Pedido no existe ID: " + idPedido));

        // ✅ Actualizar cliente
        if (pedidoDTO.getIdCliente() != null) {
            Cliente cliente = clienteRepo.findById(pedidoDTO.getIdCliente())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no existe"));
            pedidoExist.setCliente(cliente);
        }

        // ✅ Actualizar productos
        if (pedidoDTO.getProductosIds() != null) {
            if (!pedidoDTO.getProductosIds().isEmpty()) {
                List<ProductoDTO> productos = productoFei.getProductosByIds(pedidoDTO.getProductosIds());
                if (productos == null || productos.size() != pedidoDTO.getProductosIds().size()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Algún producto no existe");
                }
            }
            pedidoExist.setProductosIds(pedidoDTO.getProductosIds());
        }

        // ✅ Actualizar reservas
        if (pedidoDTO.getReservasIds() != null) {
            List<Integer> validadas = new ArrayList<>();
            for (Integer idRes : pedidoDTO.getReservasIds()) {
                try {
                    ReservaDTO r = reservaFeign.obtenerRPorId(idRes);
                    if (r != null) validadas.add(idRes);
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva " + idRes + " no existe");
                }
            }
            pedidoExist.setReservasIds(validadas);
        }

        Pedido saved = pedidoRepo.save(pedidoExist);

        // ✅ Regenerar ticket
        String path = ticketService.generarYGuardarTicket(saved);
        saved.setTicketPath(path);
        saved = pedidoRepo.save(saved);

        // ✅ Retornar DTO completo
        return getPedidoById(saved.getIdPedido());
    }
}
