package com.example.restclient.retrofit;

import com.example.restclient.Entity.Alumno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JsonApi {
    //Listar Alumnos
    @GET("listar")
    Call<List<Alumno>> getPost();

    //Registrar Alumnos
    @POST("add/")
    Call<Alumno> addAlumno(@Body Alumno alum);

    //Modificar Alumnos
    @PUT("update/{id}")
    Call<Alumno> updateAlumno(@Path("id") int id, @Body Alumno alum);

    //Eliminar Alumno
    @DELETE("delete/{id}")
    Call<Alumno> deleteAlumno(@Path("id") int id);
}
