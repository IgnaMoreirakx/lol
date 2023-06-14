import React from 'react';
import ListSuppliers from './components/proveedores/ListSuppliers';
import HomeComponent from './components/HomeComponent';
import { BrowserRouter, Route, Routes } from "react-router-dom";

function App() {
  return (
    <div>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<HomeComponent />} />
            <Route path= "/ver-proveedores" element={<ListSuppliers />} />
          </Routes>
        </BrowserRouter>
  </div>
  );
}
export default App;
