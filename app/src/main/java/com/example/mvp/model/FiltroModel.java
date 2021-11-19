package com.example.mvp.model;

import android.content.Intent;

import com.example.mvp.Constantes;
import com.example.mvp.presenter.FiltroPresenter;
import com.google.gson.Gson;

public class FiltroModel implements com.example.mvp.interfaces.filtro.FiltroModel {

    private final FiltroPresenter presenter;
    private WrapperFiltro wrapperFiltro;
    final Gson converter = new Gson();


    public FiltroModel(FiltroPresenter presenter) {
        this.presenter = presenter;
    }

    public void setWrapperFiltro(Intent intent) {
        this.wrapperFiltro = converter.fromJson(intent.getStringExtra("wrapperMain"), WrapperFiltro.class);
        this.presenter.pintarFiltros();
    }

    public WrapperFiltro getWrapper() {
        return this.wrapperFiltro;
    }

    public String getWrapperParseado() {
        return converter.toJson(wrapperFiltro);
    }

    public void anyadirEstadoFiltro(String estado) {
        this.wrapperFiltro.estadosFiltro.add(estado);
    }

    public void eliminarEstadoFiltro(String estado) {
        this.wrapperFiltro.estadosFiltro.remove(estado);
    }

    public void setImporte(int importe) {
        this.wrapperFiltro.importeFiltro = importe;
    }

    public void vaciarWrapper() {
        int importeMaximoTemp = wrapperFiltro.importeMaximo;
        this.wrapperFiltro = new WrapperFiltro();
        this.wrapperFiltro.setImporteMaximo(importeMaximoTemp);
        this.presenter.pintarFiltros();
    }

    public void setFecha(String codigo, String fecha) {

        if (codigo.equals(Constantes.clave_1)) {
            this.wrapperFiltro.fecha_desde = fecha;
        }

        if (codigo.equals(Constantes.clave_2)) {
            this.wrapperFiltro.fecha_hasta = fecha;
        }
        this.presenter.pintarFiltros();

    }

    public String getFecha(String codigo) {
        if (codigo.equals(Constantes.clave_1)) {
            return this.wrapperFiltro.fecha_desde;
        }

        if (codigo.equals(Constantes.clave_2)) {
            return this.wrapperFiltro.fecha_hasta;

        }
        return null;
    }



    public Intent generateIntentFromFilter() {
        Intent intentConWrapper = new Intent();
        intentConWrapper.putExtra(Constantes.wrapperFiltro,getWrapperParseado());
        return intentConWrapper;
    }
}
