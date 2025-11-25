package tecnm.itch.progweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Cliente21Application {

	public static void main(String[] args) {
		SpringApplication.run(Cliente21Application.class, args);
	}

}
