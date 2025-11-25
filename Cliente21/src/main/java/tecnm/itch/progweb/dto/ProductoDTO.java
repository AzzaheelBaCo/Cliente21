package tecnm.itch.progweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
	
	private Integer idProducto;
	private String nombre;
	private String descripcionP;
	private Double precio;
	private Integer idTipo;

}
