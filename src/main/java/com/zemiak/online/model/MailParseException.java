package com.zemiak.online.model;

public class MailParseException extends IllegalStateException {
    public MailParseException(String message, Throwable origin) {
        super(message, origin);
    }

    public MailParseException(String message) {
        super(message);
    }
}
