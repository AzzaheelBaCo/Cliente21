package tecnm.itch.progweb.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private Integer idPedido;

    private Integer idCliente;  // ✅ SOLO ID

    private List<Integer> productosIds;
    private List<ProductoDTO> productos;

    private List<Integer> reservasIds;
    private List<ReservaDTO> reservas;

    private String ticketPath;
}
