package tecnm.itch.progweb.services;

import java.util.List;

import tecnm.itch.progweb.dto.ClienteDTO;

public interface ClienteServices {

	ClienteDTO createCliente(ClienteDTO clienteDto);

	ClienteDTO getClienteById(Integer idCliente);

	List<ClienteDTO> getAllCliente();

	ClienteDTO updateCliente(Integer idCliente, ClienteDTO updateCliente);

	void deleteCliente(Integer idCliente);
	
	ClienteDTO getClienteByUsuario(String usuario);
}
