package nl.saxion.paracomp.assignment1.util;

import java.util.Arrays;

public class SortThread extends Thread {
    private final int[] array;
    private final int threshold;

    public SortThread(int[] array, int threshold) {
        this.array = array;
        this.threshold = threshold;
    }

    @Override
    public void run() {
        if (array.length <= threshold) { //Bubble sort when threshold is not met.
            Utils.bubbleSort(array);
        } else { //Else split into 2 threads
            int mid = array.length / 2;
            int[] left = Arrays.copyOfRange(array, 0, mid);
            int[] right = Arrays.copyOfRange(array, mid, array.length);

            //Make 2 new threads
            SortThread t1 = new SortThread(left, threshold);
            SortThread t2 = new SortThread(right, threshold);

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Utils.merge(left, right);
            System.arraycopy(left, 0, array, 0, left.length); //Copying the left and right array into the original array.
            System.arraycopy(right, 0, array, left.length, right.length);
        }
    }
}
