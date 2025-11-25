package tecnm.itch.progweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
	
	private Integer idCliente;
	private String nombrecliente;
	private String telefonocliente;
	private String usuario;
	private String password;
	private String rol;
}
