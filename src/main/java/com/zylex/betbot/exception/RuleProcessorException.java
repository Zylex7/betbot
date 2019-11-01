package com.zylex.betbot.exception;

public class RuleProcessorException extends OneXBetParserException {

    public RuleProcessorException() {
    }

    public RuleProcessorException(String message) {
        super(message);
    }

    public RuleProcessorException(String message, Throwable cause) {
        super(message, cause);
    }
}
