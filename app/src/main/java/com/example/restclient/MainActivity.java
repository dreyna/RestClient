package com.example.restclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView tv01, tv02, tv03;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv01 = (TextView) findViewById(R.id.tvnombre);
        tv02 = (TextView) findViewById(R.id.tvcodigo);
        tv03 = (TextView) findViewById(R.id.tvcorreo);
        getPosts();
    }
    private void getPosts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.74.17:4000/alumno/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        Call<List<Alumno>> call = jsonApi.getPost();
        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                if(!response.isSuccessful()){
                    tv01.setText("Codigo: "+response.code());
                    return;
                }
                List<Alumno> postsList = response.body();
                for(Alumno alum: postsList){
                    String nombres = "Nombres: "+alum.getNombres()+" "+alum.getApellidos();
                    String codigo = "Código: "+alum.getCodigo();
                    String correo ="Correo: "+alum.getCorreo();
                    tv01.append(nombres);
                    tv02.append(codigo);
                    tv03.append(correo);
                }
            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {
                tv01.setText(t.getMessage());
            }
        });
    }
}
