import React from 'react';
import '../styles/navbarStyle.css';

function navbar() {
    return (
      <div>
        <header className="header">
            <div className="logo">
                <a href="/"><h1>MilkStgo</h1></a>
            </div> 
            <a class="btn" href="/"><button>Volver al menú principal</button></a>
            <a class="btn" href="/subir-archivos"><button>Subir archivos</button></a>
            <a class="btn" href="/nuevo-proveedor"><button>Ingresar nuevo Proveedor</button></a>
        </header>
      </div>
    );
  }
  export default navbar;