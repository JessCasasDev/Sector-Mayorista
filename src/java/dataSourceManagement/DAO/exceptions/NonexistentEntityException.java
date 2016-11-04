package dataSourceManagement.DAO.exceptions;

import java.io.Serializable;

public class NonexistentEntityException extends Exception implements Serializable{

    public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonexistentEntityException(String message) {
        super(message);
    }
}
