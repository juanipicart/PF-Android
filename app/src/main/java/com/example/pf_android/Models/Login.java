package com.example.pf_android.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    public Login(String usuario, String pass) {
        this.usuario = usuario;
        this.pass = pass;
    }

    @SerializedName("usuario")
    @Expose
    private String usuario;
    @SerializedName("pass")
    @Expose
    private String pass;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "{" +
                "usuario='" + usuario + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}