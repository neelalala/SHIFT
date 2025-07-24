package ru.shift.filterutility.service;

import ru.shift.filterutility.model.DataType;

public class DataInterpriter {
    public static DataType getType(String string) {
        if(string.matches("(-?[1-9][0-9]*)|0")) {
            return DataType.INT;
        }
        else if(string.matches("-?(0\\.[0-9]+)|([1-9][0-9]*\\.[0-9]+)|([1-9][0-9]*\\.[0-9]+[eE][+-]?[0-9]+)")) {
            return DataType.FLOAT;
        }
        else return DataType.STRING;
    }
}
