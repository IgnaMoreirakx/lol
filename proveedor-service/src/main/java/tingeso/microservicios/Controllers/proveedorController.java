package tingeso.microservicios.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso.microservicios.Entity.proveedorEntity;
import tingeso.microservicios.Services.proveedorService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/proveedor")
public class proveedorController {

    @Autowired
    proveedorService proveedorservice;

    @GetMapping
    public ResponseEntity<List<proveedorEntity>> getAll(){
        List<proveedorEntity> proveedores = proveedorservice.getProveedores();
        if(proveedores.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(proveedores);
    }

    @PostMapping
    public ResponseEntity<proveedorEntity> save(@RequestBody proveedorEntity proveedor){
        return ResponseEntity.ok(proveedorservice.save(proveedor));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<proveedorEntity> getByCodigo(@PathVariable String codigo){
        proveedorEntity proveedor = proveedorservice.getByCodigo(codigo);
        if(proveedor == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(proveedor);
    }
}
