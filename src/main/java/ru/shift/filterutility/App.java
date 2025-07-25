package ru.shift.filterutility;

import ru.shift.filterutility.exception.NoInputFilesException;
import ru.shift.filterutility.model.ExitCode;
import ru.shift.filterutility.service.FilterUtility;

public class App {
    public static void main(String[] args) {
        FilterUtility app = new FilterUtility();
        try {
            app.configure(args);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            FilterUtility.printUsage();
            System.exit(ExitCode.BAD_ARGS);
        } catch (NoInputFilesException e) {
            System.err.println(e.getMessage());
            FilterUtility.printUsage();
            System.exit(ExitCode.NO_INPUT_FILES);
        }

        try {
            app.filter();
        } catch (NoInputFilesException e) {
            System.err.println(e.getMessage());
            FilterUtility.printUsage();
            System.exit(ExitCode.NO_INPUT_FILES);
        }
    }
}
