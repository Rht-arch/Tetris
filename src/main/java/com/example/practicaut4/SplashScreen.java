package com.example.practicaut4;

import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Esta clase se encarga de hacer de intermediario entre la SplashScreen y el juego principal
 * @author Rafael Haro
 * @version 1.0
 * @since 1.0
 */
public class SplashScreen {
    /**
     * Genera una nueva ventana
     */
    protected Stage splashStage;

    /**
     * Configuramos la Splash Screen para que se vea durante 4 segundos y luego se lanza el juego del tetris
     * y deberia cerrar la pantalla de carga
     */
    public void initialize() {
        // Simulamos que el SplashScreen está visible durante 4 segundos
        new Thread(() -> {
            try {
                Thread.sleep(4000);  // Espera 4 segundos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Cambio de pantalla después de 4 segundos
            Platform.runLater(() -> {
                // Cambiar al juego principal
                try {
                    CambiarPantallas.switchScene(splashStage, "tetris.fxml", "Juego");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Cerrar la ventana del SplashScreen
                splashStage.close();
            });
        }).start();
    }

    /**
     * Permite establecer la Stage que prefieras
     * @param splashStage Coge la escena actual
     */
    public void setSplashStage(Stage splashStage) {
        this.splashStage = splashStage;
    }
}
