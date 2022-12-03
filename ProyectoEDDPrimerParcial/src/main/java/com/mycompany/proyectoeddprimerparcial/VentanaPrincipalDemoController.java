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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class VentanaPrincipalDemoController implements Initializable {

    private LinkedListDobleCircular<Juego> juegos = App.cargarJuegos();
    private LinkedListDobleCircular<Image> imgsCatalogo = llenarCatalogo();
    private LinkedListDobleCircular<VBox> vboxes = llenarCatalogo2(juegos);
    private LinkedListDobleCircular<Juego> juegos_destacados = new LinkedListDobleCircular();
    @FXML
    private ScrollPane root;
    @FXML
    private Label lbl_cat;
    @FXML
    private Button btn_cat_izq;
    @FXML
    private Button btn_cat_der;
    @FXML
    private Button btnModoOscuro;
    @FXML
    private ImageView imgvDestacados;
    @FXML
    private HBox hbox_catalogo;
    @FXML
    private HBox hbox_h;
    private boolean isModoOscuroOn;
    private TextField barra_busqueda;
    Image img_juego_actual;
    Juego juego_actual;
    Juego juego_destacado_actual;
    Image imagenDestacada_actual;
    LinkedListDobleCircular<Image> imgsDestacados;
    /**
     * Initializes the controller class.
     */
    private TextField barra_nombre;
    private TextField barra_anio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        moverDestacados(Thread.currentThread());
        isModoOscuroOn = true;
        hbox_catalogo.getChildren().addAll(vboxes.get(0), vboxes.get(1), vboxes.get(2), vboxes.get(3), vboxes.get(4));
        hbox_catalogo.setSpacing(25);
        //btnModoOscuro.setOnAction(e->{
        // cambiarModo();
        //});

        barra_nombre = new TextField();
        barra_nombre.setPromptText("Buscar por nombre");
        barra_nombre.setFocusTraversable(false);
        barra_anio = new TextField();
        barra_anio.setPromptText("Buscar por año");
        barra_anio.setFocusTraversable(false);
        hbox_h.getChildren().addAll(barra_nombre, barra_anio);
        hbox_h.setSpacing(20);
        moverCatalogo();
        imgsDestacados = agregarDestacados();
        imagenDestacada_actual = imgsDestacados.get(0);
        imgvDestacados.setImage(imgsDestacados.get(0));
        juego_destacado_actual = juegos_destacados.get(0);

    }

    public LinkedListDobleCircular<Image> llenarCatalogo() {
        LinkedListDobleCircular<Image> retorno = new LinkedListDobleCircular<>();

        for (int i = 0; i < juegos.size(); i++) {
            Image tmp = App.getImage("Images/" + juegos.get(i).getTitulo() + ".jpg");
            retorno.addLast(tmp);
        }

        return retorno;

    }

    public void moverCatalogo() {
        moverIzq();
        moverDer();
    }

    public void moverIzq() {
        btn_cat_izq.setOnAction(e -> {
            VBox tmp = (VBox) hbox_catalogo.getChildren().get(0);
            hbox_catalogo.getChildren().clear();
            for (int i = 0; i < 5; i++) {
                tmp = vboxes.getAnterior(tmp);
            }
            for (int i = 0; i < 5; i++) {
                hbox_catalogo.getChildren().add(tmp);
                tmp = vboxes.getSiguiente(tmp);
            }
        });
    }

    public void moverDer() {
        btn_cat_der.setOnAction(e -> {
            VBox tmp = (VBox) hbox_catalogo.getChildren().get(4);
            hbox_catalogo.getChildren().clear();
            for (int i = 0; i < 5; i++) {
                hbox_catalogo.getChildren().add(vboxes.getSiguiente(tmp));
                tmp = vboxes.getSiguiente(tmp);
            }
        });
    }

    public LinkedListDobleCircular<Image> agregarDestacados() {
        LinkedListDobleCircular<Image> lista_Destacados = new LinkedListDobleCircular();

        for (int i = 0; i < juegos.size(); i++) {
            Image tmp = App.getImage("Images/Destacados/" + juegos.get(i).getTitulo() + ".jpg", true);
            if (tmp != null) {
                juegos_destacados.addLast(juegos.get(i));
                lista_Destacados.addLast(tmp);
            }
        }
        return lista_Destacados;

    }

    public void moverDestacados(Thread hilo) {
        Thread hilo1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (hilo.isAlive()) {
                    Platform.runLater(() -> {

                        imagenDestacada_actual = imgsDestacados.getSiguiente(imagenDestacada_actual);
                        juego_destacado_actual = juegos_destacados.getSiguiente(juego_destacado_actual);
                        imgvDestacados.setImage(imagenDestacada_actual);
                        try {
                            abrirDestacado(juego_destacado_actual);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                    }
                }

            }
        });
        hilo1.start();

    }

    private LinkedListDobleCircular<VBox> llenarCatalogo2(LinkedListDobleCircular<Juego> juegosl) {
        LinkedListDobleCircular<VBox> tmp = new LinkedListDobleCircular<>();
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
                    abrirVentanaJuego(actual);
                } catch (IOException e1) {
                    System.out.println("Se cayo");
                }

            });
            vbJuego.getChildren().add(imgvJuego);
            Label titulo = new Label(juegos.get(i).getTitulo());
            titulo.setPadding(new Insets(5, 5, 5, 5));
            titulo.setTextFill(Color.WHITE);
            vbJuego.getChildren().add(titulo);
            Label precio = new Label(juegos.get(i).getPrecio());
            precio.setTextFill(Color.WHITE);
            precio.setPadding(new Insets(5, 5, 5, 5));
            vbJuego.getChildren().add(precio);
            vbJuego.setSpacing(5);
            tmp.addLast(vbJuego);
            vbJuego.setCursor(Cursor.HAND);
            imgvJuego.setOnMouseEntered(e -> {

                vbJuego.setStyle("-fx-background-color:#343434;-fx-background-radius:15;");

            });

            imgvJuego.setOnMouseExited(e -> {
                vbJuego.setStyle("-fx-background-color:#121212;-fx-background-radius:15;");
            });
        }
        return tmp;
    }

    public void abrirDestacado(Juego j) throws IOException {
        imgvDestacados.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if (isModoOscuroOn) {
                        VentanaDetalleController.modo = "black";
                        VentanaDetalleController.modocontrario = "white";
                    } else {
                        VentanaDetalleController.modo = "white";
                        VentanaDetalleController.modocontrario = "black";
                    }
                    VentanaDetalleController.usr = App.usr;
                    VentanaDetalleController.selected = j;
                    FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("VentanaDetalle.fxml"));
                    Parent root1 = fxmloader.load();
                    Stage s = (Stage) root.getScene().getWindow();
                    Scene scene = new Scene(root1, 1280, 720);
                    s.setScene(scene);
                    s.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });
    }

    public void abrirVentanaJuego(Juego j) throws IOException {
        if (isModoOscuroOn) {
            VentanaDetalleController.modo = "black";
            VentanaDetalleController.modocontrario = "white";
        } else {
            VentanaDetalleController.modo = "white";
            VentanaDetalleController.modocontrario = "black";
        }
        VentanaDetalleController.usr = App.usr;
        VentanaDetalleController.selected = j;
        FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("VentanaDetalle.fxml"));
        Parent root1 = fxmloader.load();
        Stage s = (Stage) root.getScene().getWindow();
        Scene scene = new Scene(root1, 1280, 720);
        s.setScene(scene);
        s.show();

    }

}
