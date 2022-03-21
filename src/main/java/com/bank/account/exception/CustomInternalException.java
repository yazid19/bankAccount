package com.bank.account.exception;


public class CustomInternalException extends CustomManagmentException {

    private static final long serialVersionUID = 9107184852439734726L;

    public CustomInternalException() {
        super("Field Not Valid");
    }

    public CustomInternalException(String message) {
        super(message);
    }


}
