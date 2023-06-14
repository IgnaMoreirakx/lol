import React, { Component } from 'react'
import '../App.css';
import NavbarHome from '../components/navbarHome';


class HomeComponent extends Component{
    
    render(){
        return(
            <div>
                <NavbarHome />
                <div class='container'>
                    <div class='row'>
                    <div class='col-12'>
                        <br/>
                        <div class="data-card">
                        <div class="card-body">
                            <h2>File</h2>
                            <p>Acá podrás subir y ver los acopios necesarios.</p>
                            <a class="btn-2" href="/subir-archivos"><button>Ir a subir archivos</button></a>
                            <a class="btn-2" href="/fileInformation"><button>Ir a ver información de archivos</button></a>
                        </div>
                        </div>
                    </div>
                    </div>
                </div>
                <div class='container'>
                    <div class='row'>
                    <div class='col-12'>
                        <br/>
                        <div class="data-card">
                        <div class="card-body">
                            <h2>Proveedores</h2>
                            <p>Acá podrás registrar un proveedor y ver a los que ya lo están.</p>
                            <a class="btn-2" href="/nuevo-proveedor"><button>Ir a registrar a un proveedor</button></a>
                            <a class="btn-2" href="/ver-proveedores"><button>Ir a ver proveedores</button></a>
                        </div>
                        </div>
                    </div>
                    </div>
                </div>
                <div class='container'>
                    <div class='row'>
                    <div class='col-12'>
                        <br/>
                        <div class="data-card">
                        <div class="card-body">
                            <h2>Pagos</h2>
                            <p>Acá podrás ver la planilla de pagos y calcular un nuevo pago.</p>
                            <a class="btn-2" href="/calcular-pago"><button>Ir a calcular un pago</button></a>
                            <a class="btn-2" href="/ver-planilla"><button>Ir a ver planilla de pago</button></a>
                        </div>
                        </div>
                    </div>
                    </div>
                </div>
            </div>
        );
    }
}
export default HomeComponent;

