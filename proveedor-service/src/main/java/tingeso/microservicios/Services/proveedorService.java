package tingeso.microservicios.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tingeso.microservicios.Entity.proveedorEntity;
import tingeso.microservicios.Repositories.proveedorRepository;

import java.util.List;

@Service
public class proveedorService {
    @Autowired
    proveedorRepository Proveedorrepository;

    public proveedorEntity save(proveedorEntity proveedor){
        proveedorEntity proveedorNew = Proveedorrepository.save(proveedor);
        return proveedorNew;
    }

    public List<proveedorEntity> getProveedores(){
        return Proveedorrepository.findAll();
    }

    public proveedorEntity getByCodigo(String codigo){
        proveedorEntity proveedorEntregar = new proveedorEntity();
        List<proveedorEntity> proveedores = getProveedores();
        for(int i = 0; i< proveedores.size(); i++){
            if(proveedores.get(i).getCodigo().equals(codigo)){
                proveedorEntregar = proveedores.get(i);
            }
        }
        return proveedorEntregar;
    }
}
