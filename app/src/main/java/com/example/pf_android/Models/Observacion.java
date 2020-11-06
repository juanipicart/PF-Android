package com.example.pf_android.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Observacion {

    @SerializedName("altitud")
    @Expose
    private float altitud;
    @SerializedName("codigo")
    @Expose
    private String codigo;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("fenomeno")
    @Expose
    private String fenomeno;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("latitud")
    @Expose
    private float latitud;
    @SerializedName("localidad")
    @Expose
    private String localidad;
    @SerializedName("departamento")
    @Expose
    private String departamento;
    @SerializedName("longitud")
    @Expose
    private float longitud;
    @SerializedName("usuario")
    @Expose
    private String usuario;
    @SerializedName("imagen")
    @Expose
    private String imagen;

    public Observacion(float altitud, String codigo, String descripcion, String estado, String fecha, String fenomeno, float latitud, String localidad, String departamento, float longitud, String usuario, String imagen) {
        this.altitud = altitud;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fecha = fecha;
        this.fenomeno = fenomeno;
        this.id = id;
        this.latitud = latitud;
        this.localidad = localidad;
        this.longitud = longitud;
        this.usuario = usuario;
        this.imagen = imagen;
    }

    public Observacion(float altitud, String codigo, String descripcion, String estado, String fecha, String fenomeno, float latitud, String localidad, float longitud, String usuario, String imagen) {
        this.altitud = altitud;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fecha = fecha;
        this.fenomeno = fenomeno;
        this.latitud = latitud;
        this.localidad = localidad;
        this.longitud = longitud;
        this.usuario = usuario;
        this.imagen = imagen;
    }

    public float getAltitud() {
        return altitud;
    }

    public void setAltitud(float altitud) {
        this.altitud = altitud;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFenomeno() {
        return fenomeno;
    }

    public void setFenomeno(String fenomeno) {
        this.fenomeno = fenomeno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}