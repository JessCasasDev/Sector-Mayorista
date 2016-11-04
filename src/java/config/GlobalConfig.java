/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.io.Serializable;

/**
 *
 * @author juan
 */
public class GlobalConfig implements Serializable{

    public static final String PERSISTENCE_UNIT = "automarketPU";
    public static int session_counter;

}
