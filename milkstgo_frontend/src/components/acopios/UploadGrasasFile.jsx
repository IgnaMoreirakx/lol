import { Component } from "react";
import AcopioUploadService from "../../services/AcopioUploadService";
import swal from 'sweetalert';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import '../../styles/klsGrasas.css';
import Navbar from '../../components/navbar';

class uploadGrasasFile extends Component{
    constructor(props){
        super(props);
        this.state = {
            file: null,
        };
        this.onFileChange = this.onFileChange.bind(this);
    }
    onFileChange(event){
        this.setState({file: event.target.files[0]});
    }
    onFileUpload = () => {
        swal({
            title: "¿Es correcto el archivo?"
        }).then(res =>{
            const formData = new FormData();
            formData.append("file", this.state.file);
            AcopioUploadService.uploadGrasas(formData).then(res =>{

            });
        });
    };
    render(){
        return(
            <div>
                <Navbar />
                <div className = "image-upload-wrap">
                    <div className="container">
                        <h1><b>Cargar el archivo de datos</b></h1>
                        <Form.Group controlId = "formFileLg">
                        <br/>
                        <Form.Control className = "file-upload-btn" type="file" size = "lg" onChange = {this.onFileChange} />
                        <br/>
                        </Form.Group>
                        <Button className = "file-upload-btn" onClick={this.onFileUpload}>
                            Cargar el archivo a la Base de Datos</Button>
                    </div>
                </div>
            </div>
        )
    }
}
export default uploadGrasasFile;