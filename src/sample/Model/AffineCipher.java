package sample.Model;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class AffineCipher extends HashAlgorithm {
    private int alpha;
    private int beta;
    public static ArrayList<String> letters = new ArrayList<>();

    public AffineCipher(int alpha, int beta) { // Input is a letter
        ArrayList<Integer> gcd = new ArrayList<>();
        letters.add("a");
        gcd.add(1);
        for (int i = 1; i < 26; i++) {
            if (26 % i != 0) {
                gcd.add(i);
            }
            letters.add(String.valueOf((char) (i + 97)));
        }
        if ((beta >= 0 && beta <= 25) && (gcd.contains(alpha))) {
            this.alpha = alpha;
            this.beta = beta;
        } else {
            throw new InputMismatchException("Incorrect Alpha Beta");
        }
    }

    @Override
    public String startEncrypt(String input) {
        int x = letters.indexOf(input.toLowerCase());
        int y = alpha * (x) + beta;
        return letters.get(y % 26);
    }
}
