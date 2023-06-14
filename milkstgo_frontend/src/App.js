import React from 'react';
import ListSuppliers from './components/proveedores/ListSuppliers';
import HomeComponent from './components/HomeComponent';
import UploadFile from './components/acopios/UploadFile';
import UploadKilosFile from './components/acopios/UploadKilosFile';
import { BrowserRouter, Route, Routes } from "react-router-dom";

function App() {
  return (
    <div>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<HomeComponent />} />
            <Route path= "/ver-proveedores" element={<ListSuppliers />} />
            <Route path= "/subir-archivos" element={<UploadFile />} />
            <Route path= "/fileUploadKls" element={<UploadKilosFile />} />
          </Routes>
        </BrowserRouter>
  </div>
  );
}
export default App;
