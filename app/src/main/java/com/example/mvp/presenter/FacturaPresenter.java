package com.example.mvp.presenter;

import android.content.Context;

import com.example.mvp.model.FacturaModel;
import com.example.mvp.view.FacturaView;

public class FacturaPresenter implements com.example.mvp.interfaces.factura.FacturaPresenter {

    private FacturaView view;
    public FacturaModel model = new FacturaModel(this);

    //CONSTRUCTOR
    public FacturaPresenter(FacturaView view) {
        if (view != null) {
            this.view = view;
        }
    }

    //Se le indica a la vista la inicialización del recyclerView con una lista de datos pasada por
    //parámetro
    public void initRecyclerView() {
        if(this.view != null && this.model != null){
            view.initRecyclerView(model.getFacturas());
        }
    }



    //Se muestra, mediante el método del modelo, el dialog en la view
    public void crearYMostrarDialog(Context context) {
        if (this.model != null) {
            this.model.crearYMostrarDialog(context);
        }
    }

    //Accedemos al modelo para recoger las factursa de la api
    public void getFacturas() {
        if(this.model!= null){
            model.recogerFacturas();
        }
    }

    //Se recoge Wrapper almacenado en el modelo para pintar los datos en la view
    public String getWrapperParseadoJSON() {
        if(this.model != null){
            return model.getWrapperParseadoJson();
        }
        return null;
    }

    //Se accede al método del modelo para setear un nuevo wrapper en formato JSON
    public void setWrapperJson(String wrapperNoParsed) {
        if(this.model != null){
            this.model.setWrapperJson(wrapperNoParsed);
            this.getFacturas();
        }
    }
    public void mostrarFacturasVacias(){
        this.view.mostrarFacturasVacias();
    }

    public void ocultarFacturasVacias(){
        this.view.ocultarFacturasVacias();
    }

    public void mostrarLoader(){
        this.view.mostrarLoader();
    }
}
