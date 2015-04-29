package com.zemiak.online.service.gmail;

public class MailConnectException extends IllegalStateException {
    public MailConnectException(String message, Throwable origin) {
        super(message, origin);
    }

    public MailConnectException(String message) {
        super(message);
    }
}
