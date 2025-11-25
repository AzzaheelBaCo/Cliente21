package tecnm.itch.progweb.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.ResourceClosedException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import tecnm.itch.progweb.Exception.ResourseNotFoundException;
import tecnm.itch.progweb.controller.ClienteController;
import tecnm.itch.progweb.dto.ClienteDTO;
import tecnm.itch.progweb.entity.Cliente;
import tecnm.itch.progweb.mapper.ClienteMapper;
import tecnm.itch.progweb.repository.ClienteRepository;
import tecnm.itch.progweb.services.ClienteServices;

@Service
@AllArgsConstructor
public class ClienteServicesImpl implements ClienteServices {

	private ClienteRepository clienteRepo;
	
	private ClienteMapper clienteMapper;




	@Override
	public ClienteDTO createCliente(ClienteDTO clienteDto) {
		Cliente cliente = clienteMapper.toEntity(clienteDto);
		Cliente savedCliente = clienteRepo.save(cliente);
		return clienteMapper.toDto(savedCliente);
	}

	@Override
	public ClienteDTO getClienteById(Integer idCliente) {
		Cliente cliente = clienteRepo.findById(idCliente)
				.orElseThrow(() -> new ResourseNotFoundException("El alumno no existe con este ID: " + idCliente));
		return clienteMapper.toDto(cliente);
	}

	@Override
	public List<ClienteDTO> getAllCliente() {
		return clienteRepo.findAll().stream().map(clienteMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public ClienteDTO updateCliente(Integer idCliente , ClienteDTO updateCliente) {
		Cliente cliente = clienteRepo.findById(idCliente).orElseThrow(()-> new ResourceClosedException("El Cliente Con ese ID no existe: " + idCliente));
		cliente.setNombrecliente(updateCliente.getNombrecliente());
		cliente.setUsuario(updateCliente.getUsuario());
		cliente.setPassword(updateCliente.getPassword());
		cliente.setRol("CLIENTE");
		cliente.setTelefonocliente(updateCliente.getTelefonocliente());
		cliente = clienteRepo.save(cliente);
		return clienteMapper.toDto(cliente);
	}

	@Override
	public void deleteCliente(Integer idCliente) {
		Cliente cliente = clienteRepo.findById(idCliente).orElseThrow(
				()-> new ResourceClosedException("La materia con ese ID no existe: "+idCliente));
		clienteRepo.delete(cliente);

	}

	@Override
	public ClienteDTO getClienteByUsuario(String usuario) {
	    return clienteRepo.findByUsuario(usuario)
	            .map(clienteMapper::toDto)
	            .orElse(null);
	}




}
