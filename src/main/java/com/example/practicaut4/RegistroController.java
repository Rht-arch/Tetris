package com.example.practicaut4;


import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Esta clase genera un formulario de registro básico con validaciones y cambio de idioma
 * @author Rafael Haro
 * @version 1.0
 * @since 1.0
 */
public class RegistroController {
    /**
     * Campo que recoge texto en este caso el nombre
     */
    @FXML
    protected TextField nombre;

    /**
     * Campo que recoge texto en este caso el apellido
     */
    @FXML
    protected TextField apellido;

    /**
     * Campo que genera un calendario para escoger una fecha
     */
    @FXML
    protected DatePicker fechaNacimiento;

    /**
     * Botón que permite seleccionar el género en este caso hombre
     */
    @FXML
    protected RadioButton hombre;

    /**
     * Botón que permite seleccionar el género en este caso mujer
     */
    @FXML
    protected RadioButton mujer;

    /**
     * Campo que recoge texto en este caso el correo electronico
     */
    @FXML
    protected TextField  correo;

    /**
     * Campo que permite aceptar las distintas condiciones
     */
    @FXML
    protected CheckBox condiciones;

    /**
     * Campo que permite introducir una contraseña y la oculta para que no se vea
     */
    @FXML
    protected PasswordField password;

    /**
     * Botón que permite registrar el usuario
     */
    @FXML
    protected Button registrar;

    /**
     * Es un link que permite cambiar al inicio de sesión en caso de tener cuenta
     */
    @FXML
    protected Hyperlink inicio;
    /**
     * Este Text indica "Ya eres usuario"
     */
    @FXML
    protected Text yeu;

    /**
     * Este Text indica "Registrate"
     */
    @FXML
    protected Text registrarse;

    /**
     * Sirve para almacenar los idiomas y que puedan ser aplicados a las clases
     */
    public ResourceBundle resources;

    /**
     * Esta ComboBox recoge Strings con los diferentes idiomas
     */
    @FXML
    public ComboBox<String> idiomaComboBox;

    /**
     * Este método genera los campos con el idioma predeterminado
     */
    @FXML
    public void initialize() {
        resources = ResourceBundle.getBundle("com/example/practicaut4/EtiquetasBundle",java.util.Locale.getDefault());
        registrarse.setText(resources.getString("lblTextR"));
        nombre.setPromptText(resources.getString("txtNombre"));
        apellido.setPromptText(resources.getString("txtApellidos"));
        fechaNacimiento.setPromptText(resources.getString("txtFecha"));
        hombre.setText(resources.getString("txtH"));
        mujer.setText(resources.getString("txtM"));
        password.setPromptText(resources.getString("txtPass2"));
        correo.setPromptText(resources.getString("txtCorreo"));
        condiciones.setText(resources.getString("txtCondis"));
        registrar.setText(resources.getString("btnConfi"));
        yeu.setText(resources.getString("yeu"));
        inicio.setText(resources.getString("link2"));
    }

    /**
     * Válida que los campos estén rellenos, si están rellenos salta una alerta de registro completo,
     * si no te dice que debes completar los campos
     * @param event Recoge una acción realizada por un componente
     * @throws IOException
     */
    @FXML
    public void registroValido(MouseEvent event) throws IOException {
        String username = nombre.getText();
        String ap = apellido.getText();
        LocalDate fecha = fechaNacimiento.getValue();
        RadioButton radio = hombre.isSelected() ? hombre : mujer;
        String mail = correo.getText();
        String pass= password.getText();
        if(validar(username,ap,fecha,radio,mail,pass)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro completo");
            alert.setHeaderText(null);
            alert.setContentText(resources.getString("registro_completo"));
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            CambiarPantallas.switchScene(currentStage, "Pagina_Principal.fxml", "Pagina Principal");
        }else if(!validar(username,ap,fecha,radio,mail,pass)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registro invalido");
            alert.setHeaderText(null);
            alert.setContentText(resources.getString("registro_invalido"));
        }

    }

    /**
     * Cambia de escena cuando se pulsa el enlace
     * @param mouseEvent Recoge una acción realizada por ratón
     * @throws IOException
     */

    public void iniciodesesion(MouseEvent mouseEvent) throws IOException {
        //Para crear una ventana necesitas un nuevo Stage (Escenario)
        Stage currentStage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();
        CambiarPantallas.switchScene(currentStage, "inicio.fxml", "Iniciar Sesión");
    }

    /**
     * Este método se encarga de validar que ningún campo este vacío
     * @param user String que recoge el nombre
     * @param ap   String que recoge el nombre
     * @param fecha LocalDate que indica la fecha de nacimiento
     * @param radio RadioButton que indica el sexo
     * @param mail String que recoge el nombre
     * @param pass String que recoge el nombre
     * @return Devuelve los campos validados
     */
    public boolean validar(String user, String ap, LocalDate fecha, RadioButton radio, String mail, String pass) {
        return user != null && !user.trim().isEmpty() &&
                ap != null && !ap.trim().isEmpty() &&
                fecha != null &&
                radio != null && radio.isSelected() &&
                mail != null && !mail.trim().isEmpty() &&
                pass != null && !pass.trim().isEmpty();
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
        registrarse.setText(resources.getString("lblTextR"));
        nombre.setPromptText(resources.getString("txtNombre"));
        apellido.setPromptText(resources.getString("txtApellidos"));
        fechaNacimiento.setPromptText(resources.getString("txtFecha"));
        hombre.setText(resources.getString("txtH"));
        mujer.setText(resources.getString("txtM"));
        password.setPromptText(resources.getString("txtPass2"));
        correo.setPromptText(resources.getString("txtCorreo"));
        condiciones.setText(resources.getString("txtCondis"));
        registrar.setText(resources.getString("btnConfi"));
        yeu.setText(resources.getString("yeu"));
        inicio.setText(resources.getString("link2"));
    }
}


