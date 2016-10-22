/*
 * (c) Copyright 2001-2015 COMIT AG
 * All Rights Reserved.
 */
package com.vave.getbike.exception;

/**
 * @author sivanookala
 * @version 1.0, 16-Dec-2015
 */
public class AdzShopException extends RuntimeException {

    private static final long serialVersionUID = -4103999309724194764L;
    Exception rootCause;
    String customMessage;

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public AdzShopException(Exception rootCause) {
        super();
        this.rootCause = rootCause;
    }

    public AdzShopException(String customMessage) {
        super();
        this.customMessage = customMessage;
    }

    public Exception getRootCause() {
        return rootCause;
    }
}
