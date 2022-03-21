package com.bank.account.exception;

public class CustomFunctionalException extends CustomManagmentException {

    private static final long serialVersionUID = 9107184852439734726L;

    public CustomFunctionalException() {
        super("Field Not Valid");
    }

    public CustomFunctionalException(String message) {
        super(message);
    }

}
