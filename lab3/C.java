package lab3;

import java.io.*;

/**
 * Created by Sofia228 on 18.12.16.
 */
public class C {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("prefix.in"));
        PrintWriter out = new PrintWriter(new File("prefix.out"));
        String s = in.readLine();
        int res[] = pF(s);
        for (int i = 0; i < res.length; i++) {
            out.print(res[i] + " ");
        }
        out.close();
    }

    private static int[] pF(String s) {
        int sz = s.length();
        int pref[] = new int[sz];
        for (int i = 1; i < sz; ++i) {
            int j = pref[i - 1];
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = pref[j - 1];
            }
            if (s.charAt(i) == s.charAt(j)) {
                ++j;
            }
            pref[i] = j;
        }
        return pref;
    }
}
