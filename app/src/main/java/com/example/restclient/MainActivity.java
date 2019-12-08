package com.example.restclient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restclient.Entity.Alumno;
import com.example.restclient.adapter.AdapterAlumno;
import com.example.restclient.listener.OnDeleteClickListener;
import com.example.restclient.listener.OnUpdateClickListener;
import com.example.restclient.retrofit.APIUtils;
import com.example.restclient.retrofit.JsonApi;
import com.example.restclient.retrofit.Urls;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Tag;

public class MainActivity extends AppCompatActivity implements Urls, OnUpdateClickListener, OnDeleteClickListener {
    private RecyclerView recyclerView;
    private AdapterAlumno adapter;
    private JsonApi jsonApi;
    private List<Alumno> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jsonApi = APIUtils.getAlumnoService();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        lista = new ArrayList<>();
        listarAlumnos();
    }
//Listar alumnos
    private void listarAlumnos() {
        Call<List<Alumno>> call = jsonApi.getAlumnos();
        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                List<Alumno> lista = response.body();
                adapter = new AdapterAlumno(lista, MainActivity.this);
                recyclerView.setAdapter(adapter);
                initListener();
            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {

            }
        });
    }

    private void doDelete(final int position, int id) {
        Call<Alumno> call = jsonApi.deleteAlumno(id);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if (response.code() == 200)
                    adapter.remove(position);
                listarAlumnos();
            }

            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                Log.e(".errorDelete", t.toString());
            }
        });
    }

    @Override
    public void onDeleteClick(final int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Your title");
        alertDialog.setMessage("Desea eliminar...? ");
        alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doDelete(position, adapter.getData(position).getIdalumno());
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();

    }
//mostrar datos del alumno a modificar en el formulario
    @Override
    public void onUpdateClick(final int position) {
        final Alumno alum = adapter.getData(position);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.modificar_alumno, null);
        final EditText mNombres = (EditText) view.findViewById(R.id.edtnombres);
        final EditText mApellidos = (EditText) view.findViewById(R.id.edtapellidos);
        final EditText mCodigo = (EditText) view.findViewById(R.id.edtcodigo);
        final EditText mCorreo = (EditText) view.findViewById(R.id.edtcorreo);
        Button btnModificar = (Button) view.findViewById(R.id.btnaceptar);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        mNombres.setText(alum.getNombres());
        mApellidos.setText(alum.getApellidos());
        mCodigo.setText(alum.getCodigo());
        mCorreo.setText(alum.getCorreo());
        alertDialog.setView(view);

        final AlertDialog dialog = alertDialog.create();
        dialog.show();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alumno a = new Alumno(alum.getIdalumno(),
                        mNombres.getText().toString(),
                        mApellidos.getText().toString(),
                        mCodigo.getText().toString(),
                        mCorreo.getText().toString());
                dialog.dismiss();
                updateData(a);
                listarAlumnos();
            }
        });

    }

    private void initListener() {
        adapter.setOnDeleteClickListener(this);
        adapter.setOnUpdateClickListener(this);
    }
//actualizar datos de Alumno
    private void updateData(Alumno alum) {
        int id = alum.getIdalumno();
        Call<Alumno> call = jsonApi.updateAlumno(id, alum);

        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if (response.code() == 200) {
                    listarAlumnos();
                }else{
                    Log.e(".mal", response.toString());
                }
            }

            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                Log.e(".error", t.toString());
            }
        });
    }
//metodos del menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                add();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //mostrar dialog con el formulario para registrar un nuevo alumno
    private void add(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.modificar_alumno, null);
        final EditText mNombres = (EditText) view.findViewById(R.id.edtnombres);
        final EditText mApellidos = (EditText) view.findViewById(R.id.edtapellidos);
        final EditText mCodigo = (EditText) view.findViewById(R.id.edtcodigo);
        final EditText mCorreo = (EditText) view.findViewById(R.id.edtcorreo);
        TextView mtitulo = (TextView) view.findViewById(R.id.textView);
        Button btnaceptar = (Button) view.findViewById(R.id.btnaceptar);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        mtitulo.setText("Registrar Alumno");
        alertDialog.setView(view);

        final AlertDialog dialog = alertDialog.create();
        dialog.show();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alumno alumno =new Alumno(
                        0,mNombres.getText().toString(),
                        mApellidos.getText().toString(),
                        mCodigo.getText().toString(),
                        mCorreo.getText().toString());
                dialog.dismiss();
                addAlumno(alumno);
            }
        });
    }
//Registrar alumno en la base de datos mysql
    private void addAlumno(Alumno alum){
        Call<Alumno> call = jsonApi.addAlumno(alum);

        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if(response.code() == 200) {
                    listarAlumnos();
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                 }
            }

            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                Log.e(".error", t.toString());
            }
        });
    }
}
