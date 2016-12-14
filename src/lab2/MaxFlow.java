package lab2; /**
 * Created by Sofia on 14.12.16.
 */

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MaxFlow {

    static int c[][];
    static int curFlow[][];
    static int queue[];
    static int d[];
    static int ptr[];
    static int t;
    static int s;
    static int n;
    final static int INF = 1000000010;
    static int parent[];

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("maxflow.in"));
        PrintWriter out = new PrintWriter(new File("maxflow.out"));
        StringTokenizer st = new StringTokenizer(in.readLine());
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        c = new int[n][n];
        curFlow = new int[n][n];
        queue = new int[n];
        d = new int[n];
        ptr = new int[n];
        parent = new int[n];
        s = 0;
        t = n - 1;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            c[u][v] += Integer.parseInt(st.nextToken());
        }
        out.println(dinic());
        out.close();

    }

    private static int dinic() {
        int result = 0;
        int ans = 0;
        for (; ; ) {
            if (!makeNet()) {
                break;
            }

            int cur = t;
            result = INF;
            while (cur != s) {
                int to = parent[cur];
                result = Math.min(result,
                        c[to][cur] - curFlow[to][cur]);
                cur = to;
            }
            ans += result;
            cur = t;
            while (cur != s) {
                int to = parent[cur];
                curFlow[to][cur] += result;
                curFlow[cur][to] -= result;
                cur = to;
            }
        }
        return ans;
    }

    static boolean makeNet() {
        int qh = 0, qt = 0;
        queue[qt++] = s;
        Arrays.fill(d, -1);
        d[s] = 0;
        while (qh < qt) {
            int v = queue[qh++];
            for (int to = 0; to < n; ++to)
                if (d[to] == -1 && curFlow[v][to] < c[v][to]) {
                    queue[qt++] = to;
                    d[to] = d[v] + 1;
                    parent[to] = v;
                }
        }
        return d[t] != -1;
    }


}