package nl.saxion.paracomp.assignment1.excercises.excercise1;

import nl.saxion.paracomp.assignment1.util.SortThread;
import nl.saxion.paracomp.assignment1.util.Utils;

import java.time.Duration;
import java.util.Arrays;

public class Exercise4 {
    private static final int[] sizes = {25000, 50000, 100000, 200000, 400000}; //The sizes we will be testing
    private static final int[] THRESHOLDS = {10000, 5000, 2500, 1000, 500, 250, 100};

    public static void main(String[] args) {
        System.out.println("Measurement with dynamic multithreading with a threshold:");
        for (int size : sizes) { //Looping over the different array sizes
            for (int threshold : THRESHOLDS) {
                Duration averageDuration = measureSortingTimeWithDynamicThreads(size, threshold);
                System.out.println("Average time for sorting " + size + " elements with threshold: " + threshold + " in " + averageDuration.toMillis() + " ms");
            }

        }
    }

    private static Duration measureSortingTimeWithDynamicThreads(int size, int threshold) {
        Duration[] durations = new Duration[10]; //To save the 10 tests

        for (int i = 0; i < 10; i++) {
            int[] randomArray = Utils.arrayOfRandomInts(size); //Generate a new random array for every test

            durations[i] = Utils.durationOf(() -> { //Splitting the array
                SortThread sortThread = new SortThread(randomArray, threshold);
                sortThread.start();
                try {
                    sortThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Arrays.sort(durations);

        return Utils.calculateDurationMinusLowestAndHighest(durations);
    }

}
