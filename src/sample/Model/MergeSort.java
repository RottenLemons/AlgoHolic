package sample.Model;

import javafx.application.Platform;
import sample.Controller.ComparisonController;
import sample.Controller.SortingController;

import java.util.ArrayList;

public class MergeSort extends SortAlgorthm {
    public static final MergeSort MERGE = new MergeSort();

    private MergeSort() {
        java = "class MergeSort \n" +
                "{\n" +
                "    // Merges two subarrays of arr[].\n" +
                "    // First subarray is arr[l..m]\n" +
                "    // Second subarray is arr[m+1..r]\n" +
                "    void merge(int arr[], int l, int m, int r)\n" +
                "    {\n" +
                "        // Find sizes of two subarrays to be merged\n" +
                "        int n1 = m - l + 1;\n" +
                "        int n2 = r - m;\n" +
                "  \n" +
                "        /* Create temp arrays */\n" +
                "        int L[] = new int[n1];\n" +
                "        int R[] = new int[n2];\n" +
                "  \n" +
                "        /*Copy data to temp arrays*/\n" +
                "        for (int i = 0; i < n1; ++i)\n" +
                "            L[i] = arr[l + i];\n" +
                "        for (int j = 0; j < n2; ++j)\n" +
                "            R[j] = arr[m + 1 + j];\n" +
                "  \n" +
                "        /* Merge the temp arrays */\n" +
                "  \n" +
                "        // Initial indexes of first and second subarrays\n" +
                "        int i = 0, j = 0;\n" +
                "  \n" +
                "        // Initial index of merged subarray array\n" +
                "        int k = l;\n" +
                "        while (i < n1 && j < n2) {\n" +
                "            if (L[i] <= R[j]) {\n" +
                "                arr[k] = L[i];\n" +
                "                i++;\n" +
                "            }\n" +
                "            else {\n" +
                "                arr[k] = R[j];\n" +
                "                j++;\n" +
                "            }\n" +
                "            k++;\n" +
                "        }\n" +
                "  \n" +
                "        /* Copy remaining elements of L[] if any */\n" +
                "        while (i < n1) {\n" +
                "            arr[k] = L[i];\n" +
                "            i++;\n" +
                "            k++;\n" +
                "        }\n" +
                "  \n" +
                "        /* Copy remaining elements of R[] if any */\n" +
                "        while (j < n2) {\n" +
                "            arr[k] = R[j];\n" +
                "            j++;\n" +
                "            k++;\n" +
                "        }\n" +
                "    }\n" +
                "  \n" +
                "    // Main function that sorts arr[l..r] using\n" +
                "    // merge()\n" +
                "    void sort(int arr[], int l, int r)\n" +
                "    {\n" +
                "        if (l < r) {\n" +
                "            // Find the middle point\n" +
                "            int m =l+ (r-l)/2;\n" +
                "  \n" +
                "            // Sort first and second halves\n" +
                "            sort(arr, l, m);\n" +
                "            sort(arr, m + 1, r);\n" +
                "  \n" +
                "            // Merge the sorted halves\n" +
                "            merge(arr, l, m, r);\n" +
                "        }\n" +
                "    }\n" +
                "  \n" +
                "    /* A utility function to print array of size n */\n" +
                "    static void printArray(int arr[])\n" +
                "    {\n" +
                "        int n = arr.length;\n" +
                "        for (int i = 0; i < n; ++i)\n" +
                "            System.out.print(arr[i] + \" \");\n" +
                "        System.out.println();\n" +
                "    }\n" +
                "  \n" +
                "    // Driver code\n" +
                "    public static void main(String args[])\n" +
                "    {\n" +
                "        int arr[] = { 12, 11, 13, 5, 6, 7 };\n" +
                "  \n" +
                "        System.out.println(\"Given Array\");\n" +
                "        printArray(arr);\n" +
                "  \n" +
                "        MergeSort ob = new MergeSort();\n" +
                "        ob.sort(arr, 0, arr.length - 1);\n" +
                "  \n" +
                "        System.out.println(\"\\nSorted array\");\n" +
                "        printArray(arr);\n" +
                "    }\n" +
                "}";
        python = "def mergeSort(arr):\n" +
                "    if len(arr) > 1:\n" +
                "  \n" +
                "         # Finding the mid of the array\n" +
                "        mid = len(arr)//2\n" +
                "  \n" +
                "        # Dividing the array elements\n" +
                "        L = arr[:mid]\n" +
                "  \n" +
                "        # into 2 halves\n" +
                "        R = arr[mid:]\n" +
                "  \n" +
                "        # Sorting the first half\n" +
                "        mergeSort(L)\n" +
                "  \n" +
                "        # Sorting the second half\n" +
                "        mergeSort(R)\n" +
                "  \n" +
                "        i = j = k = 0\n" +
                "  \n" +
                "        # Copy data to temp arrays L[] and R[]\n" +
                "        while i < len(L) and j < len(R):\n" +
                "            if L[i] < R[j]:\n" +
                "                arr[k] = L[i]\n" +
                "                i += 1\n" +
                "            else:\n" +
                "                arr[k] = R[j]\n" +
                "                j += 1\n" +
                "            k += 1\n" +
                "  \n" +
                "        # Checking if any element was left\n" +
                "        while i < len(L):\n" +
                "            arr[k] = L[i]\n" +
                "            i += 1\n" +
                "            k += 1\n" +
                "  \n" +
                "        while j < len(R):\n" +
                "            arr[k] = R[j]\n" +
                "            j += 1\n" +
                "            k += 1\n" +
                "  \n" +
                "# Code to print the list\n" +
                "  \n" +
                "  \n" +
                "def printList(arr):\n" +
                "    for i in range(len(arr)):\n" +
                "        print(arr[i], end=\" \")\n" +
                "    print()\n" +
                "  \n" +
                "  \n" +
                "# Driver Code\n" +
                "if __name__ == '__main__':\n" +
                "    arr = [12, 11, 13, 5, 6, 7]\n" +
                "    print(\"Given array is\", end=\"\\n\")\n" +
                "    printList(arr)\n" +
                "    mergeSort(arr)\n" +
                "    print(\"Sorted array is: \", end=\"\\n\")\n" +
                "    printList(arr)";
        uses = "Merge sort is very predictable. It makes between 0.5lg(n) and lg(n) comparisons per element, and between lg(n) and 1.5lg(n) swaps per element. The minima are achieved for already sorted data; the maxima are achieved, on average, for random data. If using Θ(n) extra space is of no concern, then merge sort is an excellent choice: It is simple to implement, and it is the only stable O(n·lg(n)) sorting algorithm. Note that when sorting linked lists, merge sort requires only Θ(lg(n)) extra space (for recursion). Merge sort is the algorithm of choice for a variety of situations: when stability is required, when sorting linked lists, and when random access is much more expensive than sequential access (for example, external sorting on tape). There do exist linear time in-place merge algorithms for the last step of the algorithm, but they are both expensive and complex. The complexity is justified for applications such as external sorting when O(n) extra space is not available.";
    }

