package tecnm.itch.progweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {

	private Integer idEmpleado;
	private String nombreEmpleado;
	private String usuario;
	private String password;
	private String rol;
}