package microservicios.pagos.service;

import lombok.Data;
import microservicios.pagos.entity.Pagos;
import microservicios.pagos.model.Acopio;
import microservicios.pagos.model.Proveedor;
import microservicios.pagos.repository.PagosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PagosService {

    @Autowired
    PagosRepository pagosRepository;

    @Autowired
    RestTemplate restTemplate;


    public List<Pagos> getPagos(){
        return pagosRepository.findAll();
    }

    public Acopio acopioByCodigo(String codigo){
        Acopio acopio = restTemplate.getForObject("http://acopio-service/acopio/" + codigo, Acopio.class);
        if(acopio == null){
            return null;
        }
        return acopio;
    }

    public List<Proveedor> getProveedores(){
        List<Proveedor> proveedores = restTemplate.getForObject("http://proveedor-service/proveedor", List.class);
        if(proveedores.isEmpty()){
            return null;
        }
        return proveedores;
    }

    public Proveedor proveedorByCodigo(String codigo){
        Proveedor proveedor = restTemplate.getForObject("http://proveedor-service/proveedor/" + codigo, Proveedor.class);
        return proveedor;
    }

    public String getTurnosEnvios(String codigo){
        String turno = restTemplate.getForObject("http://acopio-service/acopio/turnos/" + codigo, String.class);
        return turno;
    }

    public Integer getTotalEnvios(String codigo){
        Integer envios = restTemplate.getForObject("http://acopio-service/acopio/envios/" + codigo, Integer.class);
        return envios;
    }

    public Integer getTotalKls(String codigo){
        Integer totalKls = restTemplate.getForObject("http://acopio-service/acopio/totalKls/" + codigo, Integer.class);
        return totalKls;
    }

    public Integer getPromedioKls(String codigo){
        Integer promedio = restTemplate.getForObject("http://acopio-service/acopio/promedio/" + codigo, Integer.class);
        return promedio;
    }


    public void planillaProveedor(String codigo) {
        Acopio acopioProveedor = acopioByCodigo(codigo);
        Proveedor proveedor = proveedorByCodigo(codigo);
        if(acopioProveedor.getProveedor() != null) {
            Pagos pagos = new Pagos();
                if (proveedor.getCodigo().equals(codigo)) {
                    pagos.setCodigo_proveedor(codigo);
                    pagos.setNombre_proveedor(proveedor.getNombre());
                    pagos.setQuincena(getQuincena(acopioProveedor.getFecha()));

                    listarInfoAcop(codigo, acopioProveedor, pagos);
                    listarVariaciones(codigo, pagos);

                    Integer pagoLeche = pagoLeche(proveedor.getCategoria(), pagos.getTotal_kilos());
                    Double bonificacion = bonificacion(codigo, pagoLeche, pagos.getTotal_envios());


                    pagos.setPago_leche(pagoLeche);
                    pagos.setBonus_freq(bonificacion);
                    listarPagos(acopioProveedor, pagos);

                    Double pagoAcopioLeche = pagoLeche + pagos.getPago_grasa() + pagos.getPago_solidos() + bonificacion;
                    pagos.setPago_total(pagoAcopioLeche);
                    listarDcto(pagos, pagoAcopioLeche);


                    Double descuentosTotal = pagos.getDcto_var_grasa() + pagos.getDcto_var_leche() + pagos.getDcto_var_st();
                    Double pagoTotal = pagoAcopioLeche - descuentosTotal;
                    Double montoRetencion = calcularRetencion(proveedor.getRetencion(), pagoTotal);
                    Double montoFinal = pagoTotal - montoRetencion;

                    pagos.setPago_total(pagoTotal);
                    pagos.setRetencion(montoRetencion);
                    pagos.setMonto_final(montoFinal);

                }
            pagosRepository.save(pagos);
        }
    }

    public String getQuincena(String fecha) {
        SimpleDateFormat formateQuincena = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaDate = new Date();
        try {
            fechaDate = formateQuincena.parse(fecha);
        } catch (ParseException e) {
            return "";
        }
        Integer numFecha = fechaDate.getDate();
        Integer numQuincena;
        if (numFecha <= 15) {
            numQuincena = 1;
        } else {
            numQuincena = 2;
        }
        fechaDate.setDate(numQuincena);

        return formateQuincena.format(fechaDate);
    }

    public List<Pagos> pagosByCodigo(String codigo) {
        List<Pagos> pagosTotales = pagosRepository.findAll();
        List<Pagos> pagosProveedor = new ArrayList<>();
        for (int i = 0; i < pagosTotales.size(); i++) {
            if (pagosTotales.get(i).getCodigo_proveedor().equals(codigo)) {
                pagosProveedor.add(pagosTotales.get(i));
            }
        }
        return pagosProveedor;
    }

    public Integer pagoLeche(String categoria, Integer totalKls) {
        Integer pagoLeche = 0;
        if (categoria.equals("A")) {
            pagoLeche = 700 * totalKls;
        }
        if (categoria.equals("B")) {
            pagoLeche = 550 * totalKls;
        }
        if (categoria.equals("C")) {
            pagoLeche = 400 * totalKls;
        }
        if (categoria.equals("D")) {
            pagoLeche = 250 * totalKls;
        }
        return pagoLeche;
    }

    public Integer pagoGrasa(Integer totalGrasa, Integer totalKls) {
        Integer pagoGrasa = 0;
        if (totalGrasa <= 20) {
            pagoGrasa = 30 * totalKls;
        }
        if (totalGrasa >= 21 && totalGrasa <= 45) {
            pagoGrasa = 80 * totalKls;
        }
        if (totalGrasa >= 46) {
            pagoGrasa = 120 * totalKls;
        }
        return pagoGrasa;
    }

    public Integer pagoSolidos(Integer totalSolidos, Integer totalKls) {
        Integer pagoSolidos = 0;
        if (totalSolidos <= 7) {
            pagoSolidos = -130 * totalKls;
        }
        if (totalSolidos >= 8 && totalSolidos <= 18) {
            pagoSolidos = -90 * totalKls;
        }
        if (totalSolidos >= 19 && totalSolidos <= 35) {
            pagoSolidos = 95 * totalKls;
        }
        if (totalSolidos >= 36) {
            pagoSolidos = 150 * totalKls;
        }
        return pagoSolidos;
    }

    public Double bonificacion(String codigo, Integer pagoLeche, Integer nroDias) {
        Double pago = 0.0;
        if (nroDias > 10) {
            String turnos = getTurnosEnvios(codigo);
            if (turnos.equals("M")) {
                pago = pagoLeche * 0.12;
            }
            if (turnos.equals("T")) {
                pago = pagoLeche * 0.08;

            }
            if (turnos.equals("MyT")) {
                pago = pagoLeche * 0.2;
            }
        }
        return pago;
    }

    public Double dctoPagoLeche(Double variacion, Double pagoLeche) {
        Double dcto = 0.0;
        if (variacion >= 0 && variacion <= 8) {
            dcto = 0.0 * pagoLeche;
        }
        if (variacion >= 9 && variacion <= 25) {
            dcto = 0.07 * pagoLeche;
        }
        if (variacion >= 26 && variacion <= 45) {
            dcto = 0.15 * pagoLeche;
        }
        if (variacion >= 46) {
            dcto = 0.3 * pagoLeche;
        }
        dcto = Math.round(dcto * 100.0) / 100.0;
        return dcto;
    }

    public Double dctoPagoGrasa(Double variacion, Double pagoLeche) {
        Double dcto = 0.0;
        if (variacion > 0 && variacion <= 15) {
            dcto = 0.0 * pagoLeche;
        }
        if (variacion >= 16 && variacion <= 25) {
            dcto = 0.12 * pagoLeche;
        }
        if (variacion >= 26 && variacion <= 40) {
            dcto = 0.20 * pagoLeche;
        }
        if (variacion >= 41) {
            dcto = 0.3 * pagoLeche;
        }
        dcto = Math.round(dcto * 100.0) / 100.0;
        return dcto;
    }

    public Double dctoPagoSolidos(Double variacion, Double pagoLeche) {
        Double dcto = 0.0;
        if (variacion > 0 && variacion <= 6) {
            dcto = 0.0 * pagoLeche;
        }
        if (variacion >= 7 && variacion <= 12) {
            dcto = 0.18 * pagoLeche;
        }
        if (variacion >= 13 && variacion <= 35) {
            dcto = 0.27 * pagoLeche;
        }
        if (variacion >= 36) {
            dcto = 0.45 * pagoLeche;
        }
        dcto = Math.round(dcto * 100.0) / 100.0;
        return dcto;
    }

    public Double calcularRetencion(String retencion, Double pagoTotal) {
        Double montoRetencion = 0.0;
        if (retencion.equals("Si") && pagoTotal > 950000.0) {
            montoRetencion = pagoTotal * 0.13;
        }
        montoRetencion = Math.round(montoRetencion * 100.0) / 100.0;
        return montoRetencion;
    }

    public Double variacionLeche(Integer valorAntiguo, Integer nuevoTotal) {
        Double var;
        var = (((double) valorAntiguo - (double) nuevoTotal) / (double) valorAntiguo) * 100;
        var = Math.round(var * 100.0) / 100.0;
        if (var < 0) {
            return 0.0;
        }
        return var;
    }

    public Double variacionGrasaSolidos(Integer valorAntiguo, Integer nuevoTotal) {
        Double variacion;
        variacion = (double) valorAntiguo - (double) nuevoTotal;
        if (variacion < 0.0) {
            variacion = 0.0;
        }
        return variacion;
    }
    public void listarInfoAcop(String codigo, Acopio acopio, Pagos pagos) {
        pagos.setTotal_grasa(Integer.parseInt(acopio.getPorcentaje_grasa()));
        pagos.setTotal_solidos(Integer.parseInt(acopio.getSolidos_total()));
        Integer envios = getTotalEnvios(codigo);
        Integer totalKls = getTotalKls(codigo);
        Integer promedioKls = getPromedioKls(codigo);
        pagos.setTotal_envios(envios);
        pagos.setTotal_kilos(totalKls);
        pagos.setPromedio_diario_kilos(promedioKls);
    }

    public void listarDcto(Pagos pagos, Double pagoAcopioLeche) {

        Double dctoVarLeche = dctoPagoLeche(pagos.getVariacion_leche(), pagoAcopioLeche);
        Double dctoVarGrasa = dctoPagoGrasa(pagos.getVariacion_grasa(), pagoAcopioLeche);
        Double dctoVarSolidos = dctoPagoSolidos(pagos.getVariacion_st(), pagoAcopioLeche);
        pagos.setDcto_var_leche(dctoVarLeche);
        pagos.setDcto_var_grasa(dctoVarGrasa);
        pagos.setDcto_var_st(dctoVarSolidos);
    }

    public void listarPagos(Acopio acopio, Pagos pagos) {
        Integer pagoGrasaa = pagoGrasa(Integer.parseInt(acopio.getPorcentaje_grasa()), pagos.getTotal_kilos());
        Integer pagoSolido = pagoSolidos(Integer.parseInt(acopio.getSolidos_total()), pagos.getTotal_kilos());
        pagos.setPago_grasa(pagoGrasaa);
        pagos.setPago_solidos(pagoSolido);
    }
    public void listarVariaciones(String codigo, Pagos pagos) {
        List<Pagos> pagosProveedor = pagosByCodigo(codigo);
        if (pagosProveedor.isEmpty()) {
            pagos.setVariacion_leche(0.0);
            pagos.setVariacion_grasa(0.0);
            pagos.setVariacion_st(0.0);
        } else {
            Integer cantidadQuincena = (pagosProveedor.size() - 1);
            Double varLeche = variacionLeche(pagosProveedor.get(cantidadQuincena).getTotal_kilos(), pagos.getTotal_kilos());
            Double varGrasa = variacionGrasaSolidos(pagosProveedor.get(cantidadQuincena).getTotal_grasa(), pagos.getTotal_grasa());
            Double varSolidos = variacionGrasaSolidos(pagosProveedor.get(cantidadQuincena).getTotal_solidos(), pagos.getTotal_solidos());

            pagos.setVariacion_leche(varLeche);
            pagos.setVariacion_grasa(varGrasa);
            pagos.setVariacion_st(varSolidos);

        }
    }
}
