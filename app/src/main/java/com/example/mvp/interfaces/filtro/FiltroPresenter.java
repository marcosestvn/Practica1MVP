package com.example.mvp.interfaces.filtro;

import android.content.Intent;

import com.example.mvp.model.WrapperFiltro;

public interface FiltroPresenter {
    void setWrapperToModel(Intent intent);
    WrapperFiltro getWrapper();
    void anyadirEstadoFiltro(String estado);
    void eliminarEstadoFiltro(String estado);
    Intent generateIntentFromFilter();
    void setImporteToModel(int importe);
    void limpiarFiltros();
    void pintarFiltros();
    String getFecha(String codigo);
    void setFecha(String codigo, String fecha);
}
