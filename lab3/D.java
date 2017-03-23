package lab3;

import java.io.*;

/**
 * Created by Sofia on 18.12.16.
 */
public class D {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("z.in"));
        PrintWriter out = new PrintWriter(new File("z.out"));
        String s1 = in.readLine();
        int zz[] = zFunction(s1);
        for (int i = 1; i < zz.length; i++) {
            out.print(zz[i] + " ");
        }
        out.close();
    }

    private static int[] zFunction(String s) {
        int sz = s.length();
        int ans[] = new int[sz];
        int l = 0;
        int r = 0;
        for (int i = 0; i < sz; i++) {
            ans[i] = Math.max(0, Math.min(r - i, ans[i - l]));
            while (i + ans[i] < sz && s.charAt(ans[i]) == s.charAt(i + ans[i])) ans[i]++;
            if (i + ans[i] >= r) {
                r = i + ans[i]; l = i;
            }
        }
        return ans;

    }
}
