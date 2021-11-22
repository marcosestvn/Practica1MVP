package com.example.mvp.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.mvp.Constantes;
import com.example.mvp.R;
import com.example.mvp.network.FacturaApi;
import com.example.mvp.network.RetroFitNetwork;
import com.example.mvp.presenter.FacturaPresenter;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FacturaModel implements com.example.mvp.interfaces.factura.FacturaModel {

    private List<Factura> facturas = new ArrayList<>();
    private final FacturaApi retrofit = new RetroFitNetwork().getRetrofit();
    private final FacturaPresenter presenter;
    private WrapperFiltro wrapperFiltro = new WrapperFiltro();
    private final Gson converter = new Gson();

    //CONSTRUCTOR
    public FacturaModel(FacturaPresenter presenter) {
        this.presenter = presenter;
    }


    //Llamada a la API donde se recogen las facturas y se indica al presenter la inicialización del recyclerview con
    //este conjunto de datos
    public void recogerFacturas() {

        Call<RespuestaFactura> call = retrofit.getFacturas();
        presenter.mostrarLoader();

        call.enqueue(
                new Callback<RespuestaFactura>() {
                    @Override
                    public void onResponse(@NonNull Call<RespuestaFactura> call, @NonNull Response<RespuestaFactura> response) {
                        if (response.isSuccessful()) {

                            assert response.body() != null;
                            facturas = response.body().getFacturas();

                            //Para apis menores de 24 tenemos que definir la propia función de remove
                            facturas.removeIf(factura -> !filtrar(factura));

                            if(!facturas.isEmpty()){


                                getMaxImporte();

                                presenter.ocultarFacturasVacias();
                                //Se accede a la inicialización del recycler en nuestra view
                                presenter.initRecyclerView();

                            }else{
                                presenter.mostrarFacturasVacias();

                            }


                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<RespuestaFactura> call, @NonNull Throwable t) {

                        facturas = new ArrayList<>();
                    }
                }
        );


    }

    //Se realizan las funciones de filtros por cada campo a evaluar y se comparan las flags
    //si alguna flag es falsa eliminamos la factura de nuestra lista
    public Boolean filtrar(Factura facturaEvaluada) {

        Boolean flagImporte = filtrarPorImporte((int) facturaEvaluada.importeOrdenacion);
        Boolean flagEstados = filtrarPorEstados(facturaEvaluada.descEstado);
        Boolean flagFecha = filtrarFechas(facturaEvaluada.fecha);
        return (flagImporte && flagEstados && flagFecha);

    }

    //Se evalua si el estado de la factura se encuentra en los estados seleccionados del filtro
    public Boolean filtrarPorEstados(String estadoEvaluado) {
        if(wrapperFiltro.estadosFiltro.isEmpty()){
            return true;
        }
        return (wrapperFiltro.getEstadosFiltro()
                .contains(estadoEvaluado));
    }

    //Se evalua si el importe de la factura es inferior al importe seleccionado
    public Boolean filtrarPorImporte(int importeOrdenacionFacturaEvaluada) {
        if (wrapperFiltro.importeFiltro > 0) {
            return this.wrapperFiltro.getImporteFiltro() > importeOrdenacionFacturaEvaluada;
        }

        return true;
    }

    //Se evaluan los tres posibles casos, solo se tenga la fecha desde, la fecha hasta o ambas.
    public Boolean filtrarFechas(String fecha) {

        String fechaDesdeWrapper = wrapperFiltro.getFecha_desde();
        String fechaHastaWrapper = wrapperFiltro.getFecha_hasta();
        DateTimeFormatter format = DateTimeFormat.forPattern(Constantes.defaultPatternFecha);
        DateTime fechaFormateada = format.parseDateTime(fecha);

        //Ambas fechas
        if (!fechaDesdeWrapper.isEmpty() && !fechaHastaWrapper.isEmpty()) {
            DateTime fechaDesdeFormateada = format.parseDateTime(fechaDesdeWrapper);
            DateTime fechaHastaFormateada = format.parseDateTime(fechaHastaWrapper);
            return fechaFormateada.isAfter(fechaDesdeFormateada) && fechaFormateada.isBefore(fechaHastaFormateada);

            //Fecha Desde
        } else if (!fechaDesdeWrapper.isEmpty()) {

            DateTime fechaDesdeFormateada = format.parseDateTime(fechaDesdeWrapper);
            return fechaFormateada.isAfter(fechaDesdeFormateada);

            //Fecha Hasta
        } else if (!fechaHastaWrapper.isEmpty()) {
            DateTime fechaHastaFormateada = format.parseDateTime(fechaHastaWrapper);
            return fechaFormateada.isBefore(fechaHastaFormateada);
        }

        return true;
    }

    public List<Factura> getFacturas() {
        return this.facturas;
    }

    //Se devueve el Wrapper parseado a JSON
    public String getWrapperParseadoJson() {
        return converter.toJson(this.wrapperFiltro);
    }

    //Se calcula el maximo importe de las facturas obtenidas más uno ya que el importe máximo debe ser Integer pero
    //las facturas son del tipo Double.
    public void getMaxImporte() {
        for (Factura f : facturas
        ) {
            if (this.wrapperFiltro.importeMaximo < f.importeOrdenacion)
                this.wrapperFiltro.importeMaximo = (int) f.importeOrdenacion + 1;
        }
    }

    //Se define nuevo wrapper
    public void setWrapperJson(String wrapperJson) {
        this.wrapperFiltro = converter.fromJson(wrapperJson, WrapperFiltro.class);
    }


    //Lógica para generar y mostrar Dialog en View
    public void crearYMostrarDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.titutloDialogFactura);
        builder.setMessage(R.string.cuerpoDialogFactura);
        builder.setPositiveButton(R.string.neutralButtonDialogFactura, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
