package ru.shift;

import ru.shift.filterutility.service.FilterUtility;

public class App {
    public static void main(String[] args) {
        FilterUtility app = new FilterUtility();
        app.filter(args);
    }
}
