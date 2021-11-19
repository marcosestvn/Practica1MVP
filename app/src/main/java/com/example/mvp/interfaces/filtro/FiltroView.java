package com.example.mvp.interfaces.filtro;

import android.widget.CheckBox;

import com.example.mvp.model.WrapperFiltro;

import java.util.List;

public interface FiltroView {

    void recogerWrapperYEnviarAPresenter();
    void pintarResultados(WrapperFiltro wrapperFiltro);
    void bind();
    void comprobarImporteSeekBar();
    void comprobarCheckBox();
    void comprobarCheckboxIndividual(CheckBox checkbox, String opcion);
    void pintarCheckBox(List<String> estados);
    void mostrarDialog(int id);

}
