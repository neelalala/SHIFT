package ru.shift.filterutility.service;

import ru.shift.filterutility.exception.NoInputFilesException;
import ru.shift.filterutility.model.DataStats;
import ru.shift.filterutility.config.Config;
import ru.shift.filterutility.model.DataType;

import java.io.*;
import java.util.*;


/**
 *
 * Filter lines in specified files by their data types.
 * @author Semen Dutkin s.dutkin@g.nsu.ru
 */
public class FilterUtility {
    private static final String integerFileName = "integers.txt";
    private static final String floatFileName = "floats.txt";
    private static final String stringFileName = "strings.txt";

    private final Config config = new Config();

    public void filter() throws NoInputFilesException {
        List<BufferedReader> inputFiles = new ArrayList<>();
        for(String path : config.getInputFileNames()) {
            try {
                inputFiles.add(new BufferedReader(new FileReader(path)));
            } catch (Exception e) {
                System.err.println("Couldn't open input file: " + e.getMessage());
            }
        }

        if (inputFiles.isEmpty() ) {
            throw new NoInputFilesException("Couldn't open any input file");
        }

        Map<DataType, FileWriter> writers = new EnumMap<>(DataType.class);

        Map<DataType, String> files = new HashMap<>();

        files.put(DataType.INT, config.getOutputPath() + config.getPrefix() + integerFileName);
        files.put(DataType.FLOAT, config.getOutputPath() + config.getPrefix() + floatFileName);
        files.put(DataType.STRING, config.getOutputPath() + config.getPrefix() + stringFileName);

        DataStats stats = new DataStats();

        for (BufferedReader reader : inputFiles) {
            try (reader) {
                String line;
                while ((line = reader.readLine()) != null) {
                    DataType type = DataInterpriter.getType(line);

                    FileWriter writer = writers.computeIfAbsent(type, t -> {
                        try {
                            return new FileWriter(files.get(t), config.getShouldAppend());
                        } catch (IOException e) {
                            System.err.println("Failed to create writer for " + t.name() + ": " + e.getMessage());
                            return null;
                        }
                    });

                    if (writer != null) {
                        writer.write(line + "\n");
                    }

                    try {
                        stats.add(type, line);
                    } catch (NumberFormatException e) {
                        System.err.println("Couldn't parse line " + line + " to " + type.name() + ".");
                    }
                }
            } catch (IOException e) {
                System.err.println("Couldn't read line from input file: " + e.getMessage());
            }
        }

        for(FileWriter writer : writers.values()) {
            if(writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Couldn't close output file: " + e.getMessage());
                }
            }
        }
        if(config.getShowStats()) {
            stats.printStats(config.getFullStats());
        }
    }

    public void configure(String[] args) throws IllegalArgumentException, NoInputFilesException {
        try {
            config.parseArgs(args);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Couldn't parse arguments, error occurred", e);
        }
    }

    public static void printUsage() {
        System.out.println("Использование: java -jar FilterUtility.jar [опции] inputFile1 inputFile2 ...");
        System.out.println("Опции:");
        System.out.println("\t-o <output_dir>     задаёт папку для результатов (по умолчанию текущая папка)");
        System.out.println("\t-p <prefix>         задаёт префикс для имён выходных файлов");
        System.out.println("\t-a                  режим добавления в существующие файлы");
        System.out.println("\t-s                  краткая статистика (только количество)");
        System.out.println("\t-f                  полная статистика (для чисел: min, max, сумма, среднее; для строк: длины минимальной и максимальной строк)");
    }
}