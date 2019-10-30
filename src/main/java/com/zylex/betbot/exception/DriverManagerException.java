package com.zylex.betbot.exception;

public class DriverManagerException extends OneXBetParserException {

    public DriverManagerException() {
    }

    public DriverManagerException(String message) {
        super(message);
    }

    public DriverManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}