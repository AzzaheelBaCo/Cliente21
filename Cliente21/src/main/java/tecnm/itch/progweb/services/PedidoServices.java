package tecnm.itch.progweb.services;

import java.util.*;

import tecnm.itch.progweb.dto.PedidoDTO;

//PedidoServices.java
public interface PedidoServices {

	PedidoDTO registrarPedido(PedidoDTO pedidoDTO);
	
	PedidoDTO getPedidoById(Integer idPedido);
	
	List<PedidoDTO> getAllPedido();

	PedidoDTO actualizarPedido(Integer idPedido, PedidoDTO pedidoDTO);
}
