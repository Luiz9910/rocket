package com.gestaovagas.vagas.exceptions;

public class UserFoundException extends RuntimeException{
    public UserFoundException() {
        super("Usuario ja existe");
    }
}
