package edu.phystech.hw2.contact;

public class InvalidContactFieldException extends RuntimeException {
    private final String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    InvalidContactFieldException(String fieldName) {
        this.fieldName = fieldName;
    }
}
