package lab2; /**
 * Created by Sofia on 14.12.16.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class MinCostMaxFlow {
    static class edge {
        int b, f, c, w;
        int bb;

        public edge(int b, int f, int c, int w, int bb) {
            this.b = b;
            this.f = f;
            this.c = c;
            this.w = w;
            this.bb = bb;
        }

        public int ost() {
            return c - f;
        }
    }

    static final int N = 110;
    static final long INF = 2000000000000000000L;
    static ArrayList<ArrayList<edge>> graph = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("mincost.in"));
        PrintWriter out = new PrintWriter(new File("mincost.out"));
        StringTokenizer t = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(t.nextToken());
        int m = Integer.parseInt(t.nextToken());
        for (int i = 0; i < n + 20; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; ++i) {
            t = new StringTokenizer(in.readLine());
            addEdge(Integer.parseInt(t.nextToken()),
                    Integer.parseInt(t.nextToken()),
                    Integer.parseInt(t.nextToken()),
                    Integer.parseInt(t.nextToken()));
        }
        long result = 0;
        while (true) {

            int s = 0;
            int e = 0;

            int q[] = new int[N];
            long d[] = new long[n + 1];

            int used2[] = new int[n + 1];
            int p[] = new int[n + 1];

            int pInd[] = new int[n + 1];
            for (int i = 1; i < d.length; i++) {
                d[i] = INF;
            }
            d[1] = 0;
            q[e++] = 1;
            used2[1] = 1;
            e = e == N ? 0 : e;

            while (s != e) {
                int x = q[s++];
                if (s == N) s = 0;
                used2[x] = 2;
                int i = 0;
                for (edge cur : graph.get(x)) {
                    if (cur.ost() != 0 && d[x] + cur.w < d[cur.b]) {
                        d[cur.b] = cur.w + d[x];
                        if (used2[cur.b] == 0) {
                            q[e++] = cur.b;
                            e = e == N ? 0 : e;
                        } else if (used2[cur.b] == 2) {
                            s = s == 0 ? N - 1 : s - 1;
                            q[s] = cur.b;
                        }
                        used2[cur.b] = 1;
                        p[cur.b] = x;
                        pInd[cur.b] = i;
                    }
                    i++;
                }
            }
            if (d[n] == INF)
                break;

            long f = INF;
            for (int cur = n; cur != 1; cur = p[cur]) {
                if (graph.get(p[cur]).get(pInd[cur]).ost() < f) {
                    f = graph.get(p[cur]).get(pInd[cur]).ost();
                }
            }


            for (int cur = n; cur != 1; cur = p[cur]) {
                graph.get(p[cur]).get(pInd[cur]).f += f;
                graph.get(cur).get(graph.get(p[cur]).get(pInd[cur]).bb).f -= f;
                result += graph.get(p[cur]).get(pInd[cur]).w * f;
            }
        }
        out.print(result);
        out.close();
    }

    static void addEdge(int from, int to, int c, int w) {
        edge a = new edge(to, 0, c, w, graph.get(to).size());
        edge b = new edge(from, 0, 0, -w, graph.get(from).size());
        graph.get(from).add(a);
        graph.get(to).add(b);
    }

}
