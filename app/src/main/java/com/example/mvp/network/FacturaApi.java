package com.example.mvp.network;

import com.example.mvp.model.RespuestaFactura;


import retrofit2.Call;
import retrofit2.http.GET;

public interface FacturaApi {

    @GET("/facturas")
    Call<RespuestaFactura> getFacturas();
}
