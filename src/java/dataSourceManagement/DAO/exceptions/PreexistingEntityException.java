package dataSourceManagement.DAO.exceptions;

import java.io.Serializable;

public class PreexistingEntityException extends Exception implements Serializable{

    public PreexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public PreexistingEntityException(String message) {
        super(message);
    }
}
