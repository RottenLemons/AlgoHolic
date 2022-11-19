package sample.Model;

import javafx.application.Platform;
import sample.Controller.ComparisonController;
import sample.Controller.SortingController;

import java.util.ArrayList;

public class QuickSort extends SortAlgorthm {
    public static final QuickSort QUICK = new QuickSort();

    private QuickSort() {
        java = "import java.io.*;\n" +
                "  \n" +
                "class QuickSort{\n" +
                "      \n" +
                "// A utility function to swap two elements\n" +
                "static void swap(int[] arr, int i, int j)\n" +
                "{\n" +
                "    int temp = arr[i];\n" +
                "    arr[i] = arr[j];\n" +
                "    arr[j] = temp;\n" +
                "}\n" +
                "  \n" +
                "/* This function takes last element as pivot, places\n" +
                "   the pivot element at its correct position in sorted\n" +
                "   array, and places all smaller (smaller than pivot)\n" +
                "   to left of pivot and all greater elements to right\n" +
                "   of pivot */\n" +
                "static int partition(int[] arr, int low, int high)\n" +
                "{\n" +
                "      \n" +
                "    // pivot\n" +
                "    int pivot = arr[high]; \n" +
                "      \n" +
                "    // Index of smaller element and\n" +
                "    // indicates the right position\n" +
                "    // of pivot found so far\n" +
                "    int i = (low - 1); \n" +
                "  \n" +
                "    for(int j = low; j <= high - 1; j++)\n" +
                "    {\n" +
                "          \n" +
                "        // If current element is smaller \n" +
                "        // than the pivot\n" +
                "        if (arr[j] < pivot) \n" +
                "        {\n" +
                "              \n" +
                "            // Increment index of \n" +
                "            // smaller element\n" +
                "            i++; \n" +
                "            swap(arr, i, j);\n" +
                "        }\n" +
                "    }\n" +
                "    swap(arr, i + 1, high);\n" +
                "    return (i + 1);\n" +
                "}\n" +
                "  \n" +
                "/* The main function that implements QuickSort\n" +
                "          arr[] --> Array to be sorted,\n" +
                "          low --> Starting index,\n" +
                "          high --> Ending index\n" +
                " */\n" +
                "static void quickSort(int[] arr, int low, int high)\n" +
                "{\n" +
                "    if (low < high) \n" +
                "    {\n" +
                "          \n" +
                "        // pi is partitioning index, arr[p]\n" +
                "        // is now at right place \n" +
                "        int pi = partition(arr, low, high);\n" +
                "  \n" +
                "        // Separately sort elements before\n" +
                "        // partition and after partition\n" +
                "        quickSort(arr, low, pi - 1);\n" +
                "        quickSort(arr, pi + 1, high);\n" +
                "    }\n" +
                "}\n" +
                "  \n" +
                "// Function to print an array \n" +
                "static void printArray(int[] arr, int size)\n" +
                "{\n" +
                "    for(int i = 0; i < size; i++)\n" +
                "        System.out.print(arr[i] + \" \");\n" +
                "          \n" +
                "    System.out.println();\n" +
                "}\n" +
                "  \n" +
                "// Driver Code\n" +
                "public static void main(String[] args)\n" +
                "{\n" +
                "    int[] arr = { 10, 7, 8, 9, 1, 5 };\n" +
                "    int n = arr.length;\n" +
                "      \n" +
                "    quickSort(arr, 0, n - 1);\n" +
                "    System.out.println(\"Sorted array: \");\n" +
                "    printArray(arr, n);\n" +
                "}\n" +
                "}";
        python = "# This Function handles sorting part of quick sort\n" +
                "# start and end points to first and last element of\n" +
                "# an array respectively\n" +
                "def partition(start, end, array):\n" +
                "      \n" +
                "    # Initializing pivot's index to start\n" +
                "    pivot_index = start \n" +
                "    pivot = array[pivot_index]\n" +
                "      \n" +
                "    # This loop runs till start pointer crosses \n" +
                "    # end pointer, and when it does we swap the\n" +
                "    # pivot with element on end pointer\n" +
                "    while start < end:\n" +
                "          \n" +
                "        # Increment the start pointer till it finds an \n" +
                "        # element greater than  pivot \n" +
                "        while start < len(array) and array[start] <= pivot:\n" +
                "            start += 1\n" +
                "              \n" +
                "        # Decrement the end pointer till it finds an \n" +
                "        # element less than pivot\n" +
                "        while array[end] > pivot:\n" +
                "            end -= 1\n" +
                "          \n" +
                "        # If start and end have not crossed each other, \n" +
                "        # swap the numbers on start and end\n" +
                "        if(start < end):\n" +
                "            array[start], array[end] = array[end], array[start]\n" +
                "      \n" +
                "    # Swap pivot element with element on end pointer.\n" +
                "    # This puts pivot on its correct sorted place.\n" +
                "    array[end], array[pivot_index] = array[pivot_index], array[end]\n" +
                "     \n" +
                "    # Returning end pointer to divide the array into 2\n" +
                "    return end\n" +
                "      \n" +
                "# The main function that implements QuickSort \n" +
                "def quick_sort(start, end, array):\n" +
                "      \n" +
                "    if (start < end):\n" +
                "          \n" +
                "        # p is partitioning index, array[p] \n" +
                "        # is at right place\n" +
                "        p = partition(start, end, array)\n" +
                "          \n" +
                "        # Sort elements before partition \n" +
                "        # and after partition\n" +
                "        quick_sort(start, p - 1, array)\n" +
                "        quick_sort(p + 1, end, array)\n" +
                "          \n" +
                "# Driver code\n" +
                "array = [ 10, 7, 8, 9, 1, 5 ]\n" +
                "quick_sort(0, len(array) - 1, array)\n" +
                "  \n" +
                "print(f'Sorted array: {array}')";
        uses = "When carefully implemented, quick sort is robust and has low overhead. When a stable sort is not needed, quick sort is an excellent general-purpose sort – although the 3-way partitioning version should always be used instead. The 2-way partitioning code shown above is written for clarity rather than optimal performance; it exhibits poor locality, and, critically, exhibits O(n2) time when there are few unique keys. A more efficient and robust 2-way partitioning method is given in Quicksort is Optimal by Robert Sedgewick and Jon Bentley. The robust partitioning produces balanced recursion when there are many values equal to the pivot, yielding probabilistic guarantees of O(n·lg(n)) time and O(lg(n)) space for all inputs. With both sub-sorts performed recursively, quick sort requires O(n) extra space for the recursion stack in the worst case when recursion is not balanced. This is exceedingly unlikely to occur, but it can be avoided by sorting the smaller sub-array recursively first; the second sub-array sort is a tail recursive call, which may be done with iteration instead. With this optimization, the algorithm uses O(lg(n)) extra space in the worst case.";
    }

