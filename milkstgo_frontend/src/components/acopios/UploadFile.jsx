import React, { Component } from 'react'
import '../../styles/uploadFileStyle.css';
import Navbar from '../../components/navbar';

class uploadFile extends Component{
    render(){
        return(
            <div>
                <Navbar />
                <section class="page-contain">
                    <a style = {{color: "black", fontFamily: 'Brush Script MT'}} href="/fileUploadKls" class="data-card">
                        <h4>Acopio kilos</h4>
                        <p>Subir acopio sobre los kilos de leche.</p>
                    </a>
                    <a href="/fileUploadGrasas" class="data-card">
                        <h4>Acopio grasas y solidos</h4>
                        <p>Subir acopio sobre las grasas y solidos.</p>
                    </a>
                </section>
            </div>
    )}
}
export default uploadFile;