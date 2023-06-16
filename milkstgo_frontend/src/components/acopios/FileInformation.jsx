import { Component } from "react";
import AcopioUploadService from "../../services/AcopioUploadService";
import Navbar from '../../components/navbar';
import '../../styles/listaStyle.css';
import swal from "sweetalert";

class fileInformation extends Component{
    constructor(props){
        super(props)

        this.state ={
            acopio: [],
            flag: ""
        };
    }
    componentDidMount(){
        AcopioUploadService.getAcopios().then(res =>{
            if(Object.keys(res.data).length === 0){
                this.noData();
                this.setState({flag: "no hay"})
            }else{
            this.setState({acopio: res.data});
            this.setState({flag: "si hay"});
            }
        })
        .catch(error =>{
            this.noData();
        })
    }

    noData(){
        swal({
            title: "Lo siento, intenta cargar archivos primero"
        }).then(respuesta =>{
            window.location.href = "/subir-archivos"; 
        })
    }
    render(){
        return(
            <div>
                <Navbar />
                <div className = "container my-2">
                    <h1 className="h1"><b> Listado de acopios</b></h1>
                    <table style={{border: '1px solid black'}} className = "content-table">
                        <thead>
                        <tr>
                            <th>Fecha</th>
                            <th>Turno</th>
                            <th>Proveedor</th>
                            <th>Leche</th>
                            <th>Grasa</th>
                            <th>Solidos</th>
                        </tr>
                        </thead>
                        <tbody>
                            {this.state.flag === "si hay" ?(
                                this.state.acopio.map(acopio =>
                                    <tr key={acopio.id}>
                                    <td>{acopio.fecha}</td>
                                    <td>{acopio.turno}</td>
                                    <td>{acopio.proveedor}</td>
                                    <td>{acopio.kls_leche}</td>
                                    <td>{acopio.porcentaje_grasa}</td>
                                    <td>{acopio.solidos_total}</td>
                                    </tr>
                                )
                            ):null
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}
export default fileInformation;