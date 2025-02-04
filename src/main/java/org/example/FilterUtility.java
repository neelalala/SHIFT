package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FilterUtility {
    public static void main(String[] args) {
        Config config = new Config();
        try {
            config.parseArgs(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            printUsage();
            return;
        }
        ArrayList<BufferedReader> inputFiles = new ArrayList<>();
        for(String path : config.getInputFiles()) {
            try {
                inputFiles.add(new BufferedReader(new FileReader(path)));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        String line;

        Map<DataInterpriter.Type, FileWriter> writers = new HashMap<>();

        Map<DataInterpriter.Type, String> files = new HashMap<>();

        files.put(DataInterpriter.Type.INT, config.getOutputPath() + config.getPrefix() + "integers.txt");
        files.put(DataInterpriter.Type.FLOAT, config.getOutputPath() + config.getPrefix() + "floats.txt");
        files.put(DataInterpriter.Type.STRING, config.getOutputPath() + config.getPrefix() + "strings.txt");

        DataStats stats = new DataStats();

        while(!inputFiles.isEmpty()) {
            Iterator<BufferedReader> iterator = inputFiles.iterator();
            while(iterator.hasNext()) {
                BufferedReader reader = iterator.next();
                try {
                    line = reader.readLine();
                    if(line != null) {
                        DataInterpriter.Type type = DataInterpriter.dataType(line);
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
                        reader.close(); // не закрывает ссылку
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
                    writer.close(); // не закрывает ссылку
                } catch (IOException e) {
                    System.err.println("Couldn't close output file: " + e.getMessage());
                }
            }
        }
        if(config.getShowStats()) {
            stats.printStats(config.getFullStats());
        }
    }

    private static void printUsage() {
        System.out.println("Использование: java FilterUtility [опции] inputFile1 inputFile2 ...");
        System.out.println("Опции:");
        System.out.println("    -o <output_dir>     задаёт папку для результатов (по умолчанию текущая папка)");
        System.out.println("    -p <prefix>         задаёт префикс для имён выходных файлов");
        System.out.println("    -a                  режим добавления в существующие файлы");
        System.out.println("    -s                  краткая статистика (только количество)");
        System.out.println("    -f                  полная статистика (для чисел: min, max, сумма, среднее; для строк: длина минимальной и максимальной строки)");
    }
}