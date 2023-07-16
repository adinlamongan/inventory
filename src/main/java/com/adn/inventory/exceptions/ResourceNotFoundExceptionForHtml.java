package com.adn.inventory.exceptions;

public class ResourceNotFoundExceptionForHtml extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 5210761201055818957L;

    public ResourceNotFoundExceptionForHtml(String message){
        super(message);
    }
}
