package dataSourceManagement.DAO.exceptions;

import java.io.Serializable;

public class RollbackFailureException extends Exception implements Serializable{

    public RollbackFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public RollbackFailureException(String message) {
        super(message);
    }
}
