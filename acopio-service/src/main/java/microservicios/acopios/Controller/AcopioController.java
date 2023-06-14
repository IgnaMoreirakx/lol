package microservicios.acopios.Controller;

import microservicios.acopios.Entity.AcopioEntity;
import microservicios.acopios.Service.AcopioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/acopio")
public class AcopioController {

    @Autowired
    AcopioService acopioService;

    @GetMapping
    public ResponseEntity<List<AcopioEntity>> getAcopios(){
        List<AcopioEntity> acopios = acopioService.getAcopios();
        if(acopios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(acopios);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<AcopioEntity> getByCodigo(@PathVariable Integer codigo){
        AcopioEntity acopio = acopioService.oneAcopio(codigo);
        if(acopio == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(acopio);
    }

    @PostMapping("/filekls")
    public String uploadKls(@RequestParam("file") MultipartFile file) {
        acopioService.guardar(file);
        acopioService.leerCsvKilos("AcopioKls.csv");
        return "Se leyó correctamente";
    }

    @PostMapping("/filegrasas")
    public String uploadGrasas(@RequestParam("file") MultipartFile file) {
        acopioService.guardar(file);
        acopioService.leerCsvGrasas("AcopioGrasas.csv");
        return "Se leyó correctamente";
    }

    @GetMapping("/turnos/{codigo}")
    public String turnoEnvios(@PathVariable String codigo){
        return acopioService.getTurnosEnvios(codigo);
    }

    @GetMapping("/envios/{codigo}")
    public Integer getTotalEnvios(@PathVariable String codigo){
        return acopioService.gettotalEnvios(codigo);
    }

    @GetMapping("/totalKls/{codigo}")
    public Integer getTotalKls(@PathVariable String codigo){
        return acopioService.getTotalKls(codigo);
    }

    @GetMapping("/promedio/{codigo}")
    public Integer getPromedioKls(@PathVariable String codigo){
        return acopioService.getPromedioKls(codigo);
    }

}
