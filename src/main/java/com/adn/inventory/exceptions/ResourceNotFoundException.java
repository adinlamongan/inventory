package com.adn.inventory.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    
    /**
     *
     */
    private static final long serialVersionUID = 5210761201055818957L;

    public ResourceNotFoundException(String message){
        super(message);
    }
}
