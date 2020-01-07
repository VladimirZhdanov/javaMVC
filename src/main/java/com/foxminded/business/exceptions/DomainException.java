package com.foxminded.business.exceptions;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
public class DomainException extends RuntimeException {

    /**
     * Constructor of the class.
     *
     * @param msg - massage
     */
    public DomainException(String msg) {
        super(msg);
    }
}
