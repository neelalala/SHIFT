package ru.shift.filterutility.service;

import org.junit.Test;
import ru.shift.filterutility.model.DataType;

import static org.junit.Assert.*;

public class DataInterpriterTest {

    //INT

    @Test
    public void testIntegers() {
        assertEquals(DataType.INT, DataInterpriter.getType("1"));
        assertEquals(DataType.INT, DataInterpriter.getType("0"));
        assertEquals(DataType.INT, DataInterpriter.getType("123"));
        assertEquals(DataType.INT, DataInterpriter.getType("999999999"));
    }

    @Test
    public void testIntegerSigns() {
        assertEquals(DataType.INT, DataInterpriter.getType("+1"));
        assertEquals(DataType.INT, DataInterpriter.getType("-1"));
        assertEquals(DataType.INT, DataInterpriter.getType("+0"));
        assertEquals(DataType.INT, DataInterpriter.getType("-0"));
        assertEquals(DataType.INT, DataInterpriter.getType("+123"));
        assertEquals(DataType.INT, DataInterpriter.getType("-456"));
    }

    @Test
    public void testLeadingZeros() {
        assertEquals(DataType.INT, DataInterpriter.getType("01"));
        assertEquals(DataType.INT, DataInterpriter.getType("001"));
        assertEquals(DataType.INT, DataInterpriter.getType("+01"));
        assertEquals(DataType.INT, DataInterpriter.getType("-01"));
        assertEquals(DataType.INT, DataInterpriter.getType("00"));
        assertEquals( DataType.INT, DataInterpriter.getType("000"));
    }

    @Test
    public void testIntegerEdgeCases() {
        assertEquals(DataType.INT, DataInterpriter.getType("0"));
        assertEquals(DataType.INT, DataInterpriter.getType("9"));
        assertEquals(DataType.INT, DataInterpriter.getType("2147483647"));
        assertEquals(DataType.INT, DataInterpriter.getType("-2147483648"));
        assertEquals(DataType.INT, DataInterpriter.getType("9999999999999999999999999999999"));
    }

    @Test
    public void testInvalidIntegers() {
        assertEquals(DataType.STRING, DataInterpriter.getType(""));
        assertEquals(DataType.STRING, DataInterpriter.getType("+"));
        assertEquals(DataType.STRING, DataInterpriter.getType("-"));
        assertEquals(DataType.STRING, DataInterpriter.getType("+ 1"));
        assertEquals(DataType.STRING, DataInterpriter.getType("+-1"));
        assertEquals(DataType.STRING, DataInterpriter.getType("1+"));
        assertEquals(DataType.STRING, DataInterpriter.getType("1+2"));
    }

    // FLOAT

    @Test
    public void testBasicFloats() {
        assertEquals(DataType.FLOAT, DataInterpriter.getType("0.1"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("0.5"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("1.0"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("123.456"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("123456789123456789.123456789123456789"));
    }

    @Test
    public void testFloatSigns() {
        assertEquals(DataType.FLOAT, DataInterpriter.getType("+1.5"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("-1.5"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("+0.1"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("-0.1"));
    }

    @Test
    public void testScientificNotation() {
        assertEquals(DataType.FLOAT, DataInterpriter.getType("1.5e10"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("1.5E10"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("1.5e+10"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("1.5e-10"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("1.5E+10"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("1.5E-10"));
    }

    @Test
    public void testScientificNotationWithSigns() {
        assertEquals(DataType.FLOAT, DataInterpriter.getType("-1.5e10"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("+1.5e10"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("-1.5e+10"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("+1.5e-10"));
    }

    @Test
    public void testScientificNotationEdgeCases() {
        assertEquals(DataType.FLOAT, DataInterpriter.getType("9999999999.9999999999e9999999999"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("1.5e0"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("1.5e-0"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("1.5e+0"));
    }

    @Test
    public void testInvalidScientificNotation() {
        assertEquals(DataType.STRING, DataInterpriter.getType("0.1e5"));
        assertEquals(DataType.STRING, DataInterpriter.getType("1e5"));
        assertEquals(DataType.STRING, DataInterpriter.getType("1.5e"));
        assertEquals(DataType.STRING, DataInterpriter.getType("1.5e+"));
        assertEquals(DataType.STRING, DataInterpriter.getType("1.5e-"));
        assertEquals(DataType.STRING, DataInterpriter.getType("1.5ee5"));
    }

    @Test
    public void testFloatZeroCases() {
        assertEquals(DataType.FLOAT, DataInterpriter.getType("0.0"));
        assertEquals( DataType.FLOAT, DataInterpriter.getType("0.000"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("0.001"));
        assertEquals(DataType.FLOAT, DataInterpriter.getType("1.500"));
    }

    @Test
    public void testInvalidFloats() {
        assertEquals(DataType.STRING, DataInterpriter.getType("."));
        assertEquals(DataType.STRING, DataInterpriter.getType(".5"));
        assertEquals(DataType.STRING, DataInterpriter.getType("5."));
        assertEquals(DataType.STRING, DataInterpriter.getType("1..5"));
        assertEquals(DataType.STRING, DataInterpriter.getType("01.5"));
    }

    // STRING

    @Test
    public void testBasicStrings() {
        assertEquals(DataType.STRING, DataInterpriter.getType("hello"));
        assertEquals(DataType.STRING, DataInterpriter.getType("hello world"));
        assertEquals(DataType.STRING, DataInterpriter.getType("a"));
        assertEquals(DataType.STRING, DataInterpriter.getType(" "));
    }

    @Test
    public void testSpecialCharacters() {
        assertEquals(DataType.STRING, DataInterpriter.getType("_"));
        assertEquals(DataType.STRING, DataInterpriter.getType("#"));
        assertEquals(DataType.STRING, DataInterpriter.getType("@"));
        assertEquals(DataType.STRING, DataInterpriter.getType("!"));
        assertEquals(DataType.STRING, DataInterpriter.getType("?"));
        assertEquals(DataType.STRING, DataInterpriter.getType("!@#$%"));
    }

    @Test
    public void testWhitespaceInNumbers() {
        assertEquals(DataType.STRING, DataInterpriter.getType(" 123"));
        assertEquals(DataType.STRING, DataInterpriter.getType("123 "));
        assertEquals(DataType.STRING, DataInterpriter.getType("1 23"));
        assertEquals(DataType.STRING, DataInterpriter.getType("\t123"));
        assertEquals(DataType.STRING, DataInterpriter.getType("123\n"));
        assertEquals(DataType.STRING, DataInterpriter.getType("  123  "));
    }

    @Test
    public void testEmpty() {
        assertEquals(DataType.STRING, DataInterpriter.getType(""));
    }

    @Test
    public void testVeryLongNumbers() {
        StringBuilder longInt = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longInt.append("1");
        }

        assertEquals(DataType.INT, DataInterpriter.getType(longInt.toString()));

        String longFloat = "1." + longInt + "e123";
        assertEquals(DataType.FLOAT, DataInterpriter.getType(longFloat));
    }
}
