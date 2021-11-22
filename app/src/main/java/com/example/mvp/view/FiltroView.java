package com.example.mvp.view;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mvp.Constantes;
import com.example.mvp.DatePickerFragment;
import com.example.mvp.MyUtil;
import com.example.mvp.R;
import com.example.mvp.databinding.ActivityFiltroBinding;
import com.example.mvp.model.WrapperFiltro;
import com.example.mvp.presenter.FiltroPresenter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

public class FiltroView extends AppCompatActivity implements com.example.mvp.interfaces.filtro.FiltroView {

    private ActivityFiltroBinding binding;
    private final FiltroPresenter presenter;

    public FiltroView() {
        this.presenter = new FiltroPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityFiltroBinding.inflate(this.getLayoutInflater());
        setContentView(binding.getRoot());
        recogerWrapperYEnviarAPresenter();
        bind();

    }

     public void recogerWrapperYEnviarAPresenter() {
        this.presenter.setWrapperToModel(getIntent());
    }


    //Se pintan los resultados mediante el wrapper pasado por parámtro
    public void pintarResultados(WrapperFiltro wrapperFiltro) {
        //SeekBar o Slider
        binding.maxSeekbar.setText(MyUtil.concatenarConEspacios(wrapperFiltro.importeMaximo.toString(), Constantes.divisa));


        binding.sliderImporte.setProgress(wrapperFiltro.importeFiltro);

        binding.valorSlider.setText(MyUtil.concatenarConEspacios(wrapperFiltro.importeFiltro.toString(), Constantes.divisa));

        binding.sliderImporte.setMax(wrapperFiltro.importeMaximo);

        if (!wrapperFiltro.fecha_desde.isEmpty()) {
            binding.fechaDesde.setText(wrapperFiltro.fecha_desde);
        } else {
            binding.fechaDesde.setText(getString(R.string.dia_mes_año));
        }

        if (!wrapperFiltro.fecha_hasta.isEmpty()) {
            binding.fechaHasta.setText(wrapperFiltro.fecha_hasta);

        } else {
            binding.fechaHasta.setText(getString(R.string.dia_mes_año));

        }
        pintarCheckBox(wrapperFiltro.estadosFiltro);

    }

    //Método encargado de bindear listener con vistas
    public void bind() {

        //TextView fecha desde
        binding.fechaDesde.setOnClickListener(listener -> mostrarDialog(R.id.fechaDesde));


        //TextView fecha hasta
        binding.fechaHasta.setOnClickListener(listener -> mostrarDialog(R.id.fechaHasta));

        //Cerrar Filtros
        binding.botonCerrarFiltro.setOnClickListener(listener -> finish());

        //Eliminar Filtros
        binding.botonEliminarFiltros.setOnClickListener(listener -> presenter.limpiarFiltros());

        //Aceptar filtros
        binding.botonFiltrar.setOnClickListener(listener -> {
            comprobarCheckBox();
            comprobarImporteSeekBar();
            setResult(1, presenter.generateIntentFromFilter());
            finish();
        });

        //Slider importe
        binding.sliderImporte.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.valorSlider.setText(MyUtil.concatenar(String.valueOf(progress), Constantes.divisa));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    //Método de comprobación del valor del SeekBar
    public void comprobarImporteSeekBar() {
        presenter.setImporteToModel(Math.max(binding.sliderImporte.getProgress(), 0));
    }

    //Método de comprobación de Checkbox
    public void comprobarCheckBox() {

        comprobarCheckboxIndividual(binding.idOpcion1, Constantes.opcion1);
        comprobarCheckboxIndividual(binding.idOpcion2, Constantes.opcion2);
        comprobarCheckboxIndividual(binding.idOpcion3, Constantes.opcion3);
        comprobarCheckboxIndividual(binding.idOpcion4, Constantes.opcion4);
        comprobarCheckboxIndividual(binding.idOpcion5, Constantes.opcion5);

    }

    public void comprobarCheckboxIndividual(CheckBox checkbox, String opcion) {
        if (checkbox.isChecked()) {
            presenter.anyadirEstadoFiltro(opcion);
        } else {
            presenter.eliminarEstadoFiltro(opcion);
        }
    }

    //Mñétodo para pintar los chekbox en checked o no checked
    public void pintarCheckBox(List<String> estados) {

        binding.idOpcion1.setChecked(estados.contains(Constantes.opcion1));

        binding.idOpcion2.setChecked(estados.contains(Constantes.opcion2));

        binding.idOpcion3.setChecked(estados.contains(Constantes.opcion3));

        binding.idOpcion4.setChecked(estados.contains(Constantes.opcion4));

        binding.idOpcion5.setChecked(estados.contains(Constantes.opcion5));
    }

    //Método para mostrar DatePicker
    public void mostrarDialog(int id) {
        DatePickerFragment datePicker = DatePickerFragment.newInstance((datepicker2, year, month, day) -> presenter.onDateSelected(day, month, year, id));

        datePicker=this.presenter.mostrarDialog(id, datePicker);

        datePicker.show(getSupportFragmentManager(), "datePicker");
    }




}