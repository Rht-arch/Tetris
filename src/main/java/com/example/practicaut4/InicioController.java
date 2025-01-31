package com.example.practicaut4;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Esta clase genera un formulario de inicio de sesión con validaciones y cambio de idioma
 * @author Rafael Haro
 * @version 1.0
 * @since 1.0
 */
public class InicioController {

    /**
     * Este Text muestra "Iniciar Sesión"
     */
    @FXML
    protected Text text;

    /**
     * Este TextField toma los datos de usuario
     */
    @FXML
    protected TextField usuario;

    /**
     * Este campo se encarga de recoger los datos de la contraseña introducida por el usuario
     */
    @FXML
    protected PasswordField contrasena;

    /**
     * Botón que permite entrar al juego en caso de ser correcto el inicio de sesión
     */
    @FXML
    protected Button iniciarSesion;

    /**
     * Este Text genera "O INICIA SESIÓN CON"
     */
    @FXML
    protected Text oic;

    /**
     * Este Text genera "No tienes cuenta"
     */
    @FXML
    protected Text ntc;

    /**
     * Enlace que te traslada al formulario de registro
     */
    @FXML
    protected Hyperlink registrar;

    /**
     * Sirve para almacenar los idiomas y que puedan ser aplicados a las clases
     */
    public ResourceBundle resources;

    /**
     * ComboBox que recoge Strings con los idiomas introducidos
     */
    @FXML
    public ComboBox<String> idiomaComboBox;


    /**
     * Este método genera los campos con el idioma predeterminado
     */
    @FXML
    public void initialize() {
        resources = ResourceBundle.getBundle("com/example/practicaut4/EtiquetasBundle",java.util.Locale.getDefault());
        text.setText(resources.getString("lblText"));
        usuario.setPromptText(resources.getString("txtUser"));
        contrasena.setPromptText(resources.getString("txtPass"));
        iniciarSesion.setText(resources.getString("btnIngreso"));
        oic.setText(resources.getString("oic"));
        ntc.setText(resources.getString("txtRegis"));
        registrar.setText(resources.getString("link"));
    }

    /**
     * Este método permite cambiar al usuario al formulario de registro clickando en enlace
     * @param mouseEvent Recoge eventos causados por el ratón
     * @throws IOException
     */
    @FXML
    public void btnIngresar_MouseClicked(MouseEvent mouseEvent) throws IOException {
        Stage currentStage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();
        CambiarPantallas.switchScene(currentStage, "registro.fxml", "Registrate");
    }

    /**
     * Metodo que se encarga de comprobar  si el usuario y contraseña estan rellenos para un inicio de sesión valido
     * si estan rellenos permite entrar al juego principal, en caso contrario, salta una alerta de campos vacios
     * @param mouseEvent Recoge eventos causados por el ratón
     * @throws IOException
     */
    @FXML
    public void ingresar(MouseEvent mouseEvent) throws IOException {
        String user = usuario.getText();
        String pass = contrasena.getText();
        if(user.trim() != null && !user.trim().isEmpty() && pass.trim() != null && !pass.trim().isEmpty()) {
            Stage currentStage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();
            CambiarPantallas.switchScene(currentStage, "Pagina_Principal.fxml", "Pagina Principal");
        }
        else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle(resources.getString("lblText"));
            alerta.setHeaderText(null);
            alerta.setContentText(resources.getString("alerta_ingreso_invalido"));
            alerta.showAndWait();
        }
    }

    /**
     *  Metodo que cambia el idioma en todos texto que se encuentren en el Scene actual
     */
    @FXML
    public void cambiarIdioma() {
        String idiomaSeleccionado = idiomaComboBox.getSelectionModel().getSelectedItem();

        if ("Español".equals(idiomaSeleccionado)) {
            // Cambiar al idioma español
            Locale.setDefault(new Locale("es", "ES"));
        } else if ("English".equals(idiomaSeleccionado)) {
            // Cambiar al idioma inglés
            Locale.setDefault(Locale.ENGLISH);
        }

        // Recargar el ResourceBundle con el nuevo idioma
        resources = ResourceBundle.getBundle("com/example/practicaut4/EtiquetasBundle", Locale.getDefault());

        // Actualizar los textos de la interfaz
        actualizarTextos();
    }

    /**
     * Este método permite actualizar los textos a inglés
     */
    protected void actualizarTextos() {
        text.setText(resources.getString("lblText"));
        usuario.setPromptText(resources.getString("txtUser"));
        contrasena.setPromptText(resources.getString("txtPass"));
        iniciarSesion.setText(resources.getString("btnIngreso"));
        oic.setText(resources.getString("oic"));
        ntc.setText(resources.getString("txtRegis"));
        registrar.setText(resources.getString("link"));
    }
}