package com.example.mvp.interfaces.filtro;

import android.content.Intent;

import com.example.mvp.model.WrapperFiltro;

public interface FiltroModel {
    void setWrapperFiltro(Intent intent);
    WrapperFiltro getWrapper();
    String getWrapperParseado();
    void anyadirEstadoFiltro(String estado);
    void eliminarEstadoFiltro(String estado);
    void setImporte(int importe);
    void vaciarWrapper();
    void setFecha(String codigo, String fecha);
    String getFecha(String codigo);
    Intent generateIntentFromFilter();
}
