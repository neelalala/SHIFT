package ru.shift.filterutility.config;

import ru.shift.filterutility.exception.NoInputFilesException;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private final List<String> inputFileNames = new ArrayList<>();
    private String prefix = "";
    private String outputPath = "";
    private boolean shouldAppend = false;
    private boolean showStats = false;
    private boolean fullStats = false;

    public List<String> getInputFileNames() {
        return inputFileNames;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean getShouldAppend() {
        return shouldAppend;
    }

    public boolean getShowStats() {
        return showStats;
    }

    public boolean getFullStats() {
        return showStats && fullStats;
    }

    /**
     *
     * @param args program arguments.
     * Allowed options: -o path, -p prefix, -a to append info into file, -s to show short stats, -f to show full stats
     */
    public void parseArgs(String[] args) throws IllegalArgumentException, NoInputFilesException {
        for(int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if(i+1 > args.length) {
                        throw new IllegalArgumentException("Path is not specified after -o");
                    }
                    outputPath = "." + args[i + 1] + "/";
                    i++;
                    break;
                case "-p":
                    if(i+1 > args.length) {
                        throw new IllegalArgumentException("Prefix is not specified after -p");
                    }
                    prefix = args[i + 1];
                    i++;
                    break;
                case "-a":
                    shouldAppend = true;
                    break;
                case "-s":
                    showStats = true;
                    fullStats = false;
                    break;
                case "-f":
                    showStats = true;
                    fullStats = true;
                    break;
                default:
                    inputFileNames.add(args[i]);
            }
        }
        if(inputFileNames.isEmpty()) {
            throw new NoInputFilesException("No input files specified");
        }
    }
}
