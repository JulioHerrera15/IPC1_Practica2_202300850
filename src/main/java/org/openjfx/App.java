package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import org.kordamp.ikonli.javafx.FontIcon;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    private TertiaryController controller;   

    @Override
    public void start(Stage stage) throws IOException {        
        this.controller = new TertiaryController();
        EstadoAplicacion estado = cargarEstado(controller);
        String fxmlFile;
        if (estado != null && estado.getControladorActual().equals("TertiaryController")) {
            fxmlFile = "/org/openjfx/tertiary.fxml";
        } else {            
            fxmlFile = "/org/openjfx/primary.fxml";
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        if (estado != null && estado.getControladorActual().equals("TertiaryController")) {
            TertiaryController controller = loader.getController();
            controller.setViajes(estado.getViajes());                    
        }

        scene = new Scene(root);
        stage.setScene(scene);                   
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        
        stage.show();
    }

    @Override
    public void stop() {        
        controller.guardarEstado();
        System.out.println("Aplicación cerrada");
    }

    public EstadoAplicacion cargarEstado(TertiaryController controller) {
        EstadoAplicacion estado = null;
        File file = new File("estado.ser");
        if (file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                estado = (EstadoAplicacion) in.readObject();
                // Imprime los viajes cargados
                System.out.println("Viajes cargados: " + estado.getViajes());
                in.close();
                fileIn.close();
                System.out.println("Estado cargado correctamente");                               
            } catch (IOException i) {
                System.out.println("Error al cargar el estado");
                i.printStackTrace();
            } catch (ClassNotFoundException c) {
                System.out.println("La clase EstadoAplicacion no se encontró");
                c.printStackTrace();
            }
        } else {
            System.out.println("No se encontró ningún estado, se cargará el controlador principal");
        }
        return estado;
    }

    public static void main(String[] args) {
        launch();       
    }

}