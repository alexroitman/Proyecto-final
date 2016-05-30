package com.aexample.a41758511.myapplication;

/**
 * Created by Alex on 09/05/2016.
 */
public class SocialNetwork {
    private Integer name;

    private String icon;

    public SocialNetwork(Integer nombre, String icono)
    {
        super();
        this.name = nombre;
        this.icon = icono;
    }

    public Integer getNombre()

    {
return name;
    }

    public void setNombre(Integer nombre)
    {
        this.name = nombre;
    }

    public String getIcono()
    {
        return icon;
    }

    public void setIcono(String icono)
    {
        this.icon = icono;
    }
}
