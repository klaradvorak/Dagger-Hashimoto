package Algorithms;

import java.util.Arrays;
import java.io.*;
import domain.proof.hash.SHA512.SHA512;
//import org.bouncycastle.jcajce.provider.digest.SHA3;

public class DAGgeneration {
    protected static int  NUMBER_OF_BITS = 512;

    private static String encode_integer(int x) {
        String o = "";
        for (int i = 0; i <= (NUMBER_OF_BITS / 8); i++) {
            o = (char)(x % 256) + o;
            x /= 256;
        }
        return o;
    }

    private static Integer decode_integer(String s) {
        int x = 0;

        for (char c : s.toCharArray()) {
            //System.out.println(c);
            x *= 256;
            x += (int) c;
        }
        return x;
    }

    protected static Integer sha512(int x) {
        String s = null;
        if (x == (int)x) {
            s = encode_integer(x);
        }
        return decode_integer(SHA512.hash(s.getBytes()));
    }

    protected static Integer doubleSha512(int x) {
        String s = null;
        if (x == (int)x) {
            s = encode_integer(x);
        }
        return decode_integer(SHA512.hash(SHA512.hash(s.getBytes()).getBytes()));
    }

    public static int[] createDaggGraph ( int seed, int lenght) {
        double P = Constants.P;
        double init, picker;
        picker = init = ((int) Math.pow(sha512(seed), Constants.w)) % P ;
        // XOR here
        int[] o = null;
        return o;

    }

    public static void main (String[] args) {
        System.out.println(encode_integer(1097363306));
        System.out.println(decode_integer("Ahoj"));
        System.out.println("SHA3: " + sha512(1097363306));
        System.out.println("Double SHA3: "+ doubleSha512(1097363306));

        System.out.println("Graph "+ createDaggGraph(1097363306, 2));
    }
}