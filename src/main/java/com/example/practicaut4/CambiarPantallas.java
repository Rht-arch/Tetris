package com.example.practicaut4;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Esta clase permite cambiar entre Scenes, es decir, genera un Scene nuevo
 * ,lo muestra y posteriormente cierra el anterior para simular el cambio de
 * pantalla
 * @author Rafael Haro
 * @version 1.0
 * @since 1.0
 */
public class CambiarPantallas {
    /**
     * Este metodo se encarga de realizar los cambios entre ficheros fxml, carga el nuevo FXML,
     * crea la nueva ventana, la muestra y cierra la actual
     * @param currentStage Ventana actual
     * @param fxmlFile Recoge el fxml que se quiere mostrar
     * @param title Determina el titulo de la nueva ventana
     * @throws IOException
     */
    public static void switchScene(Stage currentStage, String fxmlFile, String title) throws IOException {
        // Cargar el nuevo FXML
        Parent root = FXMLLoader.load(CambiarPantallas.class.getResource(fxmlFile));
        Scene scene = new Scene(root);

        // Crear la nueva ventana
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle(title);

        // Mostrar la nueva ventana
        newStage.show();

        // Cerrar la ventana actual
        if (currentStage != null) {
            currentStage.close();
        }
    }
}

