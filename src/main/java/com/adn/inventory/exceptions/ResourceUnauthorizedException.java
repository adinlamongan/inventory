package com.adn.inventory.exceptions;

public class ResourceUnauthorizedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 5210761201055818957L;

    public ResourceUnauthorizedException(String message){
        super(message);
    }
}
