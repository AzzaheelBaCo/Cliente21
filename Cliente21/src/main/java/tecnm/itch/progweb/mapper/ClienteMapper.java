package tecnm.itch.progweb.mapper;

import org.mapstruct.Mapper;

import tecnm.itch.progweb.dto.ClienteDTO;
import tecnm.itch.progweb.entity.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

	ClienteDTO toDto(Cliente cliente);
	
	Cliente toEntity(ClienteDTO clienteDTO);
}
