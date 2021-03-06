package lab2; /**
 * Created by Sofia on 14.12.16.
 */


import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Circulation {
    static HashSet<Integer> firstPart = new HashSet();

    static class edge {
        public edge(int a, int b, int f, int c, int ind) {
            this.a = a;
            this.b = b;
            this.f = f;
            this.c = c;
            this.ind = ind;
        }

        int a, b, f, c;
        int ind;
    }

    static int inf = 1000000000;
    static int MAXN = 20050;
    static int used[];

    static int n, m;
    static ArrayList<edge> e = new ArrayList<>();

    static int pt[] = new int[MAXN];
    static int pt2[] = new int[MAXN];
    static ArrayList<Integer> g[] = new ArrayList[MAXN];

    static long flow = 0;
    static int index[] = new int[MAXN];

    static int d[] = new int[MAXN];
    static ArrayList<Integer> q = new ArrayList<>();
    static int lim;

    static void add_edge(int a, int b, int c, int ind) {
        edge ed = new edge(a, b, 0, c, ind);
        index[ind] = e.size();
        g[a].add(e.size());
        e.add(ed);
        ed = new edge(b, a, 0, 0, ind);
        g[b].add(e.size());
        e.add(ed);
    }

    static boolean bfs() {
        for (int i = 1; i <= n; i++)
            d[i] = inf;
        d[1] = 0;
        q.add(1);
        while (q.size() != 0 && d[n] == inf) {
            int cur = q.get(0);
            q.remove(0);
            for (int i = 0; i < g[cur].size(); i++) {
                int id = g[cur].get(i);
                int to = e.get(id).b;
                if (d[to] == inf && e.get(id).c - e.get(id).f >= lim) {
                    d[to] = d[cur] + 1;
                    q.add(to);
                }
            }
        }
        while (q.size() != 0)
            q.remove(0);
        return d[n] != inf;
    }


    static boolean dfs(int v, int flow) {
        if (flow == 0)
            return false;
        if (v == n)
            return true;
        for (; pt[v] < g[v].size(); pt[v]++) {
            int id = g[v].get(pt[v]);
            int to = e.get(id).b;
            if (d[to] == d[v] + 1 && e.get(id).c - e.get(id).f >= flow) {
                boolean pushed = dfs(to, flow);
                if (pushed) {
                    e.get(id).f += flow;
                    e.get(id ^ 1).f -= flow;
                    return true;
                }
            }
        }
        return false;
    }

    static void dinic() {
        for (lim = (1 << 30); lim >= 1; ) {
            if (!bfs()) {
                lim >>= 1;
                continue;
            }
            for (int i = 1; i <= n; i++) {
                pt[i] = 0;
                pt2[i] = 0;
            }

            boolean pushed;

            while ((dfs(1, lim))) {
                flow = flow + lim;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Pair<Pair<Integer, Integer>, Integer>> ee = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader("circulation.in"));
        PrintWriter out = new PrintWriter(new File("circulation.out"));
        StringTokenizer st = new StringTokenizer(in.readLine());
        n = Integer.parseInt(st.nextToken()) + 2;
        m = Integer.parseInt(st.nextToken());
        int mm = m;
        used = new int[n];
        for (int i = 0; i < g.length; i++) {
            g[i] = new ArrayList<>();
        }
        int ll[] = new int[m + 2];
        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(in.readLine());
            int a = Integer.parseInt(st.nextToken()) + 1;
            int b = Integer.parseInt(st.nextToken()) + 1;
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            ll[i] = l;
            add_edge(a, b, r - l, i);
            add_edge(a, n, l, 0);
            add_edge(1, b, l, 0);

        }


        dinic();
        for (edge curEdge : e) {
            if (curEdge.a == 1 && curEdge.f < curEdge.c) {
                out.println("NO");
                out.close();
                System.exit(0);
            }
        }
        out.println("YES");
        for (int i = 1; i <= mm; i++) {
//            System.out.println((e.get(index[i]).a - 1) + " " + (e.get(index[i]).b - 1));
            out.println(e.get(index[i]).f + ll[i]);

        }
        out.close();
        //System.out.println(flow);
    }

    private static void newDfs(int v) {
        if (0 == used[v]) {
            used[v] = 1;
            firstPart.add(v);
            for (int i = 0; i < g[v].size(); i++) {
                int id = g[v].get(i);
                int to = e.get(id).b;
                if (e.get(id).c != 0 && e.get(id).f != e.get(id).c) {
                    newDfs(to);
                }
            }
        }
    }

}
