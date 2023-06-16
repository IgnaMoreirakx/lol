import axios from 'axios';

const ACOPIO_API_URL = "http://localhost:8080/acopio";

class acopioUploadService {

    uploadKilos(file){
        return axios.post(ACOPIO_API_URL + "/filekls", file);
    }

    uploadGrasas(file){
        return axios.post(ACOPIO_API_URL + "/filegrasas", file);
    }

    getAcopios(){
        return axios.get(ACOPIO_API_URL);
    }

}

export default new acopioUploadService()