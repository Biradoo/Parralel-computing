package nl.saxion.paracomp.assignment1.excercises.excercise1;

import nl.saxion.paracomp.assignment1.util.Utils;

import java.time.Duration;
import java.util.Arrays;

public class Exercise1 {
    private static final int[] sizes = {25000, 50000, 100000, 200000, 400000}; //The sizes we will be testing

    public static void main(String[] args) {
        System.out.println("Bubble sort measurement without splitting:");
        for (int size : sizes) { //Looping over the different array sizes
            Duration averageDuration = measureSortingTimeFullSort(size);
            System.out.println("Average time for sorting " + size + " elements: " + averageDuration.toMillis() + " ms");
        }
    }
    private static Duration measureSortingTimeFullSort(int size) { //Method to measure sorting time
        Duration[] durations = new Duration[10];

        for (int i = 0; i < 10; i++) {
            int[] randomArray = Utils.arrayOfRandomInts(size); //Generate a new random array for every test
            durations[i] = Utils.durationOf(() -> Utils.bubbleSort(randomArray)); //Measuring sort duration
            System.out.println(durations[i]);
        }
        Arrays.sort(durations); //Sort the array to find highest and lowest

        return Utils.calculateDurationMinusLowestAndHighest(durations);
    }
}
