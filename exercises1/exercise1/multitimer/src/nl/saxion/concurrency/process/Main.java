package nl.saxion.concurrency.process;

import javax.annotation.processing.SupportedSourceVersion;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {


    public static void main(String[] args) throws Exception {
        String java = findJava();
        Process  p1 = Runtime.getRuntime().exec( java  + " -jar timer.jar" );
        Process  p2 = Runtime.getRuntime().exec( java  + " -jar timer.jar" );
        System.out.println("Process 1 has id " + p1.pid());
        System.out.println("Process 2 has id " + p2.pid());

        Thread t1 = new Thread(() -> {
            try {
                out(p1,"Process 1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "OutputThread1");

        Thread t2 = new Thread(() -> {
            try {
                out(p2,"Process 2");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "OutputThread1");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        p1.waitFor();
        p2.waitFor();
    }


    private static void out(Process p,String name) throws Exception {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    System.out.println(name + ": " + line);
                }
            }  catch (Exception e) {
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
