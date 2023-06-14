import axios from 'axios';

const PROVEEDOR_API_URL = "http://localhost:8080/proveedor";

class proveedorService {

    getProveedores(){
        return axios.get(PROVEEDOR_API_URL);
    }

    createProveedor(proveedor){
        return axios.post(PROVEEDOR_API_URL, proveedor);
    }

}

export default new proveedorService()