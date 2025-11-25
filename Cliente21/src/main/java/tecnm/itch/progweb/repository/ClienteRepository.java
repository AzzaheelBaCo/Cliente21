package tecnm.itch.progweb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tecnm.itch.progweb.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	Optional<Cliente> findByUsuario(String usuario);

}
