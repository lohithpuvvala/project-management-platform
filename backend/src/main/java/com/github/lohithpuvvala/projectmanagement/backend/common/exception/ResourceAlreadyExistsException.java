package com.github.lohithpuvvala.projectmanagement.backend.common.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(String resource,String field,Object value) {
        super("%s with %s '%s' already exists.".formatted(resource,field,value));
    }
}
