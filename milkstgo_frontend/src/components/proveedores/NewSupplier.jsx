import { Component} from "react";
import '../../styles/crearProveedor.css';
import Axios from "axios";
import Navbar from '../../components/navbar';
import proveedorService from '../../services/ProveedorService';

class NewSupplier extends Component{
    constructor(props) {
        super(props)

        this.state = {
            codigo: "",
            nombre: "",
            categoria: "",
            retencion: ""
        }
        this.changeCodigoHandler = this.changeCodigoHandler.bind(this);
        this.changenNombreHandler = this.changenNombreHandler.bind(this);
        this.changeCategoriaHandler = this.changeCategoriaHandler.bind(this);
        this.changeRetencionHandler = this.changeRetencionHandler.bind(this);
        this.saveProveedor = this.saveProveedor.bind(this);

    }
    saveProveedor = (e) => {
        e.preventDefault();
        let proveedor = {"codigo": this.state.codigo,
                        "nombre": this.state.nombre,
                        "categoria": this.state.categoria,
                        "retencion": this.state.retencion};
        let axiosCnfig ={
            headers:{
                'Access-Control-Allow-Origin': '*',
                "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-type, Accept",
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE'
            }
        }

        proveedorService.createProveedor(proveedor).then(res =>{
            window.location.href = "/ver-proveedores"; 
        })
    }
    changeCodigoHandler= (event) => {
        this.setState({codigo: event.target.value});
    }
    changenNombreHandler= (event) => {
        this.setState({nombre: event.target.value});
    }
    changeCategoriaHandler= (event) => {
        this.setState({categoria: event.target.value});
    }
    changeRetencionHandler= (event) => {
        this.setState({retencion: event.target.value});
    }
    
    render(){
        return(
            <div>
                <Navbar />
                <div  className = "mainclass" >
                    <form>
                        <h1><b>Registrar nuevo Proveedor</b></h1>
                        <div className="formcontainer">
                        <hr/>
                        <div className="container">
                            <label><strong>Código:</strong></label>
                            <input type="text" placeholder="Codigo" name="codigo" value={this.state.codigo} onChange={this.changeCodigoHandler}/>
                            <label><strong>Nombre: </strong></label>
                            <input  type="text"name = "nombre" placeholder="Nombre" value={this.state.nombre} onChange={this.changenNombreHandler}/>
                            <label><strong>Categoría del proveedor</strong></label>
                            <select className="select" name="categoria" value={this.state.categoria} onChange={this.changeCategoriaHandler}>
                                <option value="">Ingresa la categoría</option>
                                <option value="A">A</option>
                                <option value="B">B</option>
                                <option value="C">C</option>
                                <option value="D">D</option>
                            </select>
                            <label><strong>¿El proveedor tiene retención?</strong></label>
                            <select className="select" name="categoria" value={this.state.retencion} onChange={this.changeRetencionHandler}>
                                <option value="">Ingresa la categoría</option>
                                <option value="Si">Si</option>
                                <option value="No">No</option>
                            </select>
                        </div>
                        <button className="btn2" onClick={this.saveProveedor}>Registrar Proveedor</button>
                        </div>
                    </form>
                </div>
            </div>
        )
    }
}
export default NewSupplier;