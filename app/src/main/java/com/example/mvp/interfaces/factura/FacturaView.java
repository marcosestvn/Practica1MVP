package com.example.mvp.interfaces.factura;

import com.example.mvp.model.Factura;

import java.util.List;

public interface FacturaView {
    void initRecyclerView(List<Factura> facturasAMostrar);
    void getFacturas();
}
