package tecnm.itch.progweb.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tecnm.itch.progweb.dto.ClienteDTO;
import tecnm.itch.progweb.entity.Cliente;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-22T15:20:46-0600",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public ClienteDTO toDto(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setIdCliente( cliente.getIdCliente() );
        clienteDTO.setNombrecliente( cliente.getNombrecliente() );
        clienteDTO.setPassword( cliente.getPassword() );
        clienteDTO.setRol( cliente.getRol() );
        clienteDTO.setTelefonocliente( cliente.getTelefonocliente() );
        clienteDTO.setUsuario( cliente.getUsuario() );

        return clienteDTO;
    }

    @Override
    public Cliente toEntity(ClienteDTO clienteDTO) {
        if ( clienteDTO == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setIdCliente( clienteDTO.getIdCliente() );
        cliente.setNombrecliente( clienteDTO.getNombrecliente() );
        cliente.setPassword( clienteDTO.getPassword() );
        cliente.setRol( clienteDTO.getRol() );
        cliente.setTelefonocliente( clienteDTO.getTelefonocliente() );
        cliente.setUsuario( clienteDTO.getUsuario() );

        return cliente;
    }
}
