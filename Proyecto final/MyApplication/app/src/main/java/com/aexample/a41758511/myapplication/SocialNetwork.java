package com.aexample.a41758511.myapplication;

/**
 * Created by Alex on 09/05/2016.
 */
public class SocialNetwork {
    private String name;

    private int icon;

    public SocialNetwork(String nombre, int icono)
    {
        super();
        this.name = nombre;
        this.icon = icono;
    }

    public String getNombre()
    {
        return name;
    }

    public void setNombre(String nombre)
    {
        this.name = nombre;
    }

    public int getIcono()
    {
        return icon;
    }

    public void setIcono(int icono)
    {
        this.icon = icono;
    }
}
