package com.valesoft.exploradordefarmacias.modelo;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalTime;

public class Farmacia {
    private String nombre;
    private String direccion;
    private String url;
    private String horario;

    public LatLng getLatlon() {
        return latlon;
    }

    public void setLatlon(LatLng latlon) {
        this.latlon = latlon;
    }

    private LatLng latlon;


    public Farmacia(String nombre, String direccion, String url, String horario,LatLng latlon) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.url = url;
        this.horario = horario;
        this.latlon = latlon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
