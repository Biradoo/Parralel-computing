package nl.saxion.concurrency.process;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Timer implements Runnable {

    private String name;

    Timer(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
        int counter = 1000000;

        while (true) {
            //LocalDateTime date = LocalDateTime.now();
            //String text = date.format(formatter);
            System.out.println("It is now " + name);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    Timer t = new Timer();
    Thread thread = new Thread(t,"name");

}