    @Override
    protected void startSort(Compare[] numbers, int lowIndex, int highIndex) {
        swaps = new ArrayList<>();
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            ints.add(numbers[i].getValue());
        }
        swaps.add(ints);
        Compare[] temp = new Compare[numbers.length];
        mergeSort(numbers, temp, 0, numbers.length - 1);

    }

    private void mergeSort(Compare[] numbers, Compare[] temporary, int start, int end) {
        if (start < end) {
            int middle = (start + end) / 2;
            mergeSort(numbers, temporary, start, middle);
            mergeSort(numbers, temporary, middle + 1, end);
            merge(numbers, temporary, start, middle + 1, end);
        }
    }

    private void merge(Compare[] numbers, Compare[] temporary, int start, int end, int rightEnd) {
        int leftEnd = end - 1;
        int index = start;
        int num = rightEnd - start + 1;

        while (start <= leftEnd && end <= rightEnd) {

            if (numbers[start].compare(numbers[end]) == -1) {
                temporary[index++] = numbers[start++];
            } else {
                temporary[index++] = numbers[end++];
            }
        }

        while (start <= leftEnd) {
            temporary[index++] = numbers[start++];
        }

        while (end <= rightEnd) {
            temporary[index++] = numbers[end++];
        }

        for (int i = 0; i < num; i++, rightEnd--) {
            numbers[rightEnd] = temporary[rightEnd];
        }
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            ints.add(numbers[i].getValue());
        }

        swaps.add(ints);
    }
}
