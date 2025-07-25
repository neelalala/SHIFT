package ru.shift.filterutility.model;

public class DataStats {
    // Short stats
    private int intCount;
    private int floatCount;
    private int stringCount;

    // Full stats
    private Long intMin;
    private Long intMax;
    private Long intSum;
    private Double intAvg;

    private Double floatMin;
    private Double floatMax;
    private Double floatSum;
    private Double floatAvg;

    private Integer stringLengthMin;
    private Integer stringLengthMax;

    public DataStats() {
        intCount = 0;
        intMin = null;
        intMax = null;
        intSum = 0L;
        intAvg = 0D;

        floatCount = 0;
        floatMin = null;
        floatMax = null;
        floatSum = 0D;
        floatAvg = 0D;

        stringCount = 0;
        stringLengthMin = null;
        stringLengthMax = null;
    }

    public void add(DataType type, String string) throws NumberFormatException {
        switch (type) {
            case INT:
                long intVal = Long.parseLong(string);
                addInt(intVal);
                break;
            case FLOAT:
                double floatVal = Double.parseDouble(string);
                addFloat(floatVal);
                break;
            case STRING:
                addString(string);
                break;
        }
    }

    private void addInt(long value) {
        intCount++;

        if (intMin == null) intMin = value;
        if (intMax == null) intMax = value;

        if (value < intMin) {
            intMin = value;
        }
        if (value > intMax) {
            intMax = value;
        }
        intSum += value;

        intAvg = (double) intSum / intCount;
    }

    private void addFloat(double value) {
        floatCount++;

        if (floatMin == null) floatMin = value;
        if (floatMax == null) floatMax = value;

        if (value < floatMin) {
            floatMin = value;
        }
        if (value > floatMax) {
            floatMax = value;
        }
        floatSum += value;

        floatAvg = floatSum / floatCount;
    }

    private void addString(String value) {
        stringCount++;

        int len = value.length();

        if (stringLengthMin == null) stringLengthMin = len;
        if (stringLengthMax == null) stringLengthMax = len;

        if (len < stringLengthMin) {
            stringLengthMin = len;
        }
        if (len > stringLengthMax) {
            stringLengthMax = len;
        }
    }

    public void printStats(boolean full) {
        if (full) {
            System.out.println("Integers: ");
            System.out.println("\tCount:" + intCount);
            System.out.println("\tMinimal: " + intMin);
            System.out.println("\tMaximal: " + intMax);
            System.out.println("\tAverage: " + intAvg);
            System.out.println("\tSum: " + intSum);
            System.out.println("Floats: ");
            System.out.println("\tCount:" + floatCount);
            System.out.println("\tMinimal: " + floatMin);
            System.out.println("\tMaximal: " + floatMax);
            System.out.println("\tAverage: " + floatAvg);
            System.out.println("\tSum: " + floatSum);
            System.out.println("Strings: ");
            System.out.println("\tCount:" + stringCount);
            System.out.println("\tMinimal length: " + stringLengthMin);
            System.out.println("\tMaximal length: " + stringLengthMax);
        }
        else {
            System.out.println("Integers count: " + intCount);
            System.out.println("Floats count: " + floatCount);
            System.out.println("Strings count: " + stringCount);
        }
    }
}
