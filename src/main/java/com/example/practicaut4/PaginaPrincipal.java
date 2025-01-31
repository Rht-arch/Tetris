package com.example.practicaut4;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Esta clase contiene la interfaz del menú de juego con
 * dos botones uno para jugar y otro para salir y la posibilidad
 * de traducir a Inglés o a Español
 * @author Rafael Haro
 * @version 1.0
 * @since 1.0
 */
public class PaginaPrincipal {
    /**
     * Botón cuya finalidad es comenzar el juego
     */
    @FXML
    protected Button jugar;

    /**
     * Botón cuya finalidad es salir del juego
     */
    @FXML
    protected Button salir;

    /**
     * Contiene almacenado las traducciones de los textos
     */
    protected ResourceBundle resources;

    /**
     * Contiene la imagen que permite abrir html con la ayuda
     */
    @FXML
    protected ImageView info;

    /**
     * Este metodo se encarga de inicializar el idioma en default, es decir,
     * en Español y actualizar los textos en función a si has cambiado el idioma
     */
    @FXML
    public void initialize() {
        // Cargar el ResourceBundle con el idioma actual
        resources = ResourceBundle.getBundle("com.example.practicaut4.EtiquetasBundle", java.util.Locale.getDefault());

        // Actualizar los textos de los botones según el idioma
        jugar.setText(resources.getString("jugar_button_text"));
        salir.setText(resources.getString("salir_button_text"));

        // Asegurarse de que los botones estén enfocados
        jugar.setFocusTraversable(true);
        salir.setFocusTraversable(true);
    }

    /**
     * Esta clase se encarga de asignar los atajos de teclado para cambiar el idioma
     * usando CTRL + I para inglés o CTRL + E para español
     * @deprecated No implementado
     */
    @FXML
    public void setKeyboardShortcuts() {
        // Usamos Platform.runLater para asegurar que el código se ejecute después de que la escena esté completamente cargada
        Platform.runLater(() -> {
            // Añadir el filtro de teclas una vez que la escena esté completamente cargada
            jugar.getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.isControlDown() && event.getCode() == KeyCode.I) {
                    cambiarIdioma("en", "US");  // Cambiar a inglés
                } else if (event.isControlDown() && event.getCode() == KeyCode.E) {
                    cambiarIdioma("es", "ES");  // Cambiar a español
                }
            });
        });
    }

    /**
     *  Metodo que cambia el idioma en todos texto que se encuentren en el Scene actual
     * @param idioma Escoge el idioma que quieres
     * @param pais Elige el pais del idioma selecionado por ejemplo para diferenciar US,UK
     *
     */
    protected void cambiarIdioma(String idioma, String pais) {
        // Cambiar el Locale según el idioma seleccionado
        java.util.Locale.setDefault(new java.util.Locale(idioma, pais));

        // Recargar el ResourceBundle con el nuevo idioma
        resources = ResourceBundle.getBundle("com.example.practicaut4.EtiquetasBundle", java.util.Locale.getDefault());

        // Actualizar los textos de los botones según el nuevo idioma
        jugar.setText(resources.getString("jugar_button_text"));
        salir.setText(resources.getString("salir_button_text"));
    }

    /**
     * Este método permite cambiar de pestaña cuando se pulsa al botón de jugar
     * @param event Recoge un evento de ratón
     * @throws IOException
     */
    @FXML
    public void jugar(MouseEvent event) throws IOException {
        // Cambiar a la pantalla de SplashScreen cuando el usuario haga clic en "Jugar"
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        CambiarPantallas.switchScene(currentStage, "SplashScreen.fxml", "Cargando...");
        currentStage.close();
    }

    /**
     * Este método cierra la aplicación al pulsar el botón de salir
     * @param event Recoge un evento de ratón
     * @throws IOException
     */
    @FXML
    protected void salir(MouseEvent event) throws IOException {
        // Cerrar la ventana actual cuando el usuario haga clic en "Salir"
        Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    /**
     * Este método permite abrir el manual de usuario online para saber como jugar
     * @param event Recoge un evento de ratón
     */

    @FXML
    private void abrirAyuda(MouseEvent event) {
        try {
            File file = new File("/home/alumno/IdeaProjects/PracticaUT4/src/Documentacion/Ayuda-Usuario/Ayuda_Usuario.html"); // Reemplaza con la ruta correcta
            if (file.exists()) {
                String url = file.toURI().toString();
                ProcessBuilder pb = new ProcessBuilder("xdg-open", url);
                pb.start();
            } else {
                System.out.println("El archivo HTML no existe.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
