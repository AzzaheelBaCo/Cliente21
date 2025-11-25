package tecnm.itch.progweb.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {

    private Integer idReserva;
    private Date fecha;
    private String hora;

    private Integer idMesa;     // <-- ya lo tenías
    private Integer idCliente;  // <-- agrega esto
}
