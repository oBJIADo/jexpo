package ru.tsystems.divider;

import java.io.FileNotFoundException;
import java.io.IOException;

import ru.tsystems.divider.utils.impl.FileCleaner;
import ru.tsystems.divider.exceptions.NoShetException;

public class FileClear {

    public static void main(String[] args) {
        try {
            FileCleaner fileCleaner = new FileCleaner();
            fileCleaner.clearFile(args[0], args[1], args[2]);
        } catch (FileNotFoundException fnfExc) {
            System.out.println("No file: " + fnfExc.getMessage());
        } catch (IOException ioExc) {
            System.out.println("File problem" + ioExc.getMessage());
        } catch (NoShetException nsExc) {
            System.out.println("Sheet exception: " + nsExc.getMessage());
        }
    }
}