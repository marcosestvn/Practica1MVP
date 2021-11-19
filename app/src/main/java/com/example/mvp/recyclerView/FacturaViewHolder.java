package com.example.mvp.recyclerView;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvp.Constantes;
import com.example.mvp.MyUtil;
import com.example.mvp.databinding.RowLayoutBindingImpl;
import com.example.mvp.model.Factura;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

class FacturaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    View view;
    RowLayoutBindingImpl binding;
    StringBuilder stringBuilder= new StringBuilder();
    FacturaAdapter.OnFacturaListener onFacturaListener;
    DateTimeFormatter dia = DateTimeFormat.forPattern(Constantes.diaPattern);
    DateTimeFormatter mes = DateTimeFormat.forPattern(Constantes.tresPrimerasLetrasMesPattern);
    DateTimeFormatter anyo = DateTimeFormat.forPattern(Constantes.anyoPattern);

    public FacturaViewHolder(@NonNull View itemView, FacturaAdapter.OnFacturaListener facturaClickListener) {
        super(itemView);
        this.view=itemView;
        this.binding= (RowLayoutBindingImpl) RowLayoutBindingImpl.bind(view);
        this.onFacturaListener=facturaClickListener;
    }

    public void bind(Factura factura) {
        Locale local = new Locale(Constantes.idioma, Constantes.pais);
        Locale.setDefault(local);
        DateTime fechaFormateada = DateTimeFormat.forPattern(Constantes.defaultPatternFecha).parseDateTime(factura.fecha);

        binding.estadoFactura.setText(factura.descEstado);


        if(factura.descEstado.equals(Constantes.opcion1)){
            binding.estadoFactura.setVisibility(View.GONE);
        }

        binding.fecha.setText(MyUtil.concatenarConEspacios(dia.print(fechaFormateada),MyUtil.primeraLetraToUpperCase(mes.print(fechaFormateada)), anyo.print(fechaFormateada)));

        binding.importeFactura.setText(stringBuilder.append(factura.importeOrdenacion).append(" €"));
        binding.factura.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        onFacturaListener.onFacturaClick();
    }
}


