package com.example.mvp.interfaces.factura;

import android.content.Context;

public interface FacturaPresenter {

    void initRecyclerView();
    void crearYMostrarDialog(Context context);
    void getFacturas();
    String getWrapperParseadoJSON();
    void setWrapperJson(String wrapperJson);

}
