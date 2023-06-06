package microservicios.acopios.Service;

import lombok.Generated;
import microservicios.acopios.Entity.AcopioEntity;
import microservicios.acopios.Repository.AcopioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class AcopioService {

    @Autowired
    AcopioRepository acopioRepository;

    private final Logger log = LoggerFactory.getLogger(AcopioService.class);

    public List<AcopioEntity> getAcopios(){
        return acopioRepository.findAll();
    }

    public String guardar(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    log.info("Archivo guardado");
                } catch (IOException e) {
                    log.error("ERROR guardado", e);
                }
            }
            return "Archivo guardado con exito!";
        } else {
            return "No se pudo guardar el archivo";
        }
    }

    public void leerCsvKilos(String direccion) {
        BufferedReader bf = null;
        acopioRepository.deleteAll();
        try {
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while ((bfRead = bf.readLine()) != null) {
                if (count == 1) {
                    count = 0;
                } else {
                    guardarDataKilos(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2], bfRead.split(";")[3]);
                    temp = temp + "\n" + bfRead;
                }
            }
            System.out.println("Archivo leido exitosamente");
        } catch (Exception e) {
            System.err.println("No se encontro el archivo");
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    log.error("ERROR lectura kilos", e);
                }
            }
        }
    }
    public void guardarData(AcopioEntity data) {
        acopioRepository.save(data);
    }

    public void guardarDataKilos(String fecha, String turno, String proveedor, String klsLeche){
        AcopioEntity newData = new AcopioEntity();
        newData.setProveedor(proveedor);
        newData.setFecha(fecha);
        newData.setKls_leche(klsLeche);
        newData.setTurno(turno);
        guardarData(newData);
    }

    public void leerCsvGrasas(String direccion) {
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while ((bfRead = bf.readLine()) != null) {
                if (count == 1) {
                    count = 0;
                } else {
                    guardarDataGrasas(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2]);
                    temp = temp + "\n" + bfRead;
                }
            }
            System.out.println("Archivo leido exitosamente");
        } catch (Exception e) {
            System.err.println("No se encontro el archivo");
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    log.error("ERROR lectura grasas", e);
                }
            }
        }
    }

    public void guardarDataGrasas(String proveedor, String grasas, String solidos){
        List<AcopioEntity> newData = getByProveedor(proveedor);
        for(int i = 0; i< newData.size(); i++) {
            newData.get(i).setPorcentaje_grasa(grasas);
            newData.get(i).setSolidos_total(solidos);
            guardarData(newData.get(i));
        }
    }

    public List<AcopioEntity> getByProveedor(String proveedor){
        List<AcopioEntity> aux = acopioRepository.findAll();
        List<AcopioEntity> proveedores = new ArrayList<>();
        for(int i = 0; i < aux.size(); i++){
            if(aux.get(i).getProveedor().equals(proveedor)){
                proveedores.add(aux.get(i));
            }
        }
        return proveedores;
    }

    public AcopioEntity oneAcopio(Integer codigo){
        List<AcopioEntity> acopios = acopioRepository.findAll();
        AcopioEntity acopioBuscado = new AcopioEntity();
        for(int i = 0; i < acopios.size(); i++){
            if(codigo.equals(Integer.parseInt(acopios.get(i).getProveedor()))){
                acopioBuscado = acopios.get(i);
            }
        }
        return acopioBuscado;
    }

    public Integer getTotalKls(String codigo){
        List<AcopioEntity> acopioProveedor = getByProveedor(codigo);
        Integer cont = 0;
        for(int i = 0; i < acopioProveedor.size(); i++){
            cont = cont + Integer.parseInt(acopioProveedor.get(i).getKls_leche());
        }
        return cont;
    }

    public Integer gettotalEnvios(String codigo){
        List<AcopioEntity> acopioProveedor = getByProveedor(codigo);
        Integer envios = 0;
        for(int i = 0; i < acopioProveedor.size(); i++){
            envios++;
        }
        return envios;
    }

    public Integer getPromedioKls(String codigo){
        Integer envios = gettotalEnvios(codigo);
        Integer totalKls = getTotalKls(codigo);
        return (totalKls / envios);
    }

    public String getTurnosEnvios(String codigo){
        List<AcopioEntity> acopioProveedor = getByProveedor(codigo);
        List<String> turnos = new ArrayList<>();
        for(int i = 0; i < acopioProveedor.size(); i++){
            turnos.add(acopioProveedor.get(i).getTurno());
        }

        List<String> maniana = new ArrayList<>();
        List<String> tarde = new ArrayList<>();
        for(int j = 0; j < turnos.size(); j++){
            maniana.add("M");
            tarde.add("T");
        }
        if(turnos.equals(maniana)){
            return "M";
        }
        if(turnos.equals(tarde)){
            return "T";
        }
        else {
            return "MyT";
        }
    }

}
