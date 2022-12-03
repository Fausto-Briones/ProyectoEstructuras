/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyectoeddprimerparcial;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import Modelo.*;
import java.io.IOException;
import java.util.Comparator;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jexa1
 */
public class VentanaExplorarController implements Initializable {
    @FXML
    private TextField barra_busqueda;
    @FXML
    private TextField barra_busquedaAnio;
    @FXML
    private Button btnBuscar;
    @FXML
    private Label lblResultados;
    @FXML
    private HBox hboxJuegos;
    @FXML
    private Button btnAnteriores;
    @FXML
    private Button btnSiguientes;
    @FXML
    private ScrollPane root;
    private LinkedListDobleCircular<Juego> juegos=App.cargarJuegos();
    private static LinkedListDobleCircular<VBox> vboxes;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buscarJuego();
        mover();
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
        boolean condicion=false;
        if (!titulo.equals("") && !anio.equals("")) {
            condicion = j1.getTitulo().toLowerCase().contains(j2.getTitulo().toLowerCase()) && j1.getAnio().equals(j2.getAnio());
        } else if (!titulo.equals("") && anio.equals("")) {
            condicion = j1.getTitulo().toLowerCase().contains(j2.getTitulo().toLowerCase());
        } else if (titulo.equals("") && !anio.equals("")) {
            condicion = j1.getAnio().equals(j2.getAnio());
        }
        return condicion;
    }
    private LinkedListDobleCircular<VBox> llenarCatalogo2(LDEC<Juego> juegosl) {
        LinkedListDobleCircular<VBox> tmp=new LinkedListDobleCircular<>();
        for (int i = 0; i < juegosl.getLength(); i++) {
            Juego actual = juegosl.getI(i);
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
                    abrirVentanaJuego(actual);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                
            });
            vbJuego.getChildren().add(imgvJuego);
            Label titulo = new Label(actual.getTitulo());
            titulo.setPadding(new Insets(5,5,5,5));
            titulo.setTextFill(Color.WHITE);
            vbJuego.getChildren().add(titulo);
            Label precio = new Label(actual.getPrecio());
            precio.setTextFill(Color.WHITE);
            precio.setPadding(new Insets(5,5,5,5));
            vbJuego.getChildren().add(precio);
            vbJuego.setSpacing(5);
            vbJuego.setPrefSize(170,227);
            tmp.addLast(vbJuego);
            
            imgvJuego.setOnMouseEntered(e->{
                vbJuego.setCursor(Cursor.HAND);
                vbJuego.setStyle("-fx-background-color:#343434;-fx-background-radius:15;");

            });
            
            imgvJuego.setOnMouseExited(e->{
                vbJuego.setCursor(Cursor.DEFAULT);
                vbJuego.setStyle("-fx-background-color:black;-fx-background-radius:15;");
            });
        }
        return tmp;
    }

    private void abrirVentanaJuego(Juego actual) throws IOException {
        VentanaDetalleController.usr=App.usr;
        VentanaDetalleController.selected=actual;
        FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("VentanaDetalle.fxml"));
        Parent root1 = fxmloader.load();
        Stage s=(Stage)root.getScene().getWindow();
        Scene scene=new Scene(root1,1280,720);
        s.setScene(scene);
        s.show();
    }
    public void mover() {
        moverIzq();
        moverDer();
    }

    public void moverIzq() {
        btnAnteriores.setOnAction(e -> {
            VBox tmp = (VBox) hboxJuegos.getChildren().get(0);
            hboxJuegos.getChildren().clear();
            for (int i = 0; i < 5; i++) {
                tmp = vboxes.getAnterior(tmp);
            }
            for (int i = 0; i < 5; i++) {
                hboxJuegos.getChildren().add(tmp);
                tmp = vboxes.getSiguiente(tmp);
            }
        });
    }

    public void moverDer() {
        btnSiguientes.setOnAction(e -> {
            VBox tmp = (VBox) hboxJuegos.getChildren().get(4);
            hboxJuegos.getChildren().clear();
            for (int i = 0; i < 5; i++) {
                hboxJuegos.getChildren().add(vboxes.getSiguiente(tmp));
                tmp = vboxes.getSiguiente(tmp);
            }
        });
    }
    public void buscarJuego(){
        btnBuscar.setOnAction(e->{
            hboxJuegos.getChildren().clear();
            vboxes=llenarCatalogo2(buscarJ());
            if(vboxes.size()<=5){
                btnAnteriores.setVisible(false);
                btnSiguientes.setVisible(false);
                for(int i=0;i<vboxes.size();i++){
                    hboxJuegos.getChildren().add(vboxes.get(i));
                }
            }else{
                btnAnteriores.setVisible(true);
                btnSiguientes.setVisible(true);
                for(int i=0;i<5;i++){
                    hboxJuegos.getChildren().add(vboxes.get(i));
                }
            }
            lblResultados.setVisible(true);
        });
    }
}
