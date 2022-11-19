package sample.Model;

import java.util.Arrays;

public class Sorter implements Sort {
    private static Sorter sorter = null;

    public static Sorter getInstance() {
        if (sorter == null) {
            sorter = new Sorter();
        }

        return sorter;
    }

    @Override
    public Object apply(Object arg) {
        return null;
    }

    public Object[] apply(Object objects[], Sort sort) {
        Object[] result = Arrays.copyOf(objects, objects.length);
        sort.apply(objects);
        return result;
    }
}
