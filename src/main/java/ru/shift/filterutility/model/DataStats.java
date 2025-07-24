package ru.shift.filterutility.model;

public class DataStats {
    // Short stats
    private int intCount;
    private int floatCount;
    private int stringCount;

    // Full stats
    private long intMin;
    private long intMax;
    private long intSum;
    private double intAvg;

    private double floatMin;
    private double floatMax;
    private double floatSum;
    private double floatAvg;

    private int stringLengthMin;
    private int stringLengthMax;

    public DataStats() {
        intCount = 0;
        intMin = Long.MAX_VALUE;
        intMax = Long.MIN_VALUE;
        intSum = 0;
        intAvg = 0;

        floatCount = 0;
        floatMin = Double.MAX_VALUE;
        floatMax = Double.MIN_VALUE;
        floatSum = 0;
        floatAvg = 0;

        stringCount = 0;
        stringLengthMin = Integer.MAX_VALUE;
        stringLengthMax = Integer.MIN_VALUE;
    }

    public void add(DataType type, String string) {
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

    public void addInt(long value) {
        intCount++;
        if (value < intMin) {
            intMin = value;
        }
        if (value > intMax) {
            intMax = value;
        }
        intSum += value;

        intAvg = (double) intSum / intCount;
    }

    public void addFloat(double value) {
        floatCount++;
        if (value < floatMin) {
            floatMin = value;
        }
        if (value > floatMax) {
            floatMax = value;
        }
        floatSum += value;

        floatAvg = floatSum / floatCount;
    }

    public void addString(String value) {
        stringCount++;
        if (value.length() < stringLengthMin) {
            stringLengthMin = value.length();
        }
        if (value.length() > stringLengthMax) {
            stringLengthMax = value.length();
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
