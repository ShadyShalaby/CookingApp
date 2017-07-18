package com.demo.cooking.utilities;

/**
 * Created by hamdy on 11/21/2016.
 */
public class StaticCallException extends Exception {
    public StaticCallException() {
    }

    public StaticCallException(String detailMessage) {
        super(detailMessage);
    }

    public StaticCallException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public StaticCallException(Throwable throwable) {
        super(throwable);
    }
}
