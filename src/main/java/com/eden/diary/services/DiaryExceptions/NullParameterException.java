package com.eden.diary.services.DiaryExceptions;

public class NullParameterException extends RuntimeException{

    public NullParameterException (String message) {
        super(message);
    }
}
