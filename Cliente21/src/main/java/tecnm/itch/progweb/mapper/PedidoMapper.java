package tecnm.itch.progweb.mapper;

import tecnm.itch.progweb.dto.PedidoDTO;
import tecnm.itch.progweb.entity.Pedido;

public class PedidoMapper {

    public static Pedido maptoPedido(PedidoDTO dto) {
        Pedido p = new Pedido();
        p.setIdPedido(dto.getIdPedido());
        p.setProductosIds(dto.getProductosIds());
        p.setReservasIds(dto.getReservasIds());
        p.setTicketPath(dto.getTicketPath());
        return p;
    }

    public static PedidoDTO maptoPedidoDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setIdPedido(pedido.getIdPedido());

        // ✅ Devolvemos el id del cliente, no el objeto completo
        dto.setIdCliente(
            pedido.getCliente() != null ? pedido.getCliente().getIdCliente() : null
        );

        dto.setProductosIds(pedido.getProductosIds());
        dto.setReservasIds(pedido.getReservasIds());
        dto.setTicketPath(pedido.getTicketPath());

        return dto;
    }
}
