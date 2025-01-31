package com.example.practicaut4;

import javafx.scene.paint.Color;
/**
 * Esta clase es la encargada de la creación de las piezas que formaran
 * el juego del tetris
 * @author Rafael Haro
 * @version 1.0
 * @since 1.o
 */
public class Pieza {
    /**
     * Indica la posicion de la pieza
     */
    protected int x, y;

    /**
     * Indica la forma de la pieza
     */
    protected int[][] shape;

    /**
     * Indica el color de la pieza
     */
    protected Color color;

    /**
     * Indica el tipo de la pieza (0 = I, 1 = J, 2 = L, etc.)
     */
    protected int tipo; // Tipo de pieza (0 = I, 1 = J, 2 = L, etc.)

    /**
     * El constructor recoge el tipo de pieza y da una posición x e y iniciales para el
     * posicionarla en el centro, luego según el tipo de pieza se realiza un switch donde se le asigna
     * la forma y el color de la pieza
     * @param tipo Integer indicando el tipo de pieza (0 = I, 1 = J, 2 = L, etc.)
     * @exception IllegalArgumentException Si el tipo de pieza no existe
     */
    public Pieza(int tipo) {
        this.tipo = tipo;
        this.x = 4;
        this.y = 0;


        switch (tipo) {
            case 0:
                this.shape = new int[][]{{1, 1, 1, 1}}; // Pieza I
                this.color = Color.CYAN;
                break;
            case 1:
                this.shape = new int[][]{{1, 1, 1}, {0, 0, 1}}; // Pieza J
                this.color = Color.BLUE;
                break;
            case 2:
                this.shape = new int[][]{{1, 1, 1}, {1, 0, 0}}; // Pieza L
                this.color = Color.ORANGE;
                break;
            case 3:
                this.shape = new int[][]{{1, 1}, {1, 1}}; // Pieza O
                this.color = Color.YELLOW;
                break;
            case 4:
                this.shape = new int[][]{{0, 1, 1}, {1, 1, 0}}; // Pieza S
                this.color = Color.GREEN;
                break;
            case 5:
                this.shape = new int[][]{{0, 1, 0}, {1, 1, 1}}; // Pieza T
                this.color = Color.PURPLE;
                break;
            case 6:
                this.shape = new int[][]{{1, 1, 0}, {0, 1, 1}}; // Pieza Z
                this.color = Color.RED;
                break;
            default:
                throw new IllegalArgumentException("Tipo de pieza no reconocido");
        }
    }

    /**
     * Este metodo recoge la forma de la pieza
     * @return Devuelve la forma de la pieza actual
     */
    public int[][] getShape() {
        return shape;
    }

    /**
     * Este metodo recoge el color de la pieza
     * @return Devuelve el color de la pieza actual
     */
    public Color getColor() {
        return color;
    }

    /**
     * Este metodo recoge el tipo de pieza
     * @return Devuelve el tipo de pieza actual
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Este metodo recoge el valor de la posicion x de la pieza
     * @return Devuelve la posicion
     */
    public int getX() {
        return x;
    }

    /**
     * Este metodo recoge el valor de la posicion y de la pieza
     * @return Devuelve la posicion
     */
    public int getY() {
        return y;
    }

    /**
     * Este metodo se encarga de mover hacia abajo la pieza
     */
    public void moveDown() {
        this.y++;
    }

    /**
     * Este metodo se encarga de mover hacia la izquierda la pieza
     */
    public void moveLeft() {
        this.x--;
    }

    /**
     * Este metodo se encarga de mover hacia la derecha la pieza
     */
    public void moveRight() {
        this.x++;
    }

    /**
     * Este metodo se encarga de girar la pieza 90º
     */
    public void rotate() {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotated = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }

        this.shape = rotated;
    }
}
