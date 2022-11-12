package com.mycompany.proyectoeddprimerparcial;

import Modelo.Juego;
import Modelo.TDAArraylist;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("VistaPrincipal"), 1335, 720);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Game Store");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    public static Image getImage(String name){
        Image img=null;
       try(FileInputStream f=new FileInputStream(name)){
           img=new Image(f);
       }catch(FileNotFoundException f){
           System.out.println("No se encontró el archivo solicitado");
       }catch(IOException i){
           System.out.println("Hubo un error, inténtalo más tarde");
       }
       return img;
    }
    public static TDAArraylist<String[]> cargarDatos(String ruta){
        TDAArraylist<String[]> retorno=new TDAArraylist<>();
        try(BufferedReader bf=new BufferedReader(new FileReader(ruta))){
            //Se lee la cabecera del archivo
            bf.readLine();
            String linea;
            while((linea=bf.readLine())!=null){
                retorno.add(linea.split(","));
            }
        }catch(FileNotFoundException fe){

        }catch(IOException ioEx){

        }
        return retorno;
    }
    public static TDAArraylist<Juego> cargarJuegos(){
        TDAArraylist<Juego> juegos=new TDAArraylist<>();
        TDAArraylist<String[]> datos=cargarDatos("Files/games.csv");
        for(int i=0;i<datos.size();i++){
            String[] arr=datos.get(i);
            Juego juego=new Juego(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],arr[6]);
            juegos.add(juego);
        }
        return juegos;
    }
    
}