    @Override
    protected void startSort(Compare[] numbers, int lowIndex, int highIndex) {
        swaps = new ArrayList<>();
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            ints.add(numbers[i].getValue());
        }
        swaps.add(ints);
        qsort(numbers, 0, numbers.length - 1);
    }

    private void qsort(Compare[] numbers, int lowIndex, int highIndex) {
        if (highIndex <= lowIndex) {
            return;
        }
        int index = partition(numbers, lowIndex, highIndex);
        qsort(numbers, lowIndex, index - 1);
        qsort(numbers, index + 1, highIndex);
    }

    private int partition(Compare[] numbers, int lowIndex, int highIndex) {
        Compare temporary;
        int low = lowIndex - 1;
        int high = highIndex;
        Compare pivot = numbers[highIndex];
        while (true) {

            while (numbers[++low].compare(pivot) == -1) {
            }

            while (pivot.compare(numbers[--high]) == -1) {
                if (high == lowIndex) {
                    break;
                }
            }
            if (low >= high) {
                break;
            }
            temporary = numbers[low];
            numbers[low] = numbers[high];
            numbers[high] = temporary;
            ArrayList<Integer> ints = new ArrayList<>();
            for (int i = 0; i < numbers.length; i++) {
                ints.add(numbers[i].getValue());
            }
            swaps.add(ints);
        }
        ArrayList<Integer> ints = new ArrayList<>();
        numbers[highIndex] = numbers[low];
        numbers[low] = pivot;
        for (int i = 0; i < numbers.length; i++) {
            ints.add(numbers[i].getValue());
        }

        swaps.add(ints);
        return low;
    }
}
