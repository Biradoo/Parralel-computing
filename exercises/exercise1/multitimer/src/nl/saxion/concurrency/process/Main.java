package nl.saxion.concurrency.process;

import javax.annotation.processing.SupportedSourceVersion;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {


    public static void main(String[] args) throws Exception {
        String java = findJava();
        Process p1 = Runtime.getRuntime().exec(java + " -jar timer.jar");
        Process p2 = Runtime.getRuntime().exec(java + " -jar timer.jar");
        System.out.println("Process 1 has id " + p1.pid());
        System.out.println("Process 2 has id " + p2.pid());
        //blocks because it will read in the data from
        //Process p1 for ever.
        out(p1, "Process 1");
        //the code will only reach this point after process 1 is stopped.
        //which you can do via the task manager
        out(p2, "Process 2");
        p1.waitFor();
        p2.waitFor();
    }


    private static void out(Process p, String name) throws Exception {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(p.getInputStream()));

        Thread t1 = new Thread(() -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
            while (true) {
                LocalDateTime date = LocalDateTime.now();
                String text = date.format(formatter);
                System.out.println("It is now " + text);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2 = new Thread(() -> {

        });



        String line;
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println(name + ": " + line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String findJava() {
        String java_home = System.getProperty("java.home");
        File f = new File(java_home);
        f = new File(f, "bin");
        File fjava = new File(f, "java.exe");
        String java = "";
        if (fjava.exists()) {
            java = fjava.getAbsolutePath();
        } else {
            fjava = new File(f, "java");
            if (fjava.exists()) {
                java = fjava.getAbsolutePath();
            } else {
                System.out.println("Java not found");
                System.exit(1);
            }
        }
        return java;
    }
}
