package sample.Model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class SortAlgorthm {
    protected String java;
    protected String python;
    protected String uses;
    protected static ArrayList<ArrayList<Integer>> swaps;

    public String getJava() {
        return java;
    }

    public String getPython() {
        return python;
    }

    public static ArrayList<ArrayList<Integer>> getSwaps() {
        return swaps;
    }

    public String getUses() {
        return uses;
    }

    public void sort(Compare[] numbers, int lowIndex, int highIndex) {
        Info.startSort();
        startSort(numbers, lowIndex, highIndex);
        Info.endSort();
    }

    protected abstract void startSort(Compare[] numbers, int lowIndex, int highIndex);
}
