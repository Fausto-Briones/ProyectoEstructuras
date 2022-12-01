/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyectoeddprimerparcial;

import Modelo.Juego;
import Modelo.LDEC;
import Modelo.LinkedListDobleCircular;
import Modelo.TDAArraylist;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
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
public class VistaPrincipalController implements Initializable {
    private LinkedListDobleCircular<Juego> juegos=App.cargarJuegos();
    private LinkedListDobleCircular<Image> imgsDestacados=cargarDestacados();
    @FXML
    public static VBox root;
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
    @FXML
    private FlowPane flowpane;
    @FXML
    private Label lblDestacados;
    @FXML
    private Label lblDisfruta;
    @FXML
    private Label lblCatalogo;
    @FXML
    private VBox vbPrincipal;
    @FXML
    private VBox vbCarrusel;
    @FXML
    private HBox hbCatalogo;
    @FXML
    private Button btnModoOscuro;
    public static boolean isModoOscuroOn;
    
    Image img_juego_actual;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       isModoOscuroOn=true;
       btnModoOscuro.setOnAction(e->{
           cambiarModo();
       });
       cleanList();
       llenarCatalogo();
       img_juego_actual = imgsDestacados.get(0);
       imgvDestacado.setImage(img_juego_actual);
       imgvDestacado.setPreserveRatio(true);
       barra_busqueda = new TextField();
       HBox.setMargin(barra_busqueda,new Insets(20,20,20,20));
       HBox.setMargin(btnModoOscuro,new Insets(20,20,20,20));
       barra_busqueda.setPromptText("Buscar en la tienda");
       barra_busqueda.setPrefWidth(200);
       barra_busqueda.setFocusTraversable(false);
       barraBusqueda.getChildren().add(barra_busqueda);
       iniciarCarrusel();
       reiniciarCatalogo();
       
    }
    
    public LinkedListDobleCircular<Image> cargarDestacados(){
        LinkedListDobleCircular<Image> retorno=new LinkedListDobleCircular<>();
        
        for(int i=0;i<juegos.size();i++){
            //System.out.println("HOLA QUE APASA");
            Image tmp=App.getImage("Images/Destacados/"+juegos.get(i).getTitulo()+".jpg");
            retorno.addLast(tmp);
            int d=i;
            imgvDestacado.setOnMouseClicked(new EventHandler<MouseEvent>(){    
            @Override
            public void handle(MouseEvent event){
                
                try {
                    abrirDestacado(juegos.get(d));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        
        });
            
            
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
    //CAMBIADO POR AXCEL
    public void abrirDestacado(Juego j) throws IOException{
        imgvDestacado.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                try {
                    if(isModoOscuroOn){
                        VentanaDetalleController.modo="black";
                        VentanaDetalleController.modocontrario="white";
                    }else{
                        VentanaDetalleController.modo="white";
                        VentanaDetalleController.modocontrario="black";
                    }
                    VentanaDetalleController.usr=App.usr;
                    VentanaDetalleController.selected=j;
                    FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("VentanaDetalle.fxml"));
                    Parent root1 = fxmloader.load();
                    Stage s=(Stage)root.getScene().getWindow();
                    Scene scene=new Scene(root1,1280,720);
                    s.setScene(scene);
                    s.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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
    
    private void llenarCatalogo() {
//        flowPane.setPadding(new Insets(20,20,20,20));
        for(int i=0;i<juegos.size();i++){
            Juego actual=juegos.get(i);
            VBox vbJuego=new VBox();
            ImageView imgvJuego=new ImageView();
            imgvJuego.setImage(App.getImage("Images/"+actual.getTitulo()+".jpg"));
            imgvJuego.setFitHeight(300);
            imgvJuego.setPreserveRatio(true);
            Rectangle clip=new Rectangle(225,300);
            clip.setArcWidth(30);
            clip.setArcHeight(30);
            imgvJuego.setClip(clip);
            SnapshotParameters parameters=new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            WritableImage image=imgvJuego.snapshot(parameters,null);
            imgvJuego.setClip(null);
            imgvJuego.setImage(image);
            
            imgvJuego.setOnMouseClicked(e->{
                try{
                    abrirVentanaJuego(actual);
                }catch(IOException e1)
                {
                    System.out.println("Se cayo");
                }
                
            });
            vbJuego.getChildren().add(imgvJuego);
            Label titulo=new Label(juegos.get(i).getTitulo());
//            titulo.setStyle("-fx-font-weight:bold;-fx-font-size:12");
            titulo.setTextFill(Color.WHITE);
            vbJuego.getChildren().add(titulo);
            Label precio=new Label(juegos.get(i).getPrecio());
//            precio.setStyle("-fx-font-weight:bold;-fx-font-size:12");
            precio.setTextFill(Color.WHITE);
            vbJuego.getChildren().add(precio);
            vbJuego.setSpacing(5);
            flowpane.getChildren().add(vbJuego);
            
            imgvJuego.setOnMouseEntered(e->{
                imgvJuego.setFitWidth(230);
                imgvJuego.setFitHeight(305);
            });
            
            imgvJuego.setOnMouseExited(e->{
                imgvJuego.setFitWidth(225);
                imgvJuego.setFitHeight(300);
                
            });
        }
    }
    
    //CAMBIADO POR AXCEL
    public static void abrirVentanaJuego(Juego j) throws IOException{
        if(isModoOscuroOn){
        VentanaDetalleController.modo="black";
        VentanaDetalleController.modocontrario="white";
        }else{
        VentanaDetalleController.modo="white";
        VentanaDetalleController.modocontrario="black";
        }
        VentanaDetalleController.usr=App.usr;
        VentanaDetalleController.selected=j;
        FXMLLoader fxmloader = new FXMLLoader(App.class.getResource("VentanaDetalle.fxml"));
        Parent root1 = fxmloader.load();
        Stage s=(Stage)root.getScene().getWindow();
        Scene scene=new Scene(root1,1280,720);
        s.setScene(scene);
        s.show();
        
    }
    
    
    public void buscador(){
        String busqueda = barra_busqueda.getText();
        for(int i = 0; i<juegos.size();i++){
            if(juegos.get(i).getTitulo()==busqueda){
                //abrir ventana del juego correspondiente
            }
        }
    }

    private void reiniciarCatalogo(){
       cataPane.setOnScrollFinished((ScrollEvent e) -> {
           llenarCatalogo();
       });
    }
    public void cambiarModo(){
        if(isModoOscuroOn){
            isModoOscuroOn=false;
            cambiarContraste("black","white");
        }else{
            isModoOscuroOn=true;
            cambiarContraste("white"," #121212");
        }
    }
    public void cambiarContraste(String colorTextos,String colorFondos){
        lblDestacados.setTextFill(Color.web(colorTextos));
        lblDisfruta.setTextFill(Color.web(colorTextos));
        lblCatalogo.setTextFill(Color.web(colorTextos));
        for(Node nodo:flowpane.getChildren()){
            VBox vbox= (VBox)nodo;
            Label titulo=(Label)vbox.getChildren().get(1);
            titulo.setTextFill(Color.web(colorTextos));
            Label precio=(Label)vbox.getChildren().get(2);
            precio.setTextFill(Color.web(colorTextos));
        }
        vbPrincipal.setStyle("-fx-background-color:"+colorFondos);
        cabecera.setStyle("-fx-background-color:"+colorFondos);
        barraBusqueda.setStyle("-fx-background-color:"+colorFondos);
        carrusel.setStyle("-fx-background-color:"+colorFondos);
        vbCarrusel.setStyle("-fx-background-color:"+colorFondos);
        hbCatalogo.setStyle("-fx-background-color:"+colorFondos);
        flowpane.setStyle("-fx-background-color:"+colorFondos);
    }
}

    
//Dar funcionalidad a la barra de busqueda
