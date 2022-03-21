package com.bank.account.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Generic Exception Class
 * 
 * @author ND45094N
 *
 */
public class CustomManagmentException extends RuntimeException {

    private static final long serialVersionUID = -2859292084648724403L;
    private final int entityId;
    private final String message;

//    public CustomManagmentException(int entityId, String message) {
//        super(StringUtils.isNoneEmpty(message) ? message : "");
//        this.message = message;
//        this.entityId = entityId;
//    }

//    public CustomManagmentException(Throwable ex) {
//        super(ex);
//        entityId = -1;
//        message = "";
//    }


//    public CustomManagmentException(int entityId) {
//        this.entityId = entityId;
//        message = "";
//    }
//

    public CustomManagmentException(String message) {
        this.entityId = -1;
        this.message = message;
    }

//    public CustomManagmentException() {
//        super();
//        entityId = -1;
//        message = "";
//    }

    public int getEntityId() {
        return entityId;
    }

    public String getMessage() {
        return message;
    }

}
