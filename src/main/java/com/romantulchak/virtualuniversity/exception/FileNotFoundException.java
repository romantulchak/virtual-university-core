package com.romantulchak.virtualuniversity.exception;

public class FileNotFoundException extends RuntimeException{
    public FileNotFoundException(){
        super("File not found");
    }
}
