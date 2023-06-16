package microservicios.pagos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagos {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String quincena;
    private String codigo_proveedor;
    private String nombre_proveedor;
    private Integer total_kilos;
    private Integer total_envios;
    private Integer promedio_diario_kilos;
    private Double variacion_leche;
    private Integer total_grasa;
    private Double variacion_grasa;
    private Integer total_solidos;
    private Double variacion_st;
    private Integer pago_leche;
    private Integer pago_grasa;
    private Integer pago_solidos;
    private Double bonus_freq;
    private Double dcto_var_leche;
    private Double dcto_var_grasa;
    private Double dcto_var_st;
    private Double pago_total;
    private Double retencion;
    private Double monto_final;
}
