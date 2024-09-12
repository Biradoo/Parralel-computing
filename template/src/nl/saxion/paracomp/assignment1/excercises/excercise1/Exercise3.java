package nl.saxion.paracomp.assignment1.excercises.excercise1;

import nl.saxion.paracomp.assignment1.util.Utils;

import java.time.Duration;
import java.util.Arrays;

public class Exercise3 {
    private final static int[] sizes = {25000, 50000, 100000, 200000, 400000}; //The sizes we will be testing

    public static void main(String[] args) {
        System.out.println("Measurement with multithreading:");
        for (int size : sizes) { //Looping over the different array sizes
            Duration averageDuration = measureSortingTimeWithThreads(size);
            System.out.println("Average time for sorting " + size + " elements: " + averageDuration.toMillis() + " ms");
        }
    }

    private static Duration measureSortingTimeWithThreads(int size) {
        Duration[] durations = new Duration[10];

        for (int i = 0; i < 10; i++) {
            int[] randomArray = Utils.arrayOfRandomInts(size); //Generate a new random array for every test

            durations[i] = Utils.durationOf(() -> { //Splitting the array
                int[][] splitArray = Utils.splitArray(randomArray);
                int[] left = splitArray[0];
                int[] right = splitArray[1];

                //Creating 2 threads
                Thread t1 = new Thread(() -> Utils.bubbleSort(left));
                Thread t2 = new Thread(() -> Utils.bubbleSort(right));

                //Starting both threads
                t1.start();
                t2.start();

                try {
                    t1.join();
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Utils.merge(left, right);
            });
        }
        Arrays.sort(durations); //Sort the array to find highest and lowest

        return Utils.calculateDurationMinusLowestAndHighest(durations);
    }
}
