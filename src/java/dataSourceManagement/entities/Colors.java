/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataSourceManagement.entities;

import java.io.Serializable;

/**
 *
 * @author JuanCamilo
 */
public class Colors implements Serializable{

    public static final String YELLOW = "Amarillo";
    public static final String PURPLE = "Morado";
    public static final String GREEN = "Verde";
    public static final String RED = "Rojo";
    public static final String GRAY = "Gris";
    public static final String BLACK = "Negro";
    public static final String WHITE = "Blanco";
    public static final String GOLDEN = "Dorado";
    public static final String SILVER = "Plateado";
        public static final String BLUE = "Azul";
    
    private static final String[] colors = new String[]{
        RED, GREEN, PURPLE, YELLOW, SILVER, GOLDEN, WHITE, BLACK, GRAY,BLUE};

    private Colors() {
        //empty constructor
    }

    public static String[] getAvailableColors() {
        return colors.clone();
    }

}
