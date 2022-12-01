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
import javafx.application.Platform;
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
    private VBox vbox3;
    @FXML
    private VBox vbox4;
    @FXML
    private VBox vbox5;
    @FXML
    private Button btn_cat_izq;
    @FXML
    private Button btn_cat_der;
    @FXML
    private Button btnModoOscuro;
    @FXML
    private ImageView imgvDestacados;
    
    private boolean isModoOscuroOn;
    private TextField barra_busqueda;
    Image img_juego_actual;
    Juego juego_actual;
    Image imagenDestacada_actual;
    LinkedListDobleCircular<Image> imgsDestacados;
    /**
     * Initializes the controller class.
     */
    
    private ImageView imgv1 =  new ImageView();
    private ImageView imgv2 = new ImageView();
    private Label lbl_titulo_juego1 = new Label();
    private Label lbl_titulo_juego2= new Label();
    private Label precio1 = new Label();
    private Label precio2 = new Label();
    private ImageView imgv3 =  new ImageView();
    private ImageView imgv4 = new ImageView();
    private Label lbl_titulo_juego3 = new Label();
    private Label lbl_titulo_juego4= new Label();
    private Label precio3 = new Label();
    private Label precio4 = new Label();
    private ImageView imgv5 =  new ImageView();
    private Label lbl_titulo_juego5 = new Label();
    private Label precio5 = new Label();
    
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
       imgv3.setImage(imgsCatalogo.get(2));
       imgv3.setPreserveRatio(true);
       imgv4.setImage(imgsCatalogo.get(3));
       imgv4.setPreserveRatio(true);
       imgv3.setFitHeight(300);
       imgv4.setFitHeight(300);
       imgv5.setImage(imgsCatalogo.get(4));
       imgv5.setPreserveRatio(true);
       imgv5.setFitHeight(300);
       lbl_titulo_juego1.setText(juego_actual.getTitulo());
       lbl_titulo_juego1.setTextFill(Color.WHITE);
       lbl_titulo_juego2.setText(juegos.get(1).getTitulo());
       lbl_titulo_juego2.setTextFill(Color.WHITE);
       precio1.setText(juego_actual.getPrecio());
       precio1.setTextFill(Color.WHITE);
       precio2.setText(juegos.get(1).getPrecio());
       precio2.setTextFill(Color.WHITE);
       
       lbl_titulo_juego3.setText(juegos.get(2).getTitulo());
       lbl_titulo_juego3.setTextFill(Color.WHITE);
       lbl_titulo_juego4.setText(juegos.get(3).getTitulo());
       lbl_titulo_juego4.setTextFill(Color.WHITE);
       precio3.setText(juegos.get(2).getPrecio());
       precio3.setTextFill(Color.WHITE);
       precio4.setText(juegos.get(3).getPrecio());
       precio4.setTextFill(Color.WHITE);
       
       lbl_titulo_juego5.setText(juegos.get(4).getTitulo());
       lbl_titulo_juego5.setTextFill(Color.WHITE);
       precio5.setText(juegos.get(4).getPrecio());
       precio5.setTextFill(Color.WHITE);
       
       vbox1.getChildren().addAll(imgv1,lbl_titulo_juego1,precio1);
       vbox2.getChildren().addAll(imgv2,lbl_titulo_juego2,precio2);
       vbox3.getChildren().addAll(imgv3,lbl_titulo_juego3,precio3);
       vbox4.getChildren().addAll(imgv4,lbl_titulo_juego4,precio4);
       vbox5.getChildren().addAll(imgv5,lbl_titulo_juego5,precio5);
       
       moverCatalogo();
       imgsDestacados=agregarDestacados();
       //cleanList();
       imagenDestacada_actual = imgsDestacados.get(0);
       imgvDestacados.setImage(imgsDestacados.get(0));
       moverDestacados();
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
            for(int i=0;i<5;i++){
                img_juego_actual = imgsCatalogo.getAnterior(img_juego_actual);
                juego_actual = juegos.getAnterior(juego_actual);
            }
            
            
            imgv1.setImage(img_juego_actual);
            lbl_titulo_juego1.setText(juego_actual.getTitulo());
            precio1.setText(juego_actual.getPrecio());
            
            Image img_juego2 = imgsCatalogo.getSiguiente(img_juego_actual);
            Juego juego2 = juegos.getSiguiente(juego_actual);
            imgv2.setImage(img_juego2);
            lbl_titulo_juego2.setText(juego2.getTitulo());
            precio2.setText(juego2.getPrecio());
            
            Image img_juego3 = imgsCatalogo.getSiguiente(img_juego2);
            Juego juego3 = juegos.getSiguiente(juego2);
            imgv3.setImage(img_juego3);
            lbl_titulo_juego3.setText(juego3.getTitulo());
            precio3.setText(juego3.getPrecio());
            
            Image img_juego4 = imgsCatalogo.getSiguiente(img_juego3);
            Juego juego4 = juegos.getSiguiente(juego3);
            imgv4.setImage(img_juego4);
            lbl_titulo_juego4.setText(juego4.getTitulo());
            precio4.setText(juego4.getPrecio());
            
            Image img_juego5 = imgsCatalogo.getSiguiente(img_juego4);
            Juego juego5 = juegos.getSiguiente(juego4);
            imgv5.setImage(img_juego5);
            lbl_titulo_juego5.setText(juego5.getTitulo());
            precio5.setText(juego5.getPrecio());
            
            
        });
    }
    
    public void moverDer(){
        btn_cat_der.setOnAction(e->{
            
            for(int i=0;i<5;i++){
                img_juego_actual = imgsCatalogo.getSiguiente(img_juego_actual);
                juego_actual = juegos.getSiguiente(juego_actual);
            }
            
            
            
            imgv1.setImage(img_juego_actual);
            lbl_titulo_juego1.setText(juego_actual.getTitulo());
            precio1.setText(juego_actual.getPrecio());
            
            Image img_juego2 = imgsCatalogo.getSiguiente(img_juego_actual);
            Juego juego2 = juegos.getSiguiente(juego_actual);
            imgv2.setImage(img_juego2);
            lbl_titulo_juego2.setText(juego2.getTitulo());
            precio2.setText(juego2.getPrecio());
            
            Image img_juego3 = imgsCatalogo.getSiguiente(img_juego2);
            Juego juego3 = juegos.getSiguiente(juego2);
            imgv3.setImage(img_juego3);
            lbl_titulo_juego3.setText(juego3.getTitulo());
            precio3.setText(juego3.getPrecio());
            
            Image img_juego4 = imgsCatalogo.getSiguiente(img_juego3);
            Juego juego4 = juegos.getSiguiente(juego3);
            imgv4.setImage(img_juego4);
            lbl_titulo_juego4.setText(juego4.getTitulo());
            precio4.setText(juego4.getPrecio());
            
            Image img_juego5 = imgsCatalogo.getSiguiente(img_juego4);
            Juego juego5 = juegos.getSiguiente(juego4);
            imgv5.setImage(img_juego5);
            lbl_titulo_juego5.setText(juego5.getTitulo());
            precio5.setText(juego5.getPrecio());
        
        });
    }
    
    public LinkedListDobleCircular<Image> agregarDestacados(){
        LinkedListDobleCircular<Image> lista_Destacados = new LinkedListDobleCircular();
        for(int i=0;i<juegos.size();i++){
            Image tmp=App.getImage("Images/Destacados/"+juegos.get(i).getTitulo()+".jpg",true);
            if(tmp!=null){
            
            lista_Destacados.addLast(tmp);
            }
        }
        return lista_Destacados;
        
    }
    
    public void moverDestacados(){
        Thread hilo1 = new Thread(new Runnable() {
            @Override
            public void run() {
                    while(true){
                        
                        Platform.runLater(()->{
                        
                            imagenDestacada_actual = imgsDestacados.getSiguiente(imagenDestacada_actual);
                            imgvDestacados.setImage(imagenDestacada_actual);
                        
                        });
                        try{
                            Thread.sleep(2000);
                        }catch(InterruptedException ex){
                        }
                    }
                
            }
        });
        hilo1.start();
        
    }
    
}
