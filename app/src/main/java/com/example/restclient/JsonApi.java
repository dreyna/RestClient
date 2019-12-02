package com.example.restclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonApi {
    @GET("listar")
    Call<List<Alumno>> getPost();
}
