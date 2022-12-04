/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyectoeddprimerparcial;

import Modelo.Juego;
import Modelo.LinkedListDobleCircular;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
    private LinkedListDobleCircular<Juego> juegos_destacados = new LinkedListDobleCircular<>();
    @FXML
    private ScrollPane root;
    @FXML
    private Label lbl_cat;
    @FXML
    private Button botonSalir;
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
    private Button botonNoche;
    @FXML
    private Button botonLuz;
    @FXML
    private Button backButton;
    @FXML
    private Text textUsuario;
    @FXML
    private Button botonCatalogo;
    @FXML
    private Button botonExplorar;
    
//    @FXML
//    private HBox hbox_h;
    
    public static boolean isModoOscuroOn=true;
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
        //System.out.println(isModoOscuroOn);
        textUsuario.setText(App.usr.getId());
        eventsUsuario(textUsuario);
        eventsUsuario(botonCatalogo);
        eventsUsuario(botonExplorar);
        backButton.setVisible(false);
        imgsDestacados = agregarDestacados();
        moverDestacados(Thread.currentThread());
        cargarImagenesModos();
        //isModoOscuroOn = true;
        hbox_catalogo.getChildren().addAll(vboxes.get(0), vboxes.get(1), vboxes.get(2), vboxes.get(3), vboxes.get(4));
        hbox_catalogo.setSpacing(25);
//        btnModoOscuro.setOnAction(e -> {
//            // cambiarModo();
//            Stage s = (Stage) root.getScene().getWindow();
//            s.close();
//            App.abrirVentana("VentanaExplorar");
//        });
        setearSalir();
