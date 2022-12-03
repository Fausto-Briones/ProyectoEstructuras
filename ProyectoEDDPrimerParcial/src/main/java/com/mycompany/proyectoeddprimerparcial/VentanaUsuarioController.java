/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyectoeddprimerparcial;


import Modelo.Juego;
import Modelo.Usuario;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AVGla
 */
public class VentanaUsuarioController implements Initializable {

    @FXML
    Button botonSalir;
    @FXML
    Button botonLuz;
    @FXML
    Button botonNoche;
    @FXML
    Text usrWishTitle;
    @FXML
    FlowPane flowWish;
    @FXML
    VBox vboxMain;
    @FXML
    Button backButton;
    @FXML
    HBox hboxModo;
    @FXML
    Button botonCatalogo;
    @FXML
    Button botonExplorar;
    @FXML
    Text textUsuario;
    
    public static String modo;
    public static String modocontrario;
    //public static Juego selected1;
    //public String modo="white";
    public static Usuario usr1;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textUsuario.setText(usr1.getId());
        cambiarModo(modo,modocontrario);
        setearSalir();
        cargarImagenesModos();
        usrWishTitle.setText(usr1.getId()+"'s Wishlist");
        if(usr1.getId().equals(App.usr.getId())){
        cargarWishlistMia();
        }else{
        cargarWishlist();
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
    Stage s=(Stage)flowWish.getScene().getWindow();
    Scene scene=new Scene(root1,1280,720);
    s.setScene(scene);
    //App.pilaVentanas.push("VentanaDetalle");
    s.show();
    }
    }
    @FXML
    public void regresarDetalle()throws IOException{
    VentanaDetalleController.modo=modo;
    VentanaDetalleController.modocontrario=modocontrario;
    FXMLLoader fxmloader = new FXMLLoader(App.class.getResource(App.pilaVentanas.pop()+".fxml"));
    Parent root1 = fxmloader.load();
    Stage s=(Stage)vboxMain.getScene().getWindow();
    Scene scene=new Scene(root1,1280,720);
    s.setScene(scene);
    s.show();
        
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
    public void setLight(){    
    modo="white";
    modocontrario="black";
    cambiarModo(modo,modocontrario);
    //reseniasIniciales();
    
            
    }
    public void setNight(){  
    modo="black";
    modocontrario="white";
    cambiarModo(modo,modocontrario);
    //reseniasIniciales();
    }
    public void cambiarModo(String modo,String modocontrario){
        //System.out.println(modocontrario);
    hboxModo.setStyle("-fx-border-color:"+modocontrario+";-fx-border-width:3;-fx-border-radius:5");
    vboxMain.setStyle("-fx-background-color:"+modo);
    usrWishTitle.setStyle("-fx-fill:"+modocontrario);
    backButton.setStyle("-fx-text-fill:"+modocontrario+";-fx-background-color:"+modo);
    textUsuario.setStyle("-fx-fill:"+modocontrario);
    botonCatalogo.setStyle("-fx-text-fill:"+modocontrario+";-fx-background-color:"+modo);
    botonExplorar.setStyle("-fx-text-fill:"+modocontrario+";-fx-background-color:"+modo);
    botonSalir.setStyle("-fx-background-color:red;-fx-background-radius:24;-fx-border-radius:20;-fx-border-width:3;-fx-border-color:"+modocontrario);    
    for(Node nodo:flowWish.getChildren()){
    ((Label)((VBox)nodo).getChildren().get(1)).setStyle("-fx-text-fill:"+modocontrario);
    }    
        
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
    
    
    
    
    
    public static void setearUsuario(Text textusuario){
        //System.out.println(textusuario.getText());
    Usuario usr2=(Usuario)(App.leerUsuario(textusuario.getText()));
        //System.out.println(usr2.getId());
        usr1=usr2;
    }
    
    public void cargarWishlist(){
    for(int i=0;i<usr1.getWishlist().getLength();i++){
            Juego actual=usr1.getWishlist().getI(i);
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
            vbJuego.getChildren().add(imgvJuego);
            Label titulo=new Label(actual.getTitulo());
//            titulo.setStyle("-fx-font-weight:bold;-fx-font-size:12");
            titulo.setTextFill(Color.WHITE);
            vbJuego.getChildren().add(titulo);
            //Label precio=new Label(actual.getPrecio());
//            precio.setStyle("-fx-font-weight:bold;-fx-font-size:12");
            //precio.setTextFill(Color.WHITE);
            //vbJuego.getChildren().add(precio);
            vbJuego.setSpacing(5);
            flowWish.getChildren().add(vbJuego);
            
//            imgvJuego.setOnMouseEntered(e->{
//                imgvJuego.setFitWidth(230);
//                imgvJuego.setFitHeight(305);
//            });
//            
//            imgvJuego.setOnMouseExited(e->{
//                imgvJuego.setFitWidth(225);
//                imgvJuego.setFitHeight(300);
//                
//            });
        }
    }
    public void cargarWishlistMia(){
    for(int i=0;i<App.usr.getWishlist().getLength();i++){
            Juego actual=App.usr.getWishlist().getI(i);
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
            vbJuego.getChildren().add(imgvJuego);
            Label titulo=new Label(actual.getTitulo());
//            titulo.setStyle("-fx-font-weight:bold;-fx-font-size:12");
            titulo.setTextFill(Color.WHITE);
            vbJuego.getChildren().add(titulo);
            //Label precio=new Label(actual.getPrecio());
//            precio.setStyle("-fx-font-weight:bold;-fx-font-size:12");
            //precio.setTextFill(Color.WHITE);
            //vbJuego.getChildren().add(precio);
            vbJuego.setSpacing(5);
            flowWish.getChildren().add(vbJuego);
            
//            imgvJuego.setOnMouseEntered(e->{
//                imgvJuego.setFitWidth(230);
//                imgvJuego.setFitHeight(305);
//            });
//            
//            imgvJuego.setOnMouseExited(e->{
//                imgvJuego.setFitWidth(225);
//                imgvJuego.setFitHeight(300);
//                
//            });
        }
    }
    
    
    
    
    }
    
    
    


    
    
    

