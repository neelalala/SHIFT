package ru.shift.filterutility.service;

import ru.shift.filterutility.model.DataType;

import java.util.regex.Pattern;

public class DataInterpriter {
    private static final Pattern INT_PATTERN = Pattern.compile("([+-]?[1-9][0-9]*)|0");
    private static final Pattern FLOAT_PATTERN = Pattern.compile("[+-]? ( (0\\.[0-9]+) | ([1-9][0-9]*\\.[0-9]+) | ([1-9][0-9]*\\.[0-9]+[eE][+-]?[0-9]+) )");

    public static DataType getType(String string) {
        if( INT_PATTERN.matcher(string).matches() ) {
            return DataType.INT;
        }
        else if( FLOAT_PATTERN.matcher(string).matches() ) {
            return DataType.FLOAT;
        }
        else return DataType.STRING;
    }
}
