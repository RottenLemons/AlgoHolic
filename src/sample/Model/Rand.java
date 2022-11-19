package sample.Model;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Rand {

    public static int max_size = 10;
    private static int maxValue;
    private static Compare[] array = null;

    public static Compare[] getArray() {
        return array;
    }

    public static int getMaxValue() {
        return maxValue;
    }

    public static void setRandSet(int[] values) {
        resetArray();

        if (values == null) {
            randomValues();
        } else {
            setManualSet(values);
        }
        setMaxValue();
    }

    public static void setMaxValue() {
        int max = 0;
        for (Compare value : array) {
            max = value.getValue() > max ? value.getValue() : max;
        }
        Rand.maxValue = max;
    }

    public static void resetArray() {
        array = new Compare[max_size];
        IntStream.range(0, array.length).forEachOrdered(index -> {
            array[index] = new Compare(index + 1);
        });
    }

    private static Compare[] randomValues() {
        for (int index = 0; index < array.length - 1; index++) {
            int randomIndex = (int) (Math.random() * (array.length - index)) + index;
            int tempArray = array[index].getValue();
            array[index].setValue(array[randomIndex].getValue());
            array[randomIndex].setValue(tempArray);
        }
        return array;
    }

    private static void setManualSet(int[] values) {
        max_size = values.length;
        array = new Compare[max_size];
        IntStream.range(0, array.length).forEachOrdered(index -> {
            Compare bar = new Compare(values[index]);
            array[index] = bar;
        });
    }

    public static String getString() {
        return Arrays.asList(array).toString()
                .replace("[", "")
                .replace("]", "");
    }
}