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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;

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
    @FXML
    private Button btn_desc_izq;
    @FXML
    private Button btn_desc_der;
    
    private TextField barra_busqueda;
    
    @FXML
    private ScrollPane cataPane;
    
    Image img_juego_actual;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cleanList();
       llenarCatalogo();
       img_juego_actual = imgsDestacados.get(0);
       imgvDestacado.setImage(img_juego_actual);
       barra_busqueda = new TextField();
       HBox.setMargin(barra_busqueda,new Insets(20,20,20,20));
       barra_busqueda.setPromptText("Buscar en la tienda");
       barra_busqueda.setPrefWidth(200);
       barra_busqueda.setFocusTraversable(false);
       barraBusqueda.getChildren().add(barra_busqueda);
       iniciarCarrusel();
       
    }
    
    public LinkedListDobleCircular<Image> cargarDestacados(){
        LinkedListDobleCircular<Image> retorno=new LinkedListDobleCircular<>();
        
        for(int i=0;i<juegos.size();i++){
            Image tmp=App.getImage("Images/Destacados/"+juegos.get(i).getTitulo()+".jpg");
            retorno.addLast(tmp);
        }
        
        return retorno;
    }
    
    //Corregir changeImgIzq
    public void iniciarCarrusel(){
        changeImgIzq();
        changeImgDer();
    }
    //setOnAction de buttons para setear el imgv
    private void changeImgIzq(){
        btn_desc_izq.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            img_juego_actual = imgsDestacados.getAnterior(img_juego_actual);
            imgvDestacado.setImage(img_juego_actual);
        }
        });
    }
    
    private void changeImgDer(){
        btn_desc_der.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event){
            img_juego_actual = imgsDestacados.getSiguiente(img_juego_actual);
            imgvDestacado.setImage(img_juego_actual);
        }
        });
    }
    
    //setOnMouseClicked para haciendo click en la img, acceder a la vista de Axcel.
    public void abrirDestacado(){
        imgvDestacado.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                //setear la scene con la del juego
            }
        
        });
    }
    //Clean null values of the list
    private void cleanList(){
        for(int i=0;i<imgsDestacados.size();i++){
            if(imgsDestacados.get(i)==null){
                imgsDestacados.remove(i);
                i--;
            }
        }
    }
    
    private void llenarCatalogo(){
        FlowPane flowPane=new FlowPane();
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setPrefSize(914, 194);
        flowPane.setHgap(30);
        flowPane.setPadding(new Insets(20,20,20,20));
        for(int i=0;i<juegos.size();i++){
            Juego actual=juegos.get(i);
            VBox vbJuego=new VBox();
            ImageView imgvJuego=new ImageView();
            imgvJuego.setImage(App.getImage("Images/"+actual.getTitulo()+".jpg"));
            imgvJuego.setFitHeight(200);
            imgvJuego.setPreserveRatio(true);
            imgvJuego.setOnMouseClicked(e->{
                abrirVentanaJuego(actual);
            });
            vbJuego.getChildren().add(imgvJuego);
            vbJuego.getChildren().add(new Label(juegos.get(i).getTitulo()));
            vbJuego.getChildren().add(new Label(juegos.get(i).getPrecio()));
            vbJuego.setSpacing(5);
            flowPane.getChildren().add(vbJuego);
        }
        cataPane.setContent(flowPane);  
    }
    public void abrirVentanaJuego(Juego j){
        //Escribir código necesario para abrir la ventana del Juego
    }
}
