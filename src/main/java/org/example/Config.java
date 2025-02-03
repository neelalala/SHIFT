package org.example;

import java.util.ArrayList;

public class Config {
    private ArrayList<String> inputFiles;
    private String prefix;
    private String outputPath;
    private boolean shouldAppend;
    private boolean showStats;
    private boolean fullStats;

    public ArrayList<String> getInputFiles() {
        return inputFiles;
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

    public Config() {
        inputFiles = new ArrayList<>();
        prefix = "";
        outputPath = "";
        shouldAppend = false;
        showStats = false;
        fullStats = false;
    }

    /**
     *
     * @param args
     * Allowed options: -o path, -p prefix, -a to append info into file, -s to show short stats, -f to show full stats
     */
    void parseArgs(String[] args) {
        for(int i = 0; i < args.length; i++) {
            if(args[i].equals("-o")) {
                if(i+1 > args.length) {
                    throw new IllegalArgumentException("Path is not specified after -o");
                }
                outputPath = args[i + 1];
                i++;
            }
            else if(args[i].equals("-p")) {
                if(i+1 > args.length) {
                    throw new IllegalArgumentException("Prefix is not specified after -p");
                }
                prefix = args[i + 1] + "_";
                i++;
            }
            else if(args[i].equals("-a")) {
                shouldAppend = false;
            }
            else if(args[i].equals("-s")) {
                showStats = true;
                fullStats = false;
            }
            else if(args[i].equals("-f")) {
                shouldAppend = true;
                fullStats = true;
            }
            else {
                inputFiles.add(args[i]);
            }
        }
        if(inputFiles.isEmpty()) {
            throw new IllegalArgumentException("No input files specified");
        }
    }
}
