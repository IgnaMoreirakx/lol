import React from 'react';
import ListSuppliers from './components/proveedores/ListSuppliers';
import HomeComponent from './components/HomeComponent';
import UploadFile from './components/acopios/UploadFile';
import UploadKilosFile from './components/acopios/UploadKilosFile';
import UploadGrasasFile from './components/acopios/UploadGrasasFile';
import FileInformation from './components/acopios/FileInformation';
import Payroll from './components/pagos/Payroll';
import NewSupplier from './components/proveedores/NewSupplier';
import Payment from './components/pagos/Payment';
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
            <Route path= "/fileUploadGrasas" element={<UploadGrasasFile />} />
            <Route path= "/fileInformation" element={<FileInformation />} />
            <Route path= "/ver-planilla" element={<Payroll />} />
            <Route path= "/nuevo-proveedor" element={<NewSupplier />} />
            <Route path= "/calcular-pago" element={<Payment/>} />
          </Routes>
        </BrowserRouter>
  </div>
  );
}
export default App;
