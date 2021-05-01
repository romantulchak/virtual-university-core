package com.romantulchak.virtualuniversity.exception;

public class ExportToPdfFailedException extends RuntimeException{
    public ExportToPdfFailedException(){
        super("Export to pdf failed");
    }
}
