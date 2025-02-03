package org.example;

import java.io.*;
import java.util.ArrayList;

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