package com.example.mvp.presenter;

import android.content.Intent;

import com.example.mvp.DatePickerFragment;
import com.example.mvp.model.FiltroModel;
import com.example.mvp.model.WrapperFiltro;
import com.example.mvp.view.FiltroView;

public class FiltroPresenter implements com.example.mvp.interfaces.filtro.FiltroPresenter {

    private FiltroView view;
    private final FiltroModel model = new FiltroModel(this);

    public FiltroPresenter(FiltroView view) {
        if(view != null){
            this.view=view;
        }
    }

    //Se envía intent con wrapper a método modelo
    public void setWrapperToModel(Intent intent){
        if(view !=null){
            this.model.setWrapperFiltro(intent);
        }

    }

    //Se devuelve el wrapper del modelo
    public WrapperFiltro getWrapper(){
        if(view != null){
            return model.getWrapper();
        }else{
            return null;
        }
    }

    //Se accede al método del modelo para añdir el estado seleccionado
    public void anyadirEstadoFiltro(String estado){
        if(view != null){
            this.model.anyadirEstadoFiltro( estado);
        }
    }

    //Se accede al método del modelo para eliminar el estado seleccionado
    public void eliminarEstadoFiltro(String estado) {
        this.model.eliminarEstadoFiltro(estado);
    }

    //Devuelve intent generado en el modelo con datos del filtrado
    public Intent generateIntentFromFilter() {
        return this.model.generateIntentFromFilter();
    }

    //Se setea en el modelo el importe seleccionado para filtrar
    public void setImporteToModel(int importe) {
        this.model.setImporte(importe);
    }

    //Se accede al método del modelo para hacer un borrado de los datos acutales del filtrado
    public void limpiarFiltros() {
        this.model.vaciarWrapper();
    }

    //Método que indica a la vista con que datos y cuando pintar en el XML
    public void pintarFiltros(){
        this.view.pintarResultados(getWrapper());
    }


    //Se obtiene la fecha, hasta o después, según el código pasado por parámetro
    public String getFecha(String codigo){
        return this.model.getFecha(codigo);
    }

    //Se accede al método del modelo para setear la fecha, hasta o después, según que
    //código se indique
    public void setFecha(String codigo, String fecha){
        this.model.setFecha(codigo,fecha);
    }


    public DatePickerFragment mostrarDialog(int id, DatePickerFragment datePicker) {
        return this.model.mostrarDialog(id, datePicker);
    }

    public void onDateSelected(int day, int month, int year, int id) {
        model.onDateSelected(day,month,year,id);
    }
}
