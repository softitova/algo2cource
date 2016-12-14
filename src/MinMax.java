/**
 * Created by Sofia on 14.12.16.
 */


import java.io.*;
import java.util.*;
import static java.util.Arrays.fill;

public class MinMax {
    static int[][] c;
    static int[][] c2;
    static int n;
    static int[] u;
    static int[] v;
    static int[] p;
    static int[] way;
    static long ans;
    final static int MAX_VALUE = 1_000_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("minimax.in"));
        n = Integer.parseInt(in.readLine());
        PrintWriter out = new PrintWriter(new File("minimax.out"));
        c = new int[n + 1][n + 1];
        c2 = new int[n + 1][n + 1];
        boolean used[] = new boolean[1000010];
        ArrayList<Integer> w = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            for (int j = 1; j <= n; j++) {
                c[i][j] = Integer.parseInt(st.nextToken());
                if (!used[c[i][j]]) {
                    used[c[i][j]] = true;
                    w.add(c[i][j]);
                }
            }
        }
        Collections.sort(w);
        binarySearch(w);
        out.println(ans);
        out.close();
    }

    private static void binarySearch(ArrayList<Integer> w) {

        int l = 1, r = w.size();
        while (l < r) {
            int m = (l + r) >> 1;
            if (vengr(w.get(m))) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        ans = w.get(l - 1);
    }

    private static boolean vengr(int w) {
        u = new int[n + 1];
        v = new int[n + 1];
        p = new int[n + 1];
        way = new int[n + 1];
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
                int curJ = 1;
                for (int j = 1; j <= n; ++j)
                    if (!used[j]) {
                        int cur = (c[l][j] < w ? MAX_VALUE - 1 : c[l][j]) - u[l] - v[j];
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

        for (int i = 1; i <= n; i++) {
            if (c[p[i]][i] < w) {
                return true;
            }
        }
        return false;
    }

}
