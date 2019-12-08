package com.example.restclient.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Alumno {
    @SerializedName("idalumno")
    @Expose
    private int idalumno;
    @SerializedName("nombres")
    @Expose
    private String nombres;
    @SerializedName("apellidos")
    @Expose
    private String apellidos;
    @SerializedName("codigo")
    @Expose
    private String codigo;
    @SerializedName("correo")
    @Expose
    private String correo;

    public Alumno(int idalumno, String nombres, String apellidos, String codigo, String correo) {
        this.idalumno = idalumno;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.codigo = codigo;
        this.correo = correo;
    }

    public int getIdalumno() {
        return idalumno;
    }

    public void setIdalumno(int idalumno) {
        this.idalumno = idalumno;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
