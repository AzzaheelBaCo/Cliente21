package tecnm.itch.progweb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.AllArgsConstructor;
import tecnm.itch.progweb.dto.ClienteDTO;
import tecnm.itch.progweb.services.ClienteServices;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

	private ClienteServices clienteService;


	// Api REST
	@PostMapping
	public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO clienteDTO) {
		ClienteDTO saveCliente = clienteService.createCliente(clienteDTO);
		return new ResponseEntity<>(saveCliente, HttpStatus.CREATED);
	}

	@GetMapping("{id}")
	public ResponseEntity<ClienteDTO> getClienteByID(@PathVariable("id") int idCliente) {
		ClienteDTO clienteDTO = clienteService.getClienteById(idCliente);
		return ResponseEntity.ok(clienteDTO);
	}

	@GetMapping
	public ResponseEntity<List<ClienteDTO>> getAllCliente() {
		List<ClienteDTO> cliente = clienteService.getAllCliente();
		return ResponseEntity.ok(cliente);
	}

	@PutMapping("{Id}")
	public ResponseEntity<ClienteDTO> updateCliente(@PathVariable("Id") int idCliente,
			@RequestBody ClienteDTO updateCliente) {
		ClienteDTO clienteDto = clienteService.updateCliente(idCliente, updateCliente);
		return ResponseEntity.ok(clienteDto);
	}

	@DeleteMapping("{Id}")
	public ResponseEntity<String> deleteCliente(@PathVariable("Id") int idCliente) {
		clienteService.deleteCliente(idCliente);
		return ResponseEntity.ok("Cliente Eliminada Correctamente");
	}
	
	@GetMapping("/usuario/{usuario}")
	public ResponseEntity<ClienteDTO> getClienteByUsuario(@PathVariable String usuario) {
	    return ResponseEntity.ok(clienteService.getClienteByUsuario(usuario));
	}


}
