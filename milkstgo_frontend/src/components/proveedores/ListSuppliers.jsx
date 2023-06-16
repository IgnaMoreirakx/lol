import React, { Component } from 'react'
import '../../styles/listaStyle.css';
import proveedorService from '../../services/ProveedorService';
import Navbar from '../../components/navbar';

class ListSuppliers extends Component{
    constructor(props){
        super(props)

        this.state = {
            proveedores: []
        };
    }

    componentDidMount(){
        proveedorService.getProveedores().then((res)=> {
            console.log(res.data);
            this.setState({ proveedores: res.data});
        });
    }
    render(){
        return (
            <div>
                <Navbar />
                <div style={{ textAlign: 'center' }} class = "container my-2">

                    <h1 class="h1"><b> Listado de proveedores</b></h1>
                    <table style={{ border: '1px solid black'}} class = "content-table">
                        <thead>
                        <tr>
                            <th>Codigo</th>
                            <th>Nombre</th>
                            <th>Categoria</th>
                            <th>Retencion</th>
                        </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.proveedores.map(
                                    proveedor =>
                                    <tr key = {proveedor.id}>
                                        <td> {proveedor.codigo} </td>
                                        <td> {proveedor.nombre} </td>
                                        <td> {proveedor.categoria} </td>
                                        <td> {proveedor.retencion} </td>
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}
export default ListSuppliers;