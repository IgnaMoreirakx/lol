package microservicios.acopios.Repository;

import microservicios.acopios.Entity.AcopioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcopioRepository extends JpaRepository<AcopioEntity, Integer> {
}
