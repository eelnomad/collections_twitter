package me.melonade.collections.exceptions;

/**
 * Created by damon on 8/25/2016.
 * Contains possible exceptions thrown when working with Collections
 */
public class CollectionException extends Exception {

    private static final long serialVersionUID = -866596344111914281L;

    public CollectionException(String message) {
        super(message);
    }

    public CollectionException(String message, Throwable e) {
        super(message,e);
    }

    public CollectionException(Throwable e) {
        super(e);
    }

}
