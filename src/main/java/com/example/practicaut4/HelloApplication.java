package com.example.practicaut4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Esta clase es predeterminada al generar el proyecto
 * se encarga de iniciar la aplicación y iniciar la primera
 * ventana, es decir, el inicio de sesión
 *
 * @author  Rafael Haro
 * @version  1.0
 * @since  1.0
 */
public class HelloApplication extends Application {

    /**
     * Este método genera la primera escena de la aplicación
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Lanza el método start
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}