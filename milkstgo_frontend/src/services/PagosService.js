import axios from 'axios';

const ACOPIO_API_URL = "http://localhost:8080/pagos";
class pagosService {

    getPagos(file){
        return axios.get(ACOPIO_API_URL);
    }

    calcularPago(codigo){
        return axios.post(ACOPIO_API_URL + "/calcular/" + codigo);
    }

}

export default new pagosService();