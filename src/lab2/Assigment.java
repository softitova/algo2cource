package lab2; /**
 * Created by Sofia on 14.12.16.
 */

import java.io.*;
import java.util.StringTokenizer;

import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.fill;


public class Assigment {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("assignment.in"));

        PrintWriter out = new PrintWriter(new File("assignment.out"));
        int n = Integer.parseInt(in.readLine());
        int[][] c = new int[n + 1][n + 1];
        int[] u = new int[n + 1];
        int[] v = new int[n + 1];
        int[] p = new int[n + 1];
        int[] way = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            for (int j = 1; j <= n; j++) {
                c[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 1; i <= n; i++) {
            p[0] = i;
            int tempJ = 0;
            int[] minv = new int[n + 1];
            fill(minv, MAX_VALUE);
            boolean[] used = new boolean[n + 1];
            do {
                used[tempJ] = true;
                int l = p[tempJ];
                int delta = MAX_VALUE;
                int curJ = 0;
                for (int j = 1; j <= n; ++j)
                    if (!used[j]) {
                        int cur = c[l][j] - u[l] - v[j];
                        if (cur < minv[j]) {
                            minv[j] = cur;
                            way[j] = tempJ;
                        }
                        if (minv[j] < delta) {
                            delta = minv[j];
                            curJ = j;
                        }
                    }
                for (int j = 0; j <= n; ++j)
                    if (used[j]) {
                        u[p[j]] += delta;
                        v[j] -= delta;
                    } else
                        minv[j] -= delta;
                tempJ = curJ;
            } while (p[tempJ] != 0);
            do {
                int tempInd = way[tempJ];
                p[tempJ] = p[tempInd];
                tempJ = tempInd;
            } while (tempJ != 0);
        }

        long result = 0;
        for (int i = 1; i <= n; i++) {
            result += c[p[i]][i];
        }
        out.println(result);
        for (int i = 1; i <= n; i++) {
            out.println(p[i] + " " + i);
        }
        out.close();


    }

}