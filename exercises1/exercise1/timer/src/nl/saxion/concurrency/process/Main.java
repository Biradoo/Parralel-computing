package nl.saxion.concurrency.process;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {


    public static void main(String[] args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
        while (true) {
            LocalDateTime date = LocalDateTime.now();
            String text = date.format(formatter);
            System.out.println("It is now " + text);
            Thread.sleep(1000);
        }
    }

}
