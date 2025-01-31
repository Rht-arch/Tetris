package com.example.practicaut4;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ResourceBundle;

/**
 * Esta clase se encarga de la creación de todo lo relacionado con el tetris
 * fuera de las piezas, es decir , tablero, nivel,puntuacion y le da funcionalidad
 * a la clase Pieza
 * @author Rafael Haro
 * @version 1.0
 * @since 1.0
 */
public class TetrisController {
    /**
     * Genera un panel en malla que sera el tablero
     */
    @FXML
    protected GridPane tablero;

    /**
     * Etiqueta que indica el nivel
     */
    @FXML
    protected Label nivel;

    /**
     * Etiqueta que indica la puntuación
     */
    @FXML
    protected Label puntuacion;
    /**
     * @deprecated no usado actualmente
     */
    @FXML
    protected Pane siguiente;
    /**
     * Integer que indica la puntuación actual del jugador
     */
    protected int puntuacionActual = 0;

    /**
     * Integer que indica el nivel actual del jugador
     */
    protected int nivelActual = 1;

    /**
     * Integer que recoge las lineas eliminadas por el jugador
     */
    protected int lineasLimpiadas = 0;

    /**
     * Añade el tiempo necesario para simular las animaciones de las piezas
     */
    protected Timeline timeline;

    /**
     * Indica el número de espacios que llevara la malla
     */
    protected Rectangle[][] celdas = new Rectangle[20][10];

    /**
     * Pinta la pieza en las celdas según la pieza que sea
     */
    protected Color[][] estado = new Color[20][10];

    /**
     * @deprecated Aún no esta implementado
     */
    protected Pieza siguientePieza;

    /**
     * Sirve para almacenar los idiomas y que puedan ser aplicados a las clases
     */
    protected ResourceBundle resources;

    /**
     * Determina la pieza actual
     */
    protected Pieza pieza;

    /**
     * Metodo que inicializa el tablero en negro y asigna una velocidad de 0.5s a la caida de la pieza
     * aparte modifica a ingles o español los textos del tablero
     */
    @FXML
    public void initialize() {
        for(int row = 0; row < 20; row++) {
            for(int col = 0; col < 10; col++) {
                Rectangle cell = new Rectangle(30,30);
                cell.getStyleClass().add("cell");
                celdas[row][col] = cell;
                estado[row][col] = Color.BLACK;
                tablero.add(cell, col, row);
            }
        }
        tablero.setFocusTraversable(true);
        nuevaPieza();
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> moverAbajo()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        //Idioma
        resources = ResourceBundle.getBundle("com.example.practicaut4.EtiquetasBundle", java.util.Locale.getDefault());
        puntuacion.setText(resources.getString("puntos_text"));
        nivel.setText(resources.getString("nivel_text"));
        // Configura el foco en el tablero para capturar las teclas
        tablero.setFocusTraversable(true);
        tablero.requestFocus();
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
        nivel.setText(resources.getString("nivel_text"));
        puntuacion.setText(resources.getString("puntos_text"));
    }

