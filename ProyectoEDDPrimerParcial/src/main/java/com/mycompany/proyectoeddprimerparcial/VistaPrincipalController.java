/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyectoeddprimerparcial;

import Modelo.Juego;
import Modelo.LinkedListDobleCircular;
import Modelo.TDAArraylist;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class VistaPrincipalController implements Initializable {
    private TDAArraylist<Juego> juegos=App.cargarJuegos();
    private LinkedListDobleCircular<Image> imgsDestacados=cargarDestacados();
    @FXML
    private VBox root;
    @FXML
    private HBox cabecera;
    @FXML
    private HBox barraBusqueda;
    @FXML
    private HBox carrusel;
    @FXML
    private ImageView imgvDestacado;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       imgvDestacado.setImage(imgsDestacados.get(0));
        
    }
    
    public LinkedListDobleCircular<Image> cargarDestacados(){
        LinkedListDobleCircular<Image> retorno=new LinkedListDobleCircular<>();
        for(int i=0;i<4;i++){
            Image tmp=App.getImage("Images/"+juegos.get(i).getTitulo()+".jpg");
            retorno.addLast(tmp);
        }
        return retorno;
    }
    public void iniciarCarrusel(){
        
    }
}
