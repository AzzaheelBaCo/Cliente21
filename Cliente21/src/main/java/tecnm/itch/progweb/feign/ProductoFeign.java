package tecnm.itch.progweb.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import tecnm.itch.progweb.dto.ProductoDTO;

@FeignClient(name = "Restaurante")
public interface ProductoFeign {

    @GetMapping("/api/producto/{id}")
    ProductoDTO obtenerPorId(@PathVariable("id") Integer id);

    @PostMapping("/api/producto/list")
    List<ProductoDTO> getProductosByIds(@RequestBody List<Integer> ids);
}
