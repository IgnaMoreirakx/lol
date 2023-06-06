package microservicios.pagos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Acopio {
    private String fecha;
    private String turno;
    private String proveedor;
    private String kls_leche;
    private String porcentaje_grasa;
    private String solidos_total;
}
