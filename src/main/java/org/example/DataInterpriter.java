package org.example;

public class DataInterpriter {
    enum Type {
        INT,
        FLOAT,
        STRING
    }

    public static Type dataType(String string) {
        if(string.matches("(-?[1-9][0-9]*)|0")) {
            return Type.INT;
        }
        if(string.matches("-?(0\\.[0-9]+)|([1-9][0-9]*\\.[0-9]+)|([1-9][0-9]*\\.[0-9]+[eE][+-]?[0-9]+)")) {
            return Type.FLOAT;
        }
        return Type.STRING;
    }
}
