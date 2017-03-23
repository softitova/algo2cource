package lab3;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Sofia228 on 18.12.16.
 */
public class B {

    final static long p = 113;
    final static long MOD_FIRST = 1000000000 + 7;
    final static long MOD_SECOND = 1000000000 + 3;
    static long pf = 1;
    static long pS = 1;
    static ArrayList<Integer> result = new ArrayList<>();



    static long HashStFirst1 = 0;
    static long HashStFirst2 = 0;

    static long hashStSecond1 = 0;
    static long hashStSecond2 = 0;

    private static void makePow(int sz) {
        for (int i = 1; i < sz + 1; i++) {
            pf = (pf * p) % MOD_FIRST;
            pS = (pS * p) % MOD_SECOND;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("search1.in"));
        PrintWriter out = new PrintWriter(new File("search1.out"));
        String s11 = in.readLine();
        String s22 = in.readLine();
        String first = s11.length() < s22.length() ? s22 : s11;
        String pattern = s11.length() < s22.length() ? s11 : s22;
        makePow(pattern.length());
        makeHash(first, pattern);


        if (HashStFirst1 == hashStSecond1 && hashStSecond2 == HashStFirst2) {
            result.add(1);
        }
        out.println(makeAns(first, pattern, pf, pS));
        for (int x : result) {
            out.print(x + " ");
        }
        out.close();

    }

    private static void makeHash(String s1, String s2) {
        for (int i = 0; i < s2.length(); i++) {
            hashStSecond1 = (hashStSecond1 * p) % MOD_FIRST;
            hashStSecond1 = (hashStSecond1 + s2.charAt(i)) % MOD_FIRST;
            hashStSecond2 = (hashStSecond2 * p) % MOD_SECOND;
            hashStSecond2 = (hashStSecond2 + s2.charAt(i)) % MOD_SECOND;
            HashStFirst1 = (HashStFirst1 * p) % MOD_FIRST;
            HashStFirst1 = (HashStFirst1 + s1.charAt(i)) % MOD_FIRST;
            HashStFirst2 = (HashStFirst2 * p) % MOD_SECOND;
            HashStFirst2 = (HashStFirst2 + s1.charAt(i)) % MOD_SECOND;
        }

    }

    private static long makeAns(String s1, String s2, long pow_p1, long pow_p2) {
        long temp = 0;
        for (int i = 1; i < s1.length() - s2.length() + 1; i++) {
            HashStFirst1 = makeToComp(HashStFirst1, MOD_FIRST, s1, s2, i, temp, pow_p1);
            HashStFirst2 = makeToComp(HashStFirst2, MOD_SECOND, s1, s2, i, temp, pow_p2);
            if (HashStFirst1 == hashStSecond1 && HashStFirst2 == hashStSecond2) {
                result.add(i + 1);
            }
        }
        return result.size();
    }

    private static long makeToComp(long h, long MOD, String s1, String s2, int i, long temp, long pow) {
        h = (h * p) % MOD;
        h = (h + (s1.charAt(i + s2.length() - 1))) % MOD;
        temp = (pow * s1.charAt(i - 1)) % MOD;
        h = (h + MOD - temp) % MOD;
        return h;
    }
}
