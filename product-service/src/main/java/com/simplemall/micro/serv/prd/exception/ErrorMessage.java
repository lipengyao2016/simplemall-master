package com.simplemall.micro.serv.prd.exception;

public class ErrorMessage {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public  ErrorMessage(String msg)
    {
        this.message = msg;
    }


}
