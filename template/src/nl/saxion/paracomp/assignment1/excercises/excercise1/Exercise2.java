package nl.saxion.paracomp.assignment1.excercises.excercise1;

import nl.saxion.paracomp.assignment1.util.Utils;

import java.time.Duration;
import java.util.Arrays;

public class Exercise2 {
    private final static int[] sizes = {25000, 50000, 100000, 200000, 400000}; //The sizes we will be testing

    public static void main(String[] args) {
        System.out.println("Measurement with splitting:");
        for (int size : sizes) { //Looping over the different array sizes

            Duration averageDuration = measureSortingTimeForSplitSort(size);
            System.out.println("Average time for sorting " + size + " elements: " + averageDuration.toMillis() + " ms");
        }
    }

    private static Duration measureSortingTimeForSplitSort(int size) {
        Duration[] durations = new Duration[10];

        for (int i = 0; i < 10; i++) {
            int[] randomArray = Utils.arrayOfRandomInts(size); //Generate a new random array for every test

            durations[i] = Utils.durationOf(() -> { //Splitting the array
                int mid = randomArray.length / 2;
                int[] left = Arrays.copyOfRange(randomArray, 0, mid);
                int[] right = Arrays.copyOfRange(randomArray, mid, randomArray.length);

                //Sort both halves and merge together into durations array afterward
                Utils.bubbleSort(left);
                Utils.bubbleSort(right);

                Utils.merge(left, right);
            });
        }
        Arrays.sort(durations); //Sort the array to find highest and lowest

        return Utils.calculateDurationMinusLowestAndHighest(durations);
    }
}
