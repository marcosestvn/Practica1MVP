package com.example.mvp.model;

import android.content.Intent;

import com.example.mvp.Constantes;
import com.example.mvp.DatePickerFragment;
import com.example.mvp.MyUtil;
import com.example.mvp.R;
import com.example.mvp.presenter.FiltroPresenter;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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

    public DatePickerFragment mostrarDialog(int id, DatePickerFragment datePicker) {
        String fechaDesdeTemp = getFecha(Constantes.clave_1);
        String fechaHastaTemp = getFecha(Constantes.clave_2);
        if (id == R.id.fechaDesde) {


            if (!(fechaHastaTemp.isEmpty())) {
                datePicker.setMaxDate2(fechaHastaTemp);
            }

            if (!fechaDesdeTemp.isEmpty()) {
                datePicker.setCurrentDate(fechaDesdeTemp);
            }
        }

        if (id == R.id.fechaHasta) {

            if (!(fechaDesdeTemp.isEmpty())) {
                datePicker.setMinDate2(fechaDesdeTemp);
            }

            if (!fechaHastaTemp.isEmpty()) {
                datePicker.setCurrentDate(fechaHastaTemp);
            }
        }

        return datePicker;
    }

    public void onDateSelected(Integer day, Integer month, Integer year, int id) {
        DateTimeFormatter ff = DateTimeFormat.forPattern(Constantes.defaultPatternFecha);


        month++;

        DateTime fecha = DateTimeFormat.forPattern(Constantes.defaultPatternFecha)
                .parseDateTime(MyUtil.concatenar((day.toString()), "/", ((month).toString()), "/", (year.toString())));


        if (id == R.id.fechaHasta) {
            setFecha(Constantes.clave_2, ff.print(fecha));
        }

        if (id == R.id.fechaDesde) {
            setFecha(Constantes.clave_1, ff.print(fecha));
        }
    }
}
