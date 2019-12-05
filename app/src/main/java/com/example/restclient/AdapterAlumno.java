package com.example.restclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterAlumno extends RecyclerView.Adapter<AdapterAlumno.ViewHolderDatos> {

    private List<Alumno> datos;
    private Context context;

    public AdapterAlumno(List<Alumno> datos, Context context) {
        this.datos = datos;
        this.context = context;
    }

    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alumno,parent,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        Alumno alum = datos.get(position);
        holder.tv1.setText(alum.getNombres()+" "+alum.getApellidos());
        holder.tv2.setText(alum.getCodigo());
        holder.tv3.setText(alum.getCorreo());
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.tvnombre);
            tv2 = (TextView) itemView.findViewById(R.id.tvcodigo);
            tv3 = (TextView) itemView.findViewById(R.id.tvcorreo);

        }
    }
}
