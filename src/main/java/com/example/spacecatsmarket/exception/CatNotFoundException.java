package com.example.spacecatsmarket.exception;

public class CatNotFoundException extends RuntimeException {

    private static final String CAT_HAS_NOT_GREETINGS = "Exception %s, that cat you trying to reach, hasn't greeting";

    public CatNotFoundException(String name) {
        super(String.format(CAT_HAS_NOT_GREETINGS, name));
    }
}
