package com.example.restclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.restclient.Entity.Alumno;
import com.example.restclient.adapter.AdapterAlumno;
import com.example.restclient.retrofit.APIUtils;
import com.example.restclient.retrofit.JsonApi;
import com.example.restclient.retrofit.Urls;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Urls {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private JsonApi jsonApi;
    private List<Alumno> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jsonApi = APIUtils.getAlumnoService();
        recyclerView =(RecyclerView)findViewById(R.id.recyclerid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lista = new ArrayList<>();
        getPosts();
    }
    private void getPosts(){
        Call<List<Alumno>> call = jsonApi.getPost();
        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                if(!response.isSuccessful()){
                    return;
                }
                List<Alumno> lista = response.body();
                adapter = new AdapterAlumno(lista,getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {

            }
        });
    }
}
