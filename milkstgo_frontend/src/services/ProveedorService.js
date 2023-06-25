import axios from 'axios';

const PROVEEDOR_API_URL = "http://127.0.0.1:52141/proveedor";

class proveedorService {

    getProveedores(){
        return axios.get(PROVEEDOR_API_URL);
    }

    createProveedor(proveedor){
        return axios.post(PROVEEDOR_API_URL, proveedor);
    }

}

export default new proveedorService()