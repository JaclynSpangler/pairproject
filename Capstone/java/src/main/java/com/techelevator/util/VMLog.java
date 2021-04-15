package com.techelevator.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class VMLog {

    public static void log(String message){
        try (FileOutputStream stream = new FileOutputStream("logs/audit.log", true);
             PrintWriter writer = new PrintWriter(stream)){

            LocalDateTime timestamp = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/y hh:mm");
            writer.println(timestamp.format(formatter) + message);

        } catch (Exception e) {
            throw new VMLogException(e.getMessage() + "occurred while trying to write " + message + " to the log.");
        }
    }

    public static void changeLog(String message) {

        try (PrintWriter writer = new PrintWriter("logs/change.log")) {
            LocalDateTime timestamp = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/y hh:mm");
            writer.println(timestamp.format(formatter) + message);

        } catch (Exception e) {
            throw new VMLogException(e.getMessage() + "occurred while trying to write " + message + " to the log.");
        }
    }

    public static void printChangeLogToAudit() {
        Path source = Path.of("logs/change.log");

        try (Scanner scan = new Scanner(source);
             FileOutputStream stream = new FileOutputStream("logs/audit.log", true);
             PrintWriter writer = new PrintWriter(stream)) {
                String line = scan.nextLine();
                writer.println(line);
        } catch (IOException e) {
            System.out.print("File not Found");
        }
    }
        }

