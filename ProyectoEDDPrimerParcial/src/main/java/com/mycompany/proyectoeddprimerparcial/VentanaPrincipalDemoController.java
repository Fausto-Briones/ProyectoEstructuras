/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyectoeddprimerparcial;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import Modelo.Juego;
import Modelo.LinkedListDobleCircular;
import Modelo.TDAArraylist;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class VentanaPrincipalDemoController implements Initializable {
    private LinkedListDobleCircular<Juego> juegos=App.cargarJuegos();
    //private LinkedListDobleCircular<Image> imgsDestacados=cargarDestacados();
    private LinkedListDobleCircular<Image> imgsCatalogo = llenarCatalogo();
    
    @FXML
    private ScrollPane root;
    @FXML
    private Label lbl_cat;
    @FXML
    private VBox vbox1;
    @FXML
    private VBox vbox2;
    @FXML
    private Button btn_cat_izq;
    @FXML
    private Button btn_cat_der;
    @FXML
    private Button btnModoOscuro;
    private boolean isModoOscuroOn;
    private TextField barra_busqueda;
    Image img_juego_actual;
    Juego juego_actual;
    /**
     * Initializes the controller class.
     */
    
    private ImageView imgv1 =  new ImageView();
    private ImageView imgv2 = new ImageView();
    private Label lbl_titulo_juego1 = new Label();
    private Label lbl_titulo_juego2= new Label();
    private Label precio1 = new Label();
    private Label precio2 = new Label();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isModoOscuroOn = true;
        //btnModoOscuro.setOnAction(e->{
          // cambiarModo();
       //});
       juego_actual = juegos.get(0);
       img_juego_actual = imgsCatalogo.get(0);
       imgv1.setImage(img_juego_actual);
       imgv1.setPreserveRatio(true);
       imgv2.setImage(imgsCatalogo.get(1));
       imgv2.setPreserveRatio(true);
       imgv1.setFitHeight(300);
       imgv2.setFitHeight(300);
       lbl_titulo_juego1.setText(juego_actual.getTitulo());
       lbl_titulo_juego1.setTextFill(Color.WHITE);
       lbl_titulo_juego2.setText(juegos.get(1).getTitulo());
       lbl_titulo_juego2.setTextFill(Color.WHITE);
       precio1.setText(juego_actual.getPrecio());
       precio1.setTextFill(Color.WHITE);
       precio2.setText(juegos.get(1).getPrecio());
       precio2.setTextFill(Color.WHITE);
       
       vbox1.getChildren().addAll(imgv1,lbl_titulo_juego1,precio1);
       vbox2.getChildren().addAll(imgv2,lbl_titulo_juego2,precio2);
       moverCatalogo();
       /*
       barra_busqueda = new TextField();
       HBox.setMargin(barra_busqueda,new Insets(20,20,20,20));
       HBox.setMargin(btnModoOscuro,new Insets(20,20,20,20));
       barra_busqueda.setPromptText("Buscar en la tienda");
       barra_busqueda.setPrefWidth(200);
       barra_busqueda.setFocusTraversable(false);
       */
    }
    
    public LinkedListDobleCircular<Image> llenarCatalogo(){
        LinkedListDobleCircular<Image> retorno=new LinkedListDobleCircular<>();
        
        for(int i=0;i<juegos.size();i++){
            Image tmp=App.getImage("Images/"+juegos.get(i).getTitulo()+".jpg");
            retorno.addLast(tmp);
        }
        
        return retorno;
        
    }
    
    public void moverCatalogo(){
        moverIzq();
        moverDer();
    }
    
    public void moverIzq(){
        btn_cat_izq.setOnAction(e->{
            img_juego_actual = imgsCatalogo.getAnterior(imgsCatalogo.getAnterior(img_juego_actual));
            imgv1.setImage(img_juego_actual);
            juego_actual = juegos.getAnterior(juegos.getAnterior(juego_actual));
            lbl_titulo_juego1.setText(juego_actual.getTitulo());
            precio1.setText(juego_actual.getPrecio());
            imgv2.setImage(imgsCatalogo.getSiguiente(img_juego_actual));
            lbl_titulo_juego2.setText(juegos.getSiguiente(juego_actual).getTitulo());
            precio2.setText(juegos.getSiguiente(juego_actual).getPrecio());
        });
    }
    
    public void moverDer(){
        btn_cat_der.setOnAction(e->{
            img_juego_actual = imgsCatalogo.getSiguiente(imgsCatalogo.getSiguiente(img_juego_actual));
            imgv1.setImage(img_juego_actual);
            juego_actual = juegos.getSiguiente(juegos.getSiguiente(juego_actual));
            lbl_titulo_juego1.setText(juego_actual.getTitulo());
            precio1.setText(juego_actual.getPrecio());
            imgv2.setImage(imgsCatalogo.getSiguiente(img_juego_actual));
            lbl_titulo_juego2.setText(juegos.getSiguiente(juego_actual).getTitulo());
            precio2.setText(juegos.getSiguiente(juego_actual).getPrecio());
        
        });
    }
    
}
