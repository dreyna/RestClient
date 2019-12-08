package com.example.restclient.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restclient.Entity.Alumno;
import com.example.restclient.MainActivity;
import com.example.restclient.R;
import com.example.restclient.listener.OnDeleteClickListener;
import com.example.restclient.listener.OnUpdateClickListener;
import com.example.restclient.retrofit.APIUtils;
import com.example.restclient.retrofit.JsonApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterAlumno extends RecyclerView.Adapter<AdapterAlumno.ViewHolderDatos> {

    private List<Alumno> datos;
    private Context context;
    OnDeleteClickListener onDeleteClickListener;
    OnUpdateClickListener onUpdateClickListener;
    public AdapterAlumno(List<Alumno> datos, Context context) {
        this.datos = datos;
        this.context = context;
    }
    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public void setOnUpdateClickListener(OnUpdateClickListener onUpdateClickListener) {
        this.onUpdateClickListener = onUpdateClickListener;
    }
    public void remove(int position) {
        if (position >= 0 && position < datos.size()) {
            datos.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Alumno getData(int position) {
        return datos.get(position);
    }

    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alumno,parent,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderDatos holder, final int position) {
        final Alumno alum = datos.get(position);
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
        public ImageButton btnEd;
        public ImageButton btnEl;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.tvnombre);
            tv2 = (TextView) itemView.findViewById(R.id.tvcodigo);
            tv3 = (TextView) itemView.findViewById(R.id.tvcorreo);
            btnEd = (ImageButton) itemView.findViewById(R.id.btneditar);
            btnEl = (ImageButton) itemView.findViewById(R.id.btneliminar);
            btnEd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUpdateClickListener.onUpdateClick(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
            btnEl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClickListener.onDeleteClick(getAdapterPosition());

                }
            });

        }
    }
}
