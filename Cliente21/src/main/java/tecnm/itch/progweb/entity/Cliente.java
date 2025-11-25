package tecnm.itch.progweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="cliente")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCliente;
	
	@Column(name = "nombrecliente")
	private String nombrecliente;
	
	@Column(name = "telefonocliente")
	private String telefonocliente;
	
	@Column(name = "usuario")
	private String usuario;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "rol")
	private String rol = "CLIENTE";
}
