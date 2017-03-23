package lab3;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Sofia228 on 18.12.16.
 */
public class H {


    final static int p = 113;
    final static long MOD_F = 10000007;
    final static long MOD_S = 1000000005;

    public static void main(String[] args) throws IOException {

        //BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        //PrintWriter out = new PrintWriter(System.out);
        PrintWriter out = new PrintWriter(new File("substrcmp.out"));
         BufferedReader in = new BufferedReader(new FileReader("substrcmp.in"));

        String str = in.readLine();
        int m = Integer.parseInt(in.readLine());
        int n = str.length();

        long ArrPr[] = new long[n + 1];
        ArrPr[0] = 1;

        long ArrPr2[] = new long[n + 1];
        ArrPr2[0] = 1;

        long hashF[] = new long[n + 1];
        hashF[0] = 0;

        long hashS[] = new long[n + 1];
        hashS[0] = 0;




        for (int i = 0; i < n; i++) {
            ArrPr[i + 1] = (ArrPr[i] * p) % MOD_F;
            ArrPr2[i + 1] = (ArrPr2[i] * p) % MOD_S;
        }
        for (int i = 0; i < n; i++) {
            hashF[i + 1] = (hashF[i] * p + str.charAt(i)) % MOD_F;
            hashS[i + 1] = (hashS[i] * p + str.charAt(i)) % MOD_S;
        }

        for (int i = 0; i < m; i++) {
            int aa[] = new int[4];
            StringTokenizer st = new StringTokenizer(in.readLine());
            for (int j = 0; j < 4; j++) {
                aa[j] = Integer.parseInt(st.nextToken());
            }
            int i11 = aa[1];
            int i12 = aa[0] - 1;
            int i13 = aa[1] - aa[0] + 1;


            int i21 = aa[3];
            int i22 = aa[2] - 1;
            int i23 = aa[3] - aa[2] + 1;


            if (prepareToCompare(hashF, i21, i22, ArrPr, i23, MOD_F) != prepareToCompare(hashF, i11, i12, ArrPr, i13, MOD_F)) {
                out.print("No\n");
            } else {
                if (prepareToCompare(hashS, i11, i12, ArrPr2, i13, MOD_S) != prepareToCompare(hashS, i21, i22, ArrPr2, i23, MOD_S)) {
                    out.print("No\n");
                } else {
                    out.print("Yes\n");
                }
            }

        }
        out.close();
    }

    private static long prepareToCompare(long h[], int i1, int i2, long P[], int i3, long MOD) {
        return (h[i1] - (h[i2] * P[i3]) % MOD + MOD) % MOD;
    }
}
