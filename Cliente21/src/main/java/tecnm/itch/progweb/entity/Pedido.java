package tecnm.itch.progweb.entity;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Pedido.java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPedido;

	@ManyToOne
	@JoinColumn(name = "idCliente", referencedColumnName = "idcliente")
	private Cliente cliente;

	@ElementCollection
	@CollectionTable(name = "pedido_productos", joinColumns = @JoinColumn(name = "idPedido"))
	@Column(name = "idProducto")
	private List<Integer> productosIds;

	@ElementCollection
	@CollectionTable(name = "pedido_reservas", joinColumns = @JoinColumn(name = "idPedido"))
	@Column(name = "idReserva")
	private List<Integer> reservasIds;

	// Nueva columna para almacenar la ruta del ticket
	@Column(name = "ticket_path")
	private String ticketPath;
}

