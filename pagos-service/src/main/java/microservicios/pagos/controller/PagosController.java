package microservicios.pagos.controller;

import microservicios.pagos.entity.Pagos;
import microservicios.pagos.model.Acopio;
import microservicios.pagos.model.Proveedor;
import microservicios.pagos.service.PagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagosController {

    @Autowired
    PagosService pagosService;

    @GetMapping("/acopio/{codigo}")
    public ResponseEntity<Acopio> acopioByCodigo(@PathVariable String codigo){
        return ResponseEntity.ok(pagosService.acopioByCodigo(codigo));
    }
    @PostMapping("/calcular/{codigo}")
    public String llenarPlantilla(@PathVariable String codigo){
        pagosService.planillaProveedor(codigo);
        if(pagosService.getPagos().isEmpty()){
            return "No fue posible";
        }
        return "Se calculo con éxito!!!";
    }

    @GetMapping("/prueba/{codigo}")
    public Proveedor proveedorByCodigo(@PathVariable String codigo){
        return pagosService.proveedorByCodigo(codigo);
    }

    @GetMapping
    public ResponseEntity<List<Pagos>> getPagos(){
        List<Pagos> pagos = pagosService.getPagos();
        if(pagos.isEmpty()){
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagos);
    }
}
