package com.czarny.company_backend.domain.common;

public class ItemNotFoundException extends Exception {

    public ItemNotFoundException() {
    }
    public ItemNotFoundException(final String message) {
        super(message);
    }
    public ItemNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
    public ItemNotFoundException(final Throwable cause) {
        super(cause);
    }
}
