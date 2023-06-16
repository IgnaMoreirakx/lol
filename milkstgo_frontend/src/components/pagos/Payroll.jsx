import { Component } from "react";
import PagosService from "../../services/PagosService";
import Navbar from '../../components/navbar';
import swal from "sweetalert";

class payroll extends Component{
    constructor(props){
        super(props);
        this.state ={
            pagos: [],
            flag: ""
        };
    };
    componentDidMount(){
        PagosService.getPagos().then(res =>{
            if(Object.keys(res.data).length === 0){
                this.noData();
            }else{
                this.setState({pagos: res.data});
                this.setState({flag: "Si hay"})
            }
        })
        .catch(error =>{
            this.noData();
        })
    };
    noData(){
        swal({
            title: "Lo siento, intenta calcular pagos primero"
        }).then(respuesta =>{
            window.location.href = "/calcular-pago"; 
        });
    };
    render(){
            return(
                <div>
                    <Navbar />
                    <div class="h1">
                        <h1><b>Información de pagos</b></h1>
                    </div>
                        <div className = "container my-2">
                            <div className="table-container">
                                <table style={{border: '1px solid black'}} className = "content-table">
                                    <thead>
                                    <tr>
                                        <th>nombre_proveedor</th>
                                        <th>codigo_proveedor</th>
                                        <th>Quincena</th>
                                        <th>Total kilos de leche</th>
                                        <th>Nro de envios</th>
                                        <th>Promedio de kls diarios</th>
                                        <th>Porcentaje de grasa</th>
                                        <th>Porcentaje de solidos totales</th>
                                        <th>Variacion negativa de leche</th>
                                        <th>Variacion negativa de grasa</th>
                                        <th>Variacion negativa de ST</th>
                                        <th>Pago por el total de leche</th>
                                        <th>Pago por el porcentaje grasa total</th>
                                        <th>Pago por los solidos totales</th>
                                        <th>Bono por frecuencia</th>
                                        <th>Dcto var leche</th>
                                        <th>Dcto var grasas</th>
                                        <th>Dcto var solidos</th>
                                        <th>Pago Total</th>
                                        <th>Monto retencion</th>
                                        <th>Monto final </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        {this.state.flag === "Si hay" ?(
                                            this.state.pagos.map(
                                                pagos =>
                                                <tr key = {pagos.id}>
                                                    <td>{pagos.nombre_proveedor}</td>
                                                    <td>{pagos.codigo_proveedor}</td>
                                                    <td>{pagos.quincena}</td>
                                                    <td>{pagos.total_kilos}</td>
                                                    <td>{pagos.total_envios}</td>
                                                    <td>{pagos.promedio_diario_kilos}</td>
                                                    <td>{pagos.total_grasa}</td>
                                                    <td>{pagos.total_solidos}</td>
                                                    <td>{pagos.variacion_leche}</td>
                                                    <td>{pagos.variacion_grasa}</td>
                                                    <td>{pagos.variacion_st}</td>
                                                    <td>{pagos.pago_leche}</td>
                                                    <td>{pagos.pago_grasa}</td>
                                                    <td>{pagos.pago_solidos}</td>
                                                    <td>{pagos.bonus_freq}</td>
                                                    <td>{pagos.dcto_var_leche}</td>
                                                    <td>{pagos.dcto_var_grasa}</td>
                                                    <td>{pagos.dcto_var_st}</td>
                                                    <td>{pagos.pago_total}</td>
                                                    <td>{pagos.retencion}</td>
                                                    <td>{pagos.monto_final}</td>
                                                </tr>
                                            )
                                        ):null
                                    }
                                    </tbody>
                                </table>
                            </div>
                        </div>
                </div>
            )
    }
}
export default payroll;