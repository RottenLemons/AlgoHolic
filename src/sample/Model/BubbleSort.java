package sample.Model;

import java.util.ArrayList;

public class BubbleSort extends SortAlgorthm {
    public static final BubbleSort BUBBLE = new BubbleSort();

    private BubbleSort() {
        java = "class BubbleSort\n" +
                "{\n" +
                "    void bubbleSort(int arr[])\n" +
                "    {\n" +
                "        int n = arr.length;\n" +
                "        for (int i = 0; i < n-1; i++)\n" +
                "            for (int j = 0; j < n-i-1; j++)\n" +
                "                if (arr[j] > arr[j+1])\n" +
                "                {\n" +
                "                    // swap arr[j+1] and arr[j]\n" +
                "                    int temp = arr[j];\n" +
                "                    arr[j] = arr[j+1];\n" +
                "                    arr[j+1] = temp;\n" +
                "                }\n" +
                "    }\n" +
                "  \n" +
                "    /* Prints the array */\n" +
                "    void printArray(int arr[])\n" +
                "    {\n" +
                "        int n = arr.length;\n" +
                "        for (int i=0; i<n; ++i)\n" +
                "            System.out.print(arr[i] + \" \");\n" +
                "        System.out.println();\n" +
                "    }\n" +
                "  \n" +
                "    // Driver method to test above\n" +
                "    public static void main(String args[])\n" +
                "    {\n" +
                "        BubbleSort ob = new BubbleSort();\n" +
                "        int arr[] = {64, 34, 25, 12, 22, 11, 90};\n" +
                "        ob.bubbleSort(arr);\n" +
                "        System.out.println(\"Sorted array\");\n" +
                "        ob.printArray(arr);\n" +
                "    }\n" +
                "}";
        python = "def bubbleSort(arr):\n" +
                "    n = len(arr)\n" +
                "  \n" +
                "    # Traverse through all array elements\n" +
                "    for i in range(n):\n" +
                "  \n" +
                "        # Last i elements are already in place\n" +
                "        for j in range(0, n-i-1):\n" +
                "  \n" +
                "            # traverse the array from 0 to n-i-1\n" +
                "            # Swap if the element found is greater\n" +
                "            # than the next element\n" +
                "            if arr[j] > arr[j+1] :\n" +
                "                arr[j], arr[j+1] = arr[j+1], arr[j]\n" +
                "  \n" +
                "# Driver code to test above\n" +
                "arr = [64, 34, 25, 12, 22, 11, 90]\n" +
                "  \n" +
                "bubbleSort(arr)\n" +
                "  \n" +
                "print (\"Sorted array is:\")\n" +
                "for i in range(len(arr)):\n" +
                "    print (\"%d\" %arr[i]), ";
        uses = "Bubble sort has many of the same properties as insertion sort, but has slightly higher overhead. In the case of nearly sorted data, bubble sort takes O(n) time, but requires at least 2 passes through the data (whereas insertion sort requires something more like 1 pass).";
    }

    protected void startSort(Compare[] numbers, int lowIndex, int highIndex) {
        swaps = new ArrayList<>();
        ArrayList<Integer> ints = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            ints.add(numbers[i].getValue());
        }
        swaps.add(ints);
        int prevSwap = numbers.length - 1;


        boolean isSorted = true;
        int currentSwap = -1;
        for (int i = 1; i < Rand.getArray().length; i++) {
            for (int j = 0; j < prevSwap; j++) {
                if (numbers[j].compare(numbers[j + 1]) == 1) {

                    Compare temporary = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temporary;
                    ints = new ArrayList<>();
                    for (int k = 0; k < numbers.length; k++) {
                        ints.add(numbers[k].getValue());
                    }
                    swaps.add(ints);
                    isSorted = false;
                    currentSwap = j;
                }
            }
        }

            if (isSorted)
                return;

            prevSwap = currentSwap;


    }
}
