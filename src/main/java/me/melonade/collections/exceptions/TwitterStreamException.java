package me.melonade.collections.exceptions;

/**
 * Created by damon on 8/30/2016.
 * Contains possible exceptions thrown by TwitterStream
 */
public class TwitterStreamException extends Exception {

    private static final long serialVersionUID = -6342155752798231090L;

    public TwitterStreamException(String message) {
        super(message);
    }

    public TwitterStreamException(String message, Throwable e) {
        super(message, e);
    }

    public TwitterStreamException(Throwable e) {
        super(e);
    }


}
