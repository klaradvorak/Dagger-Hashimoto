package Algorithms;

import java.math.BigInteger;
import java.util.LinkedList;
import domain.proof.hash.SHA512.SHA512;

public class DAGgeneration { 
    protected static int  NUMBER_OF_BITS = 512;
    private static BigInteger moduloValue= new BigInteger("256");

    private static String encode_integer(BigInteger x) {
    	String o = "" ;
        for (int i = 0; i < (NUMBER_OF_BITS / 8); i++) {
            int modResult = (x.mod(moduloValue)).intValue();
            if (modResult != 0) {
            	o = (char) modResult + o;
            	//System.out.println(modResult + " " + x);
            	x = x.divide(moduloValue);
            }
        }
        return o;
    }

    private static BigInteger decode_integer(String s) {
        BigInteger x = new BigInteger("0");
        for (char c : s.toCharArray()) {
            //System.out.println(x + " " + BigInteger.valueOf(c));
            x = x.multiply(moduloValue);
            x = x.add(BigInteger.valueOf(c));
        }
        return x;
    }

    protected static BigInteger sha512(BigInteger x) {
        String s = null;
        if (x == (BigInteger)x) {
            s = encode_integer(x);
        }
        return decode_integer(SHA512.hash(s.getBytes()));
    }

    protected static BigInteger doubleSha512(BigInteger x) {
        String s = null;
        if (x == (BigInteger)x) {
            s = encode_integer(x);
        }
        return decode_integer(SHA512.hash(SHA512.hash(s.getBytes()).getBytes()));
    }

    public static LinkedList<BigInteger> createDaggGraph ( BigInteger seed, int lenght) {
        BigInteger P = Constants.P;
        BigInteger init, picker;
        //picker = init = (int) ((Math.pow(sha512(seed), Constants.w)) % P) ;
        picker = init = seed.pow(Constants.w).mod(P);
        LinkedList<BigInteger> o = new LinkedList<BigInteger>() {{add(init);}};
        
        // XOR here
    	for(int i = 1; i < lenght; i++) {
    		BigInteger x = picker = picker.multiply(init).mod(P);
    		for(int j=0; j < Constants.k; j++) {
    			//x ^=  o[(x % i)]
    			x = x.modPow(o.getLast(), P);
    		}
    		o.addLast(x.pow(Constants.w).mod(P));
    		//System.out.println("x is : " + x);
    	}
        return o;
    }

    public static void main (String[] args) {
    	
    	System.out.println("decoder " + decode_integer("Hello there"));

        BigInteger big = new BigInteger("87521618088882658227876453");
        System.out.println("encoder " + encode_integer(big));
        
        System.out.println("SHA3: " + sha512(big));
        System.out.println("Double SHA3: "+ doubleSha512(big));

        System.out.println("Graph "+ createDaggGraph(big, 10));

        System.out.println();
        System.out.println(Constants.P);
        System.out.println((Constants.P).longValue());
        System.out.println((Constants.P).doubleValue());
       

    }
}