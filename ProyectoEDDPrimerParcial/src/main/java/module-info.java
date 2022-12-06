module com.mycompany.proyectoeddprimerparcial {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires javafx.media;
    requires javafx.web;

    opens com.mycompany.proyectoeddprimerparcial to javafx.fxml;
    exports com.mycompany.proyectoeddprimerparcial;
}
