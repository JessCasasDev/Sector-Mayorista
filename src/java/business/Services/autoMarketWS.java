/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business.Services;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author mssg_
 */
@WebService(serviceName = "autoMarketWS")
public class autoMarketWS {

    /**
     * This is a sample web service operation
     
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }*/
    @WebMethod(operationName = "checkAvailableIds")
    public AutoMResponseMessage checkAvailableIds (String userName, String password){
        MakeTransaction mkt    = new MakeTransaction();
        return mkt.make(userName,password);
    }
}
