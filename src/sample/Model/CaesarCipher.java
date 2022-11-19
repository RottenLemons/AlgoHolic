package sample.Model;

import java.util.ArrayList;

public class CaesarCipher extends HashAlgorithm {

    private int alpha;
    public static ArrayList<String> letters = new ArrayList<>();

    public CaesarCipher(int alpha) { // Input is a letter
        for (int i = 0; i < 26; i++) {
            letters.add(String.valueOf((char) (i + 97)));
        }

        this.alpha = alpha;
    }

    @Override
    public String startEncrypt(String input) {
        int x = letters.indexOf(input);
        int y = alpha + x;
        return letters.get(y % 26);
    }
}
