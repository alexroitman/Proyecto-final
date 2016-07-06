package com.aexample.a41758511.myapplication;

import android.graphics.drawable.Drawable;

/**
 * Created by 41758511 on 28/6/2016.
 */
public class Subidas {
    String IdSubida;
    String Hora;
    Integer IdLinea;
    String UbicacionSubida;
    String UltimaUbicacion;
    String Calle;
    public Subidas(String texto,Integer img) {
        super();
        this.IdSubida =texto;
        this.IdLinea=img;
    }
    public Subidas() {
        super();

    }
    //CONSTRUCTOR 1

    public Integer getImagen() {
        return IdLinea;
    }
    public void setImagen(Integer imagen) {
        this.IdLinea = imagen;
    }
    public String getTexto() {
        return "Hay un "+IdLinea+" en "+Calle+ " a las "+Hora ;
    }

    public String getId() {
        return IdSubida;
    }
    public void setId(String id) {
        this.IdSubida = id;
    }
}