    /**
     * Metodo que se encarga de asignar las teclas a los movimientos de las piezas y el cambio de idioma
     * según la tecla pulsada realizara métodos determinados
     * @param event evento  Funciona segun la tecla que pulses
     */
    public void handleKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code == KeyCode.LEFT) {
            moverIzquierda();
        }else if(code == KeyCode.RIGHT) {
            moverDerecha();
        }else if(code == KeyCode.UP) {
            rotarPieza();
        }else if(code == KeyCode.DOWN) {
            moverAbajo();
        }else if(code == KeyCode.I) {
            cambiarIdioma("en","US");
        }else if(code == KeyCode.E) {
            cambiarIdioma("es","ES");
        }
    }

    /**
     * Este metodo se encarga de generar un numero aleatorio,es decir, un tipo de pieza
     * crea esa pieza y tras colocarse la pieza,se actualiza el tablero con la pieza colocada
     */
    protected void nuevaPieza() {
        int tipo = (int) (Math.random() * 7); // Genera un tipo de pieza aleatoria
        pieza = new Pieza(tipo); // Crear una nueva pieza
        actualizarTablero(); // Se actualizará después de colocar la pieza en el tablero
    }

    /**
     * Metodo que gestiona el movimiento de las fichas, en caso de que no se pueda mover se que coloca encima de la pieza
     * @param x int Determina la posicion x de la ficha
     * @param y int Determina la posicion y de la ficha
     * @param forma int[][] Determina la forma de la ficha
     * @return true si la ficha puede colocarse
     */
    protected boolean puedeMover(int x, int y, int[][] forma) {
        for (int row = 0; row < forma.length; row++) {
            for (int col = 0; col < forma[row].length; col++) {
                if (forma[row][col] == 1) {
                    int nuevoX = x + col;
                    int nuevoY = y + row;

                    if (nuevoX < 0 || nuevoX >= 10 || nuevoY < 0 || nuevoY >= 20 || estado[nuevoY][nuevoX] != Color.BLACK) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * Metodo que obtiene la forma de la pieza, realiza un bucle que revisa las celdas en negro
     * y si se coloca obtiene las coordenadas y verifica que las coordenadas no están fuera de
     * tablero
     */
    protected void colocarPieza() {
        int[][] formas = pieza.getShape(); // Obtener la forma de la pieza
        for (int row = 0; row < formas.length; row++) {
            for (int col = 0; col < formas[row].length; col++) {
                if (formas[row][col] == 1) { // Si la celda es parte de la pieza
                    int tableroRow = pieza.getY() + row; // Coordenada en Y del tablero
                    int tableroCol = pieza.getX() + col; // Coordenada en X del tablero

                    // Verificar que las coordenadas estén dentro del rango del tablero
                    if (tableroRow >= 0 && tableroRow < 20 && tableroCol >= 0 && tableroCol < 10) {
                        estado[tableroRow][tableroCol] = pieza.getColor();
                    }
                }
            }
        }
    }

    /**
     * Metodo que limpia todas las filas si se completan más de 1
     */
    protected void limpiarTodas(){
        for(int row = 0; row < 20; row++){
            boolean lineaEntera=true;
            for(int col = 0; col < 10; col++){
                if(estado[row][col] == Color.BLACK){
                    lineaEntera=false;
                    break;
                }
            }
            if(lineaEntera){
                limpiarLinea(row);
            }
        }
    }

    /**
     * Este método elimina la fila en el momento que se completa,aumenta las lineas limpias
     * y incrementa la puntación del usuario. Tras eso actualiza la interfaz con la puntuación
     * nueva y el nivel en caso de aumentar
     * @param fila int indica la fila que debera eliminarse si esta llena
     */
    public void limpiarLinea(int fila){
        for(int i = fila; i > 0; i--){
            for(int j = 0; j < 10; j++){
                estado[i][j] = estado[i-1][j];
            }
        }
        for(int j = 0; j < 10; j++){
            estado[0][j] = Color.BLACK;
        }
        lineasLimpiadas++; // Incrementa el contador de líneas limpiadas
        puntuacionActual += 100 * nivelActual; // Incrementa puntuación (escala con el nivel)
        actualizarPuntuacionYNivel(); // Actualiza la interfaz gráfica
    }

    /**
     * Método que se encarga de actualizar el tablero una vez se realice cualquier cambio de estado(colocar pieza, eliminar linea, rotar)
     */
    public void actualizarTablero() {
        for (int row = 0; row < 20; row++) {
            for (int col = 0; col < 10; col++) {
                celdas[row][col].setFill(estado[row][col]);
            }
        }
        // Representar la pieza actual
        int[][] forma = pieza.getShape();
        for (int row = 0; row < forma.length; row++) {
            for (int col = 0; col < forma[row].length; col++) {
                if (forma[row][col] == 1) {
                    int x = pieza.getX() + col;
                    int y = pieza.getY() + row;
                    if (y >= 0 && y < 20 && x >= 0 && x < 10) {
                        celdas[y][x].setFill(pieza.getColor());
                    }
                }
            }
        }
    }

    /**
     * Método que gestiona la lógica del movimiento hacia abajo
     */
    public void moverAbajo() {
        if (puedeMover(pieza.getX(), pieza.getY() + 1, pieza.getShape())) {
            pieza.moveDown();
        } else {
            // Coloca la pieza actual y genera una nueva
            colocarPieza();
            limpiarTodas();
            nuevaPieza();
            // Verifica si hay un bloqueo (fin del juego)
            if (!puedeMover(pieza.getX(), pieza.getY(), pieza.getShape())) {
                timeline.stop();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fin del juego");
                alert.setHeaderText(null);
                alert.setContentText("Has perdido");

            }
        }
        actualizarTablero();
    }
    /**
     * Método que gestiona la lógica del movimiento hacia la izquierda
     */
    public void moverIzquierda(){
        if(puedeMover(pieza.getX()-1, pieza.getY(), pieza.getShape())) { // Movimiento correcto en el eje X
            pieza.moveLeft(); // Esto mueve la pieza a la izquierda
            actualizarTablero(); // Refresca el tablero
        }
    }
    /**
     * Método que gestiona la lógica del movimiento haciala derecha
     */
    public void moverDerecha(){
        if(puedeMover(pieza.getX()+1, pieza.getY(), pieza.getShape())) { // Movimiento correcto en el eje X
            pieza.moveRight(); // Esto mueve la pieza a la derecha
            actualizarTablero(); // Refresca el tablero
        }
    }
    /**
     * Método que gestiona la lógica de la rotación de la pieza, si no puede rotar
     * vuelve a la posición original
     */
    public void rotarPieza(){
        pieza.rotate(); // Rota la pieza actual
        if(puedeMover(pieza.getX(), pieza.getY(), pieza.getShape())) {
            // Si la nueva rotación es válida, no hacemos nada más, ya que la pieza se ha rotado
        } else {
            // Si no es válida, deshaces la rotación
            pieza.rotate(); // Rota de nuevo para devolverla a su orientación inicial
        }
        actualizarTablero(); // Refresca el tablero después de rotar
    }

    /**
     * Método que se encarga de actualizar la puntuación y el nivel
     */
    protected void actualizarPuntuacionYNivel() {
        // Incrementa nivel cada 10 líneas limpiadas
        if (lineasLimpiadas >= nivelActual * 10) {
            nivelActual++;
            ajustarVelocidad();
        }

        // Actualiza las etiquetas de la interfaz
        puntuacion.setText("Puntuación: " + puntuacionActual);
        nivel.setText("Nivel: " + nivelActual);
    }

    /**
     * Este método se encargar de aumenta la velocidad cada vez que se aumenta de nivel
     */
    protected void ajustarVelocidad() {
        int velocidadBase = 1000; // Milisegundos
        int decremento = 100; // Disminución por nivel
        int velocidad = Math.max(200, velocidadBase - (nivelActual - 1) * decremento);

        // Reinicia el temporizador con la nueva velocidad
        timeline.stop();
        timeline = new Timeline(new KeyFrame(Duration.millis(velocidad), e -> moverAbajo()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

}


