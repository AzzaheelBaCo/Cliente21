package tecnm.itch.progweb.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import tecnm.itch.progweb.dto.ReservaDTO;

@FeignClient(name = "Reservas")
public interface ReservaFeign {

    @GetMapping("/api/reserva/{id}")
    ReservaDTO obtenerRPorId(@PathVariable("id") Integer id);
}
