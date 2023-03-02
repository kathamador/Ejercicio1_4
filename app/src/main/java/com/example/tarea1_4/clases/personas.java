package com.example.tarea1_4.clases;

//REALIZADO POR:
//Mirian Fatima Ordoñez Amador
//Katherin Nicole Amador Maradiaga

import java.sql.Blob;
import java.io.Serializable;

public class personas implements Serializable
{
    private Integer codigo;
    private String nombres;
    private String descripcion;
    private String Imagen;
    private byte[] image;

    public personas() {}

    public personas(Integer Codigo, String nombres, String descripcion, String Imagen, byte[] image) {
        this.codigo = Codigo;
        this.nombres = nombres;
        this.descripcion = descripcion;
        this.Imagen = Imagen;
        this.image = image;
    }

    public Integer getCodigo() {
        return codigo;
    }
    public String getNombres() {
        return nombres;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getPathImage() {
        return Imagen;
    }
    public void setPathImage(String Imagen) {
        this.Imagen = Imagen;
    }
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
}