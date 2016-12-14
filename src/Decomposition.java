/**
 * Created by Sofia on 14.12.16.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;
import static java.util.Arrays.fill;


public class Decomposition {
    static int n;
    static int m;

    private static class edge {
        final int b;
        final int ind;
        final int c;
        int f;
        edge rev;


        public edge(int b, int c, int ind) {
            this.b = b;
            this.c = c;
            this.ind = ind;
        }


    }

    static private int t;
    static private ArrayList<edge>[] aa;
    private static int[] d;
    private static boolean[] used2;
    private static int[] pt;
    private static int min = MAX_VALUE;
    private static ArrayList<edge> ans = new ArrayList<>();


    static edge addEdge(int a, int b, int flow, int i) {
        edge e = new edge(b, flow, i);
        aa[a].add(e);
        edge e2 = new edge(a, 0, i);
        System.out.println(b);
        aa[b].add(e2);
        e.rev = e2;
        e2.rev = e;
        return e;
    }


    static void dinic() {
        for (int lim = (1 << 30); lim >= 1; ) {
            if (!bfs(lim)) {
                lim >>= 1;
                continue;
            }
            for (int i = 0; i < n; i++)
                pt[i] = 0;
            while (dfs(0, lim)) ;
        }
    }

    static boolean bfs(int lim) {
        int head = 0;
        int tail = 1;
        int[] q = new int[t + 1];
        fill(d, -1);
        d[0] = 0;
        while (head < tail) {
            int u = q[head++];
            ArrayList<edge> edges = aa[u];
            if (edges != null) {
                for (edge e : edges) {
                    if (d[e.b] == -1 && e.c - e.f >= lim) {
                        q[tail++] = e.b;
                        d[e.b] = d[u] + 1;
                    }
                }
            }
        }
        return d[t] != -1;
    }

    static boolean dfs(int u, int flow) {
        if (flow == 0) {
            return false;
        }
        if (u == t) {
            return true;
        }

        ArrayList<edge> edges = aa[u];

        for (; pt[u] < edges.size(); pt[u]++) {
            edge e = edges.get(pt[u]);
            if (d[e.b] == d[u] + 1 && e.c - e.f >= flow) {
                if (dfs(e.b, flow)) {
                    e.f += flow;
                    e.rev.f -= flow;
                    return true;
                }
            }
        }
        return false;
    }


    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("decomposition.in"));
        PrintWriter out = new PrintWriter(new File("decomposition.out"));
        StringTokenizer st = new StringTokenizer(in.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        t = n - 1;
        aa = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            aa[i] = new ArrayList<>();
        }

        d = new int[n];
        used2 = new boolean[n];
        pt = new int[n];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            int cc = Integer.parseInt(st.nextToken());
            addEdge(a, b, cc, i + 1);
        }
        dinic();
        int depth;
        int counter = 0;
        StringBuilder sb = new StringBuilder();
        while ((depth = newDfs(0, 0)) != -1) {
            sb.append(min).append(" ")
                    .append(depth).append(" ");
            for (int i = ans.size() - 1; i >= 0; i--) {
                sb.append(ans.get(i).ind).append(" ");
            }
            sb.append("\n");
            min = MAX_VALUE;
            counter++;
            fill(used2, false);
            ans = new ArrayList<>();
        }
        out.print(counter + "\n" + sb);
        out.close();


    }

    static int newDfs(int u, int dd) {
        used2[u] = true;
        if (u == t) {
            return dd;
        }
        if (aa[u] != null) {
            for (edge e : aa[u]) {
                if (!used2[e.b] && e.f > 0) {
                    int lastMin = min;
                    min = min(e.f, min);
                    int d = newDfs(e.b, ++dd);
                    if (d != -1) {
                        ans.add(e);
                        e.f -= min;
                        return d;
                    } else {
                        min = lastMin;
                    }
                }
            }
        }
        used2[u] = false;
        return -1;
    }

}

