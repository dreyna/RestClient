package com.example.restclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restclient.Entity.Alumno;
import com.example.restclient.R;

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
        final Alumno alum = datos.get(position);
        holder.tv1.setText(alum.getNombres()+" "+alum.getApellidos());
        holder.tv2.setText(alum.getCodigo());
        holder.tv3.setText(alum.getCorreo());
        holder.btnEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Editar"+alum.getIdalumno(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnEl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"ELiminar"+alum.getIdalumno(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
        public ImageButton btnEd;
        public ImageButton btnEl;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.tvnombre);
            tv2 = (TextView) itemView.findViewById(R.id.tvcodigo);
            tv3 = (TextView) itemView.findViewById(R.id.tvcorreo);
            btnEd = (ImageButton) itemView.findViewById(R.id.btneditar);
            btnEl = (ImageButton) itemView.findViewById(R.id.btneliminar);

        }
    }
}
