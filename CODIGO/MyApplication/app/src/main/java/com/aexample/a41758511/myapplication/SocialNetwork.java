package com.aexample.a41758511.myapplication;

/**
 * Created by Alex on 09/05/2016.
 */
public class SocialNetwork {
    private String name;

    private Integer icon;

    public SocialNetwork(String nombre, Integer icono)
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

    public Integer getIcono()
    {
        return icon;
    }

    public void setIcono(Integer icono)
    {
        this.icon = icono;
    }
}
