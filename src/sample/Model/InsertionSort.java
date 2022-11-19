package sample.Model;

import javafx.application.Platform;
import sample.Controller.ComparisonController;
import sample.Controller.SortingController;

import java.util.ArrayList;

public class InsertionSort extends SortAlgorthm {
    public static final InsertionSort INSERTION = new InsertionSort();
    private InsertionSort() {
        java = "class InsertionSort {\n" +
                "    /*Function to sort array using insertion sort*/\n" +
                "    void sort(int arr[])\n" +
                "    {\n" +
                "        int n = arr.length;\n" +
                "        for (int i = 1; i < n; ++i) {\n" +
                "            int key = arr[i];\n" +
                "            int j = i - 1;\n" +
                " \n" +
                "            /* Move elements of arr[0..i-1], that are\n" +
                "               greater than key, to one position ahead\n" +
                "               of their current position */\n" +
                "            while (j >= 0 && arr[j] > key) {\n" +
                "                arr[j + 1] = arr[j];\n" +
                "                j = j - 1;\n" +
                "            }\n" +
                "            arr[j + 1] = key;\n" +
                "        }\n" +
                "    }\n" +
                " \n" +
                "    /* A utility function to print array of size n*/\n" +
                "    static void printArray(int arr[])\n" +
                "    {\n" +
                "        int n = arr.length;\n" +
                "        for (int i = 0; i < n; ++i)\n" +
                "            System.out.print(arr[i] + \" \");\n" +
                " \n" +
                "        System.out.println();\n" +
                "    }\n" +
                " \n" +
                "    // Driver method\n" +
                "    public static void main(String args[])\n" +
                "    {\n" +
                "        int arr[] = { 12, 11, 13, 5, 6 };\n" +
                " \n" +
                "        InsertionSort ob = new InsertionSort();\n" +
                "        ob.sort(arr);\n" +
                " \n" +
                "        printArray(arr);\n" +
                "    }\n" +
                "}";
        python = "def insertionSort(arr):\n" +
                " \n" +
                "    # Traverse through 1 to len(arr)\n" +
                "    for i in range(1, len(arr)):\n" +
                " \n" +
                "        key = arr[i]\n" +
                " \n" +
                "        # Move elements of arr[0..i-1], that are\n" +
                "        # greater than key, to one position ahead\n" +
                "        # of their current position\n" +
                "        j = i-1\n" +
                "        while j >= 0 and key < arr[j] :\n" +
                "                arr[j + 1] = arr[j]\n" +
                "                j -= 1\n" +
                "        arr[j + 1] = key\n" +
                " \n" +
                " \n" +
                "# Driver code to test above\n" +
                "arr = [12, 11, 13, 5, 6]\n" +
                "insertionSort(arr)\n" +
                "for i in range(len(arr)):\n" +
                "    print (\"% d\" % arr[i])";
        uses = "Although it is one of the elementary sorting algorithms with O(n2) worst-case time, insertion sort is the algorithm of choice either when the data is nearly sorted (because it is adaptive) or when the problem size is small. For these reasons, and because it is also stable, insertion sort is often used as the recursive base case (when the problem size is small) for higher overhead divide-and-conquer sorting algorithms, such as merge sort or quick sort.";
    }

    @Override
    protected void startSort(Compare[] numbers, int lowIndex, int highIndex) {
        swaps = new ArrayList<>();
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            ints.add(numbers[i].getValue());
        }
        swaps.add(ints);
        Compare temporary;

        for (int i = 1; i < numbers.length; i++) {
            for (int j = i; j > 0; j--) {
                if (numbers[j].compare(numbers[j - 1]) == -1) {
                    temporary = numbers[j];
                    numbers[j] = numbers[j - 1];
                    numbers[j - 1] = temporary;
                    ints = new ArrayList<>();
                    for (int k = 0; k < numbers.length; k++) {
                        ints.add(numbers[k].getValue());
                    }
                    swaps.add(ints);
                }
            }
        }

    }
}
