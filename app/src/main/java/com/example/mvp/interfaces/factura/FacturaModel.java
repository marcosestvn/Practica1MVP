package com.example.mvp.interfaces.factura;

import android.content.Context;

import com.example.mvp.model.Factura;

import java.util.List;

public interface FacturaModel {

    void recogerFacturas();
    Boolean filtrar(Factura facturaEvaluada);
    Boolean filtrarPorImporte(int ImporteEvaluado);
    Boolean filtrarPorEstados(String estadoEvaluado);
    Boolean filtrarFechas(String fechaEvaluada);
    List<Factura> getFacturas();
    void getMaxImporte();
    void setWrapperJson(String wrapperJson);
    String getWrapperParseadoJson();
    void crearYMostrarDialog(Context context);
}
