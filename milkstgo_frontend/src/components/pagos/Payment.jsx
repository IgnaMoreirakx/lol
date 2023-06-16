import { Component } from "react";
import PagosService from "../../services/PagosService";
import swal from "sweetalert";
import Navbar from '../../components/navbar';
import '../../styles/formStyles.css';

class Payment extends Component{
    constructor(props){
        super(props)

        this.state ={
            codigo: ""
        };
    this.changeCodigoHandler = this.changeCodigoHandler.bind(this);
    };
    CalcularPago =(e) =>{
        e.preventDefault();
        const codigo = this.state.codigo;
        console.log(this.state.codigo)
        PagosService.calcularPago(this.state.codigo).then(res =>{
            console.log(res.data)
            swal({
                title: "Se calculó correctamente"
            }).then(resp =>{
                window.location.href = "/ver-planilla"; 
            });
            
        })
    }
    changeCodigoHandler= (event) => {
        this.setState({codigo: event.target.value});
    }
    render(){
        return(
            <div>
                <Navbar />
                <div class = "mainclass">
                    <form>
                        <h1><b>Ingrese el código del proveedor</b></h1>
                        <div className="formcontainer">
                        <hr/>
                        <div className="container">
                            <input className="placeholder" type="text"
                                placeholder="Código" name="codigo" value={this.state.codigo} onChange={this.changeCodigoHandler} />
                        </div>
                        <button className="but" onClick={this.CalcularPago}>Calcular</button>
                        </div>
                    </form>
                </div>
            </div>
        )
    }
}
export default Payment