package sample.Model;

import java.util.InputMismatchException;

public class RSA extends HashAlgorithm {
    private int input;
    private int alpha, beta;
    private int n, z, d = 0, e, i;
    private double c;

    public RSA(int alpha, int beta) {
        if (isPrime(alpha) && isPrime(beta)) {
            this.alpha = alpha;
            this.beta = beta;

            n = alpha * beta;
            z = (alpha - 1) * (beta - 1);

            for (e = 2; e < z; e++) {
                if (gcd(e, z) == 1) {
                    break;
                }
            }
            for (i = 0; i <= 9; i++) {
                int x = 1 + (i * z);
                if (x % e == 0) {
                    d = x / e;
                    break;
                }
            }
            if (d == 0) {
                throw new InputMismatchException("Incorrect Alpha Beta");
            }
        } else {
            throw new InputMismatchException("Incorrect Alpha Beta");
        }
    }

    private boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int getAlpha() {
        return alpha;
    }

    public int getBeta() {
        return beta;
    }

    public int getInput() {
        return input;
    }

    public int getN() {
        return n;
    }

    public int getZ() {
        return z;
    }

    public int getD() {
        return d;
    }

    public int getE() {
        return e;
    }

    public int getI() {
        return i;
    }

    @Override
    public String startEncrypt(String input) {
        this.input = Integer.parseInt(input);
        c = (Math.pow(this.input, e)) % n;

        return c + "";
    }

    private int gcd(int e, int z)
    {
        if (e == 0)
            return z;
        else
            return gcd(z % e, e);
    }

    public String startDecrypt() {
        return ((Math.pow(c, d)) % n) + "";
    }
}
