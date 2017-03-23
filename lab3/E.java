package lab3;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sofia228 on 19.12.16.
 */
public class E {




    public static int[] zF(String s) {
        int n = s.length();
        int z[] = new int[n];
        int r = 0;
        int l = 0;
        for (int i = 1; i < n; i++) {
            z[i] = Math.max(0, Math.min(r - i, z[i - l]));
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i]))
                z[i]++;
            if (i + z[i] >= r) {
                l = i;
                r = i + z[i];
            }
        }
        return z;

    }


    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader("search3.in"));
        PrintWriter out = new PrintWriter(new File("search3.out"));

        String s1 = in.readLine();
        String s2 = in.readLine();
        String s3 = "";
        String s4 = "";
        for (int i = s1.length() - 1; i >= 0; i--) {
            s3 += s1.charAt(i);
        }
        for (int i = s2.length() - 1; i >= 0; i--) {
            s4 += s2.charAt(i);
        }
        int z[] = zF(s1 + "#" + s2);
        int z2[] = zF(s3 + "#" + s4);

        int sz = s1.length() + s2.length() + 1;
        int l = s1.length();
        int r = s2.length();

        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = l + 1, j = r + 1; i + l <= sz; i++, j--) {
            if (z[i] + z2[j] >= l - 1) {
                ans.add(i - l);
            }
        }
        out.println(ans.size());

        for (int x : ans) {
            out.print(x + " ");
        }
        out.close();
    }
}
