/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.Services;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mssg_
 */
public class AutoMResponseMessage {
    private String response;
    private List<Map<String,String>> data;
    private boolean succesfull;

    public AutoMResponseMessage(String response, List<Map<String, String>> data, boolean succesfull) {
        this.response = response;
        this.data = data;
        this.succesfull = succesfull;
    }

    @Override
    public String toString() {
        return "hola"; //To change body of generated methods, choose Tools | Templates.
    }
    

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getData() {
        //    return data;
        return "hola";
        //return Arrays.toString(data.toArray(new Map[data.size()]));
    }

    public void setData(List<Map<String, String>> data) {
        this.data = data;
    }

    public boolean isSuccesfull() {
        return succesfull;
    }

    public void setSuccesfull(boolean succesfull) {
        this.succesfull = succesfull;
    }
    
    
}
