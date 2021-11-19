package com.example.mvp.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvp.Constantes;
import com.example.mvp.R;
import com.example.mvp.databinding.ActivityMainBinding;
import com.example.mvp.model.Factura;
import com.example.mvp.presenter.FacturaPresenter;
import com.example.mvp.recyclerView.FacturaAdapter;

import java.util.List;

public class FacturaView extends AppCompatActivity implements com.example.mvp.interfaces.factura.FacturaView,FacturaAdapter.OnFacturaListener {

    private final FacturaPresenter presenter = new FacturaPresenter(this);
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        prepararYBindeoActionBar();
        setContentView(binding.getRoot());
        getFacturas();

    }

    //Inicialización recyclerView con lista de facturas pasada por parámetro
    @SuppressLint("NotifyDataSetChanged")
    public void initRecyclerView(List<Factura> f3) {

        ocultarLoader();

        RecyclerView recyclerView = findViewById(R.id.recycler_facturas);
        FacturaAdapter adapter = new FacturaAdapter(f3, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this,
                        DividerItemDecoration.VERTICAL)
        );

        adapter.notifyDataSetChanged();

    }

    //Obtener facturas de nuestro modelo a través del método getFacturas() del presenter
    public void getFacturas() {
        presenter.getFacturas();
    }

    //Inicialización de Toolbar
    private void prepararYBindeoActionBar() {
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
    }

    //Inicialización del menu del toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        binding.toolbar.setNavigationOnClickListener(v -> finish());
        return super.onCreateOptionsMenu(menu);
    }

    //Listener de opciones del menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.icono_filtro) {

            int LAUNCH_SECOND_ACTIVITY = 1;
            Intent i = new Intent(this, FiltroView.class);
            i.putExtra(Constantes.wrapperMain, presenter.getWrapperParseadoJSON());
            startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 1) {
            assert data != null;
            this.presenter.setWrapperJson(data.getStringExtra(Constantes.wrapperFiltro));
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //Listener para mostrar Dialog en cada factura
    @Override
    public void onFacturaClick() {
        presenter.crearYMostrarDialog(this);
    }

    private void ocultarLoader() {
        binding.progressBar.setVisibility(View.GONE);
        binding.contenido.setVisibility(View.VISIBLE);
    }
}