package lab3;

import java.io.*;

/**
 * Created by Sofia228 on 18.12.16.
 */
public class F {


    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader("period.in"));
        PrintWriter out = new PrintWriter(new File("period.out"));
        String s = in.readLine();


        int n = s.length();
        int ansArr[] = new int[n];
        for (int i = 1; i < n; i++) {
            int k = ansArr[i - 1];
            while (k > 0 && s.charAt(i) != s.charAt(k)) {
                k = ansArr[k - 1];
            }
            if (s.charAt(i) == s.charAt(k)) {
                ++k;
            }
            ansArr[i] = k;
        }
        if( 0 == (n % (n - ansArr[n - 1]))) {
            out.print(n - ansArr[n - 1]);
            out.close();
        }
        out.print(n);
        out.close();
    }
}
