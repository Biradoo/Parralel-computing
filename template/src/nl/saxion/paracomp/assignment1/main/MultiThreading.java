package nl.saxion.paracomp.assignment1.main;

import nl.saxion.paracomp.assignment1.util.Utils;

import java.time.Duration;
import java.util.Arrays;

public class MultiThreading {
    public static void main(String[] args) {

        int[] sizes = {25000, 50000, 100000, 200000, 400000}; //The sizes we will be testing

//        System.out.println("Bubble sort measurement without splitting:");
//        for (int size : sizes) { //Looping over the different array sizes
//            int[] arrayOfNumbers = Utils.arrayOfRandomInts(size);
//            Duration averageDuration = measureSortingTimeFullSort(arrayOfNumbers);
//            System.out.println("Average time for sorting " + size + " elements: " + averageDuration.toMillis() + " ms");
//        }
//
//        System.out.println("Measurement with splitting:");
//        for (int size : sizes) { //Looping over the different array sizes
//            int[] arrayOfNumbers = Utils.arrayOfRandomInts(size);
//            Duration averageDuration = measureSortingTimeForSplitSort(arrayOfNumbers);
//            System.out.println("Average time for sorting " + size + " elements: " + averageDuration.toMillis() + " ms");
//        }

        System.out.println("Measurement with multithreading:");
        for (int size : sizes) { //Looping over the different array sizes
            int[] arrayOfNumbers = Utils.arrayOfRandomInts(size);
            Duration averageDuration = measureSortingTimeWithThreads(arrayOfNumbers);
            System.out.println("Average time for sorting " + size + " elements: " + averageDuration.toMillis() + " ms");
        }
    }

    private static Duration measureSortingTimeFullSort(int[] array) { //Method to measure sorting time
        Duration[] durations = new Duration[10];

        for (int i = 0; i < 10; i++) {
            int[] arrayCopy = Arrays.copyOf(array, array.length); //A copy for each measurement.
            durations[i] = Utils.durationOf(() -> Utils.bubbleSort(arrayCopy)); //Measuring sort duration
        }

        Arrays.sort(durations); //Sort the array to find highest and lowest

        Duration totalDuration = Duration.ZERO; //Calc average excluding highest and lowest
        for (int i = 1; i < durations.length - 1; i++)
            totalDuration = totalDuration.plus(durations[i]);

        return totalDuration.dividedBy(durations.length - 2); //-2 because lowest and highest removed.
    }

    private static Duration measureSortingTimeForSplitSort(int[] array) {
        Duration[] durations = new Duration[10];

        for (int i = 0; i < 10; i++) {
            int[] arrayCopy = Arrays.copyOf(array, array.length); //A copy for each measurement.

            durations[i] = Utils.durationOf(() -> { //Splitting the array
                int mid = arrayCopy.length / 2;
                int[] left = Arrays.copyOfRange(arrayCopy, 0, mid);
                int[] right = Arrays.copyOfRange(arrayCopy, mid, arrayCopy.length);

                //Sort both halves and merge together into durations array afterward
                Utils.bubbleSort(left);
                Utils.bubbleSort(right);

                Utils.merge(left, right);
            });
        }

        Arrays.sort(durations); //Sort the array to find highest and lowest

        Duration totalDuration = Duration.ZERO; //Calc average excluding highest and lowest
        for (int i = 1; i < durations.length - 1; i++) {
            totalDuration = totalDuration.plus(durations[i]);
        }

        return totalDuration.dividedBy(durations.length - 2); //-2 because lowest and highest removed.
    }

    private static Duration measureSortingTimeWithThreads(int[] array) {
        Duration[] durations = new Duration[10];

        for (int i = 0; i < 10; i++) {
            int[] arrayCopy = Arrays.copyOf(array, array.length); //A copy for each measurement.

            durations[i] = Utils.durationOf(() -> { //Splitting the array
                int mid = arrayCopy.length / 2;
                int[] left = Arrays.copyOfRange(arrayCopy, 0, mid);
                int[] right = Arrays.copyOfRange(arrayCopy, mid, arrayCopy.length);

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

        Duration totalDuration = Duration.ZERO; //Calc average excluding highest and lowest
        for (int i = 1; i < durations.length - 1; i++) {
            totalDuration = totalDuration.plus(durations[i]);
        }

        return totalDuration.dividedBy(durations.length - 2); //-2 because lowest and highest removed.
    }
}

