package tingeso.microservicios.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tingeso.microservicios.Entity.proveedorEntity;

@Repository
public interface proveedorRepository extends JpaRepository<proveedorEntity, Integer> {
}
