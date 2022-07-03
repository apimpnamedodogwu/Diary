package com.eden.diary.services.DiaryExceptions;

public class InvalidIdentityException extends RuntimeException{

    public InvalidIdentityException (String message) {
        super(message);
    }
}
