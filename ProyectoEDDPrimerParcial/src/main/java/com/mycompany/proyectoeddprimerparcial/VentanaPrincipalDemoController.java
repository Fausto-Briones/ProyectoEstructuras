/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyectoeddprimerparcial;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import Modelo.Juego;
import Modelo.LDEC;
import Modelo.LinkedListDobleCircular;
import Modelo.TDAArraylist;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
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
    private LinkedListDobleCircular<VBox> vboxes=llenarCatalogo2(juegos);
    
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
    private TextField barra_busquedaAnio;
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
       
       vbox1.getChildren().addAll(vboxes.get(0));
       vbox2.getChildren().addAll(vboxes.get(1));
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
            VBox tmp=(VBox)vbox1.getChildren().get(0);
            vbox1.getChildren().clear();
            vbox1.getChildren().add(vboxes.getAnterior(vboxes.getAnterior(tmp)));
            vbox2.getChildren().clear();
            vbox2.getChildren().add(vboxes.getAnterior(tmp));
        });
    }
    
    public void moverDer(){
        btn_cat_der.setOnAction(e->{
            VBox tmp=(VBox)vbox2.getChildren().get(0);
            vbox1.getChildren().clear();
            vbox1.getChildren().add(vboxes.getSiguiente(tmp));
            vbox2.getChildren().clear();
            vbox2.getChildren().add(vboxes.getSiguiente(vboxes.getSiguiente(tmp)));
        });
    }
    public LDEC<Juego> buscarJ() {
        LDEC<Juego> tmp=new LDEC<>();
        for(int i=0;i<juegos.size();i++){
            tmp.addLast(juegos.get(i));
        }
        Comparator comparador = new Comparator<Juego>() {
            @Override
            public int compare(Juego j1, Juego j2) {
                if (escogerCondicion(j1,j2)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        };
        LDEC<Juego> resultado=tmp.findAll(comparador, new Juego(barra_busqueda.getText(),barra_busquedaAnio.getText()));
        return resultado;
    }

    private boolean escogerCondicion(Juego j1, Juego j2) {
        String titulo = barra_busqueda.getText();
        String anio = barra_busquedaAnio.getText();
        boolean condicion=true;
        if (!titulo.equals("") && !anio.equals("")) {
            condicion = j1.getTitulo().equals(j2.getTitulo()) && j1.getAnio().equals(j2.getAnio());
        } else if (!titulo.equals("") && anio.equals("")) {
            condicion = j1.getTitulo().equals(j2.getTitulo());
        } else if (titulo.equals("") && !anio.equals("")) {
            condicion = j1.getAnio().equals(j2.getAnio());
        }
        return condicion;
    }
    private LinkedListDobleCircular<VBox> llenarCatalogo2(LinkedListDobleCircular<Juego> juegosl) {
        LinkedListDobleCircular<VBox> tmp=new LinkedListDobleCircular<>();
        for (int i = 0; i < juegosl.size(); i++) {
            Juego actual = juegosl.get(i);
            VBox vbJuego = new VBox();
            ImageView imgvJuego = new ImageView();
            imgvJuego.setImage(App.getImage("Images/" + actual.getTitulo() + ".jpg"));
            imgvJuego.setFitHeight(227);
            imgvJuego.setPreserveRatio(true);
            Rectangle clip = new Rectangle(170, 227);
            clip.setArcWidth(30);
            clip.setArcHeight(30);
            imgvJuego.setClip(clip);
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            WritableImage image = imgvJuego.snapshot(parameters, null);
            imgvJuego.setClip(null);
            imgvJuego.setImage(image);

            imgvJuego.setOnMouseClicked(e -> {
                try {
                    VistaPrincipalController.abrirVentanaJuego(actual);
                } catch (IOException e1) {
                    System.out.println("Se cayo");
                }

            });
            vbJuego.getChildren().add(imgvJuego);
            Label titulo = new Label(juegos.get(i).getTitulo());
//            titulo.setStyle("-fx-font-weight:bold;-fx-font-size:12");
            titulo.setTextFill(Color.WHITE);
            vbJuego.getChildren().add(titulo);
            Label precio = new Label(juegos.get(i).getPrecio());
//            precio.setStyle("-fx-font-weight:bold;-fx-font-size:12");
            precio.setTextFill(Color.WHITE);
            vbJuego.getChildren().add(precio);
            vbJuego.setSpacing(5);
            tmp.addLast(vbJuego);
        }
        return tmp;
    }
    
}
