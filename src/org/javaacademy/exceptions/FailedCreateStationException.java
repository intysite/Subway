package org.javaacademy.exceptions;

public class FailedCreateStationException extends RuntimeException{
    public FailedCreateStationException(String message) {
        super(message);
    }
}