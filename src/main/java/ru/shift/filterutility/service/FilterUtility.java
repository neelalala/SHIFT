package ru.shift.filterutility.service;

import ru.shift.filterutility.model.DataStats;
import ru.shift.filterutility.config.Config;
import ru.shift.filterutility.model.DataType;
import ru.shift.filterutility.model.ExitCode;

import java.io.*;
import java.util.*;


/**
 *
 * Filter lines in specified files by their data types.
 * @author Semen Dutkin s.dutkin@g.nsu.ru
 */
public class FilterUtility {
    private Config config = new Config();



    public void filter(String[] args) {
        configure(args);

        List<BufferedReader> inputFiles = new ArrayList<>();
        for(String path : config.getInputFileNames()) {
            try {
                inputFiles.add(new BufferedReader(new FileReader(path)));
            } catch (Exception e) {
                System.err.println("Couldn't open input file: " + e.getMessage());
            }
        }
        String line;

        Map<DataType, FileWriter> writers = new HashMap<>();

        Map<DataType, String> files = new HashMap<>();

        files.put(DataType.INT, config.getOutputPath() + config.getPrefix() + "integers.txt");
        files.put(DataType.FLOAT, config.getOutputPath() + config.getPrefix() + "floats.txt");
        files.put(DataType.STRING, config.getOutputPath() + config.getPrefix() + "strings.txt");

        DataStats stats = new DataStats();

        while(!inputFiles.isEmpty()) {
            Iterator<BufferedReader> iterator = inputFiles.iterator();
            while(iterator.hasNext()) {
                BufferedReader reader = iterator.next();
                try {
                    line = reader.readLine();
                    if(line != null) {
                        DataType type = DataInterpriter.getType(line);
                        if(writers.get(type) != null) {
                            writers.get(type).write(line + "\n");
                            stats.add(type, line);
                        }
                        else {
                            try {
                                writers.put(type, new FileWriter(files.get(type), config.getShouldAppend()));
                                writers.get(type).write(line + "\n");
                                stats.add(type, line);
                            } catch (IOException e) {
                                System.err.println("Problem with creating output file: " + e.getMessage());
                            }
                        }
                    }
                    else {
                        reader.close();
                        iterator.remove();
                    }
                } catch (IOException e) {
                    System.err.println("Couldn't read line from input file: " + e.getMessage());
                }
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

    private void configure(String[] args) {
        try {
            config.parseArgs(args);
        } catch (IllegalArgumentException e) {
            System.err.println("Couldn't parse arguments, error occurred:" + e.getMessage());
            printUsage();
            System.exit(ExitCode.BAD_ARGS);
        }

    }

    private static void printUsage() {
        System.out.println("Использование: java -jar FilterUtility.jar [опции] inputFile1 inputFile2 ...");
        System.out.println("Опции:");
        System.out.println("    -o <output_dir>     задаёт папку для результатов (по умолчанию текущая папка)");
        System.out.println("    -p <prefix>         задаёт префикс для имён выходных файлов");
        System.out.println("    -a                  режим добавления в существующие файлы");
        System.out.println("    -s                  краткая статистика (только количество)");
        System.out.println("    -f                  полная статистика (для чисел: min, max, сумма, среднее; для строк: длины минимальной и максимальной строк)");
    }
}