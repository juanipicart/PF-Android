package com.example.pf_android.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Observacion {

    //Atributos
    private String codigo;
    private String descripcion;
    private String fenomeno;
    private String departmanto;
    private String localidad;
    private String zona;
    private String longitud;
    private String latitud;
    private String altitud;
    private String date;

    public Observacion(String codigo, String descripcion, String fenomeno, String departamento, String localidad, String zona, String longitud, String latitud, String altitud, String fecha) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fenomeno = fenomeno;
        this.departmanto = departamento;
        this.localidad = localidad;
        this.zona = zona;
        this.longitud = longitud;
        this.latitud = latitud;
        this.altitud = altitud;
        this.date = fecha;
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

    public String getFenomeno() {
        return fenomeno;
    }

    public void setFenomeno(String fenomeno) {
        this.fenomeno = fenomeno;
    }

    public String getDepartmanto() {
        return departmanto;
    }

    public void setDepartmanto(String departmanto) {
        this.departmanto = departmanto;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getAltitud() {
        return altitud;
    }

    public void setAltitud(String altitud) {
        this.altitud = altitud;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Observacion{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fenomeno='" + fenomeno + '\'' +
                ", departmanto='" + departmanto + '\'' +
                ", localidad='" + localidad + '\'' +
                ", zona='" + zona + '\'' +
                ", longitud='" + longitud + '\'' +
                ", latitud='" + latitud + '\'' +
                ", altitud='" + altitud + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

}