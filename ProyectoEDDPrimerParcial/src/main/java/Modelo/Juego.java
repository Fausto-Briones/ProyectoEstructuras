/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import javafx.scene.image.Image;

/**
 *
 * @author jexa1
 */
public class Juego {
    private String id;
    private String titulo;
    private String descripcion;
    private String genero;
    private String desarrolladora;
    private String anio;
    private String precio;
    private TDAArraylist<Image> images;

    public Juego(String id, String titulo, String descripcion,String genero,String desarrolladora, String anio, String precio) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.genero=genero;
        this.desarrolladora = desarrolladora;
        this.anio = anio;
        this.precio = precio;
        images=cargarImagenes(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public String getDesarrolladora() {
        return desarrolladora;
    }

    public void setDesarrolladora(String desarrolladora) {
        this.desarrolladora = desarrolladora;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public TDAArraylist<Image> getImages() {
        return images;
    }

    public void setImages(TDAArraylist<Image> images) {
        this.images = images;
    }
    private TDAArraylist<Image> cargarImagenes(String id){
        TDAArraylist<Image> result=new TDAArraylist<>();
        
        return result;
    }
    
    @Override
    public boolean equals(Object o){
        if (o == null) {
            return false;
        }

        if (o.getClass() != this.getClass()) {
            return false;
        }
        Juego j = (Juego)o;
        if ((this.titulo == null) ? (j.titulo != null) : !this.titulo.equals(j.titulo)) {
            return false;
        }
        return true;
    }
    
}
