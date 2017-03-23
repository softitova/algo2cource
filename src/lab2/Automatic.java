package lab2; /**
 * Created by Sofia on 14.12.16.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class Automatic {
    static class task {
        int tStart;
        int time;
        int cost;
        int index;

        public task(int tStart, int time, int cost, int index) {
            this.tStart = tStart;
            this.time = time;
            this.cost = cost;
            this.index = index;
        }
    }

    static class edge {
        int b, a, f, c, w;
        int bb;

        public edge(int a, int b, int f, int c, int w, int bb) {
            this.b = b;
            this.a = a;
            this.f = f;
            this.c = c;
            this.w = w;
            this.bb = bb;
        }

        public int ost() {
            return c - f;
        }
    }

    static int n;

    static final int N = 110000;
    static final long INF = 2000000000000000000L;
    static ArrayList<ArrayList<edge>> graph = new ArrayList<>();
    static ArrayList<task> tasks;

    private static int binSearch(int time) {
        int l = 0, r = n;
        while (r - l > 0) {
            int m = (l + r + 1) >> 1;
            //makeNewArr(m);
            if (tasks.get(m).tStart >= time) {
                r = m;
            } else {
                l = m;
            }
            if (r - l == 1) break;
        }
        if (tasks.get(l).tStart < time) return l + 1;
        return l;
    }

    static int k;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer t = new StringTokenizer(in.readLine());
        n = Integer.parseInt(t.nextToken());
        k = Integer.parseInt(t.nextToken());
        tasks = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            t = new StringTokenizer(in.readLine());
            tasks.add(new task(
                    Integer.parseInt(t.nextToken()),
                    Integer.parseInt(t.nextToken()),
                    Integer.parseInt(t.nextToken()) * -1,
                    i));
        }
        n += 1;
        tasks.add(
                new task(Integer.MAX_VALUE, 0, 0, n)
        );
        Collections.sort(tasks, new Comparator<task>() {
            @Override
            public int compare(task o1, task o2) {
                int ans = Integer.compare(o1.tStart, o2.tStart);
                return ans == 0 ? Integer.compare(o1.time, o2.time) : ans;
            }
        });
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < tasks.size() - 1; i++) {
            int index = i;
            while (tasks.get(++index).tStart < tasks.get(i).time + tasks.get(i).tStart) ;
            addEdge(tasks.get(i).index, tasks.get(index).index, 1, tasks.get(i).cost);
            addEdge(tasks.get(i).index, tasks.get(i + 1).index, k, 0);
        }
        boolean[] used = new boolean[n + 6];
        Arrays.fill(used, false);

        mF(tasks.get(0).index, n);
        //int kykyshka = 0;
        for (int i = 1; i < graph.size(); i++) {
            for (edge ee : graph.get(i)) {
                if (ee.f == 1 && ee.c == ee.f) {
                    used[i] = true;
                    break;
                }
            }

        }
        //boolean ttt = true;
        //if (ttt && kykyshka != 0 && kykyshka != n)
        //    System.out.print(0 + " ");
        //kykyshka++;

        for (int j = 1; j < n; ++j) {
            System.out.print((used[j] ? 1 : 0) + " ");
        }
        System.out.println();
        out.close();
    }

    private static void mF(int start, int n) {

        int p[] = new int[n + 1];
        int q[] = new int[N];

        int pInd[] = new int[n + 1];
        long curF = 0;
        while (curF < k) {
            int s = 0;
            int e = 0;
            long d[] = new long[n + 1];

            int used2[] = new int[n + 1];
            for (int i = 1; i < d.length; i++) {
                d[i] = INF;
            }
            d[start] = 0;
            q[e++] = start;
            used2[start] = 1;

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

            long f = INF;
            for (int cur = n; cur != start; cur = p[cur]) {
                f = Math.min(f, graph.get(p[cur]).get(pInd[cur]).c - graph.get(p[cur]).get(pInd[cur]).f);
            }
            curF += f;
            for (int cur = n; cur != start; cur = p[cur]) {
                graph.get(p[cur]).get(pInd[cur]).f += f;
                graph.get(cur).get(graph.get(p[cur]).get(pInd[cur]).bb).f -= f;
            }
        }
    }

    static void addEdge(int from, int to, int c, int w) {
        edge a = new edge(from, to, 0, c, w, graph.get(to).size());
        edge b = new edge(to, from, 0, 0, -w, graph.get(from).size());
        graph.get(from).add(a);
        graph.get(to).add(b);
    }
}