//        barra_nombre = new TextField();
//        barra_nombre.setPromptText("Buscar por nombre");
//        barra_nombre.setFocusTraversable(false);
//        barra_anio = new TextField();
//        barra_anio.setPromptText("Buscar por año");
//        barra_anio.setFocusTraversable(false);
//        hbox_h.getChildren().addAll(barra_nombre, barra_anio);
//        hbox_h.setSpacing(20);
        moverCatalogo();
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
        ImageView imgv = new ImageView();
        Rectangle clip = new Rectangle(300, 160);
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        imgv.setClip(clip);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        for (int i = 0; i < juegos.size(); i++) {
            //System.out.println("Anyamemby");
            Image tmp = App.getImage("Images/Destacados/" + juegos.get(i).getTitulo() + ".jpg", true);
            if (tmp != null) {
                imgv.setImage(tmp);
                Image image = imgv.snapshot(parameters, null);
                juegos_destacados.addLast(juegos.get(i));
                lista_Destacados.addLast(image);
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
                        System.out.println("Ayudaaa");
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
            vbJuego.setAlignment(Pos.CENTER);
            ImageView imgvJuego = new ImageView();
            Image img=App.getImage("Images/" + actual.getTitulo() + ".jpg",170,227);
            imgvJuego.setImage(img);
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

            imgvJuego.setOnMouseEntered(e -> {
                vbJuego.setCursor(Cursor.HAND);
                vbJuego.setStyle("-fx-background-color:#343434;-fx-background-radius:15;");

            });

            imgvJuego.setOnMouseExited(e -> {
                vbJuego.setCursor(Cursor.DEFAULT);
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
                        VentanaDetalleController.modo = "#121212";
                        VentanaDetalleController.modocontrario = "white";
                    } else {
                        VentanaDetalleController.modo = "white";
                        VentanaDetalleController.modocontrario = "#121212";
                    }
                    VentanaDetalleController.usr = App.usr;
                    VentanaDetalleController.selected = j;
                    App.pilaVentanas.push("VentanaPrincipalDemo");
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
           
            VentanaDetalleController.modo = "#121212";
            VentanaDetalleController.modocontrario = "white";
        } else {
            VentanaDetalleController.modo = "white";
            VentanaDetalleController.modocontrario = "#121212";
            
        }
        VentanaDetalleController.usr = App.usr;
        VentanaDetalleController.selected = j;
        App.pilaVentanas.push("VentanaPrincipalDemo");
        
        FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("VentanaDetalle.fxml"));
        Parent root1 = fxmloader.load();
        Stage s = (Stage) root.getScene().getWindow();
        Scene scene = new Scene(root1, 1280, 720);
        s.setScene(scene);
        s.show();

    }
    public void setLight(){    
    isModoOscuroOn=false;
    //cambiarModo(isModoOscuroOn);
    //reseniasIniciales();
    
            
    }
    public void setNight(){  
    isModoOscuroOn=true;
    //cambiarModo(isModoOscuroOn);
    //reseniasIniciales();
    }
    
    public void setearSalir(){
    try(FileInputStream input=new FileInputStream(App.pathSS+"door5.png")){
        Image img= new Image(input,35,35,false,true);
        ImageView imgv=new ImageView(img);
        botonSalir.setGraphic(imgv);
        }   catch (IOException ex) {
                ex.printStackTrace();
            }
    
    }
    @FXML
    public void desloggear(ActionEvent e) throws IOException{
    Alert conf_desloggear = new Alert(Alert.AlertType.CONFIRMATION);
        conf_desloggear.setHeaderText(null);
        conf_desloggear.setContentText("¿Está seguro de que desea cerrar sesion?");
        Optional<ButtonType> confirmacion = conf_desloggear.showAndWait();
    if (confirmacion.get() == ButtonType.OK) {
    App.serializarUsuario(App.usr);
    App.usr=null;
    FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("Login.fxml"));
    Parent root1 = fxmloader.load();
    Stage s=(Stage)root.getScene().getWindow();
    Scene scene=new Scene(root1,1280,720);
    s.setScene(scene);
    App.pilaVentanas.clear();
    //App.pilaVentanas.push("VentanaDetalle");
    s.show();
    }
    }
    @FXML
    public void cargarCatalogo(ActionEvent e) throws IOException{
    FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("VentanaPrincipalDemo.fxml"));
    Parent root1 = fxmloader.load();
    Stage s=(Stage)root.getScene().getWindow();
    Scene scene=new Scene(root1,1280,720);
    s.setScene(scene);
    App.pilaVentanas.clear();
    VentanaPrincipalDemoController.isModoOscuroOn = isModoOscuroOn;
    s.show();
    
    }
    @FXML
    public void cargarExplorar(ActionEvent e) throws IOException{
    FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("VentanaExplorar.fxml"));
    Parent root1 = fxmloader.load();
    Stage s=(Stage)root.getScene().getWindow();
    Scene scene=new Scene(root1,1280,720);
    s.setScene(scene);
    App.pilaVentanas.clear();
    if(isModoOscuroOn){
    VentanaExplorarController.modo="#121212";
    VentanaExplorarController.modocontrario="white";
    }else{
    VentanaExplorarController.modo="white";
    VentanaExplorarController.modocontrario="#121212";
    }
    s.show();
    
    }
    public void cargarImagenesModos(){
    try(FileInputStream input=new FileInputStream(App.pathSS+"darkmode.png");FileInputStream input2=new FileInputStream(App.pathSS+"lightmode.png");){
        Image imgnoche = new Image(input,35,35,false,true);
        ImageView viewnoche = new ImageView(imgnoche);
        viewnoche.setFitHeight(35);
        viewnoche.setPreserveRatio(true);
        botonNoche.setGraphic(viewnoche);
        Image imgluz = new Image(input2,35,35,false,true);
        ImageView viewluz = new ImageView(imgluz);
        viewnoche.setFitHeight(35);
        viewnoche.setPreserveRatio(true);
        botonLuz.setGraphic(viewluz);
        }   catch (IOException ex) {
                ex.printStackTrace();
            }
    
    }
    public void eventsUsuario(Text Textusuario){
    Textusuario.setFocusTraversable(true);
    Textusuario.setOnMouseEntered(new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent e){
    Textusuario.setUnderline(true);
    }
    });
    Textusuario.setOnMouseExited(new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent e){
    Textusuario.setUnderline(false);
    }
    });
    Textusuario.setOnMouseClicked(new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent e){
        try {
            abrirVentanaUsuario(Textusuario);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    });
    }
    
    public void abrirVentanaUsuario(Text textusuario)throws IOException{
    if (isModoOscuroOn) {
            VentanaUsuarioController.modo = "#121212";
            VentanaUsuarioController.modocontrario = "white";
        } else {
            VentanaUsuarioController.modo = "white";
            VentanaUsuarioController.modocontrario = "#121212";
    }
    VentanaUsuarioController.setearUsuario(textusuario);
    FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("VentanaUsuario.fxml"));
    Parent root1 = fxmloader.load();
    Stage s=(Stage)textusuario.getScene().getWindow();
    Scene scene=new Scene(root1,1280,720);
    s.setScene(scene);
    App.pilaVentanas.push("VentanaPrincipalDemo");
    s.show();
    }
    public void eventsUsuario(Button boton){
    
    boton.setOnMouseEntered(new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent e){
    boton.setUnderline(true);
    }
    });
    boton.setOnMouseExited(new EventHandler<MouseEvent>(){
    @Override
    public void handle(MouseEvent e){
    boton.setUnderline(false);
    }
    });
    }
    
    
}
