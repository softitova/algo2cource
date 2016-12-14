/**
 * Created by Sofia on 14.12.16.
 */


import java.io.*;
import java.util.*;
import static java.lang.Integer.*;

public class MultiAssigment {
    static ArrayList<edge> arr;

    static class edge {
        int a, b, f, c, w;
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

    static final int N = 110;
    static final long INF = 2000000000000000000L;
    static ArrayList<ArrayList<edge>> graph = new ArrayList<>();

    static int n;
    static ArrayList<Integer> fREs[];

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("multiassignment.in"));
        PrintWriter out = new PrintWriter(new File("multiassignment.out"));
        // PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(in.readLine());
        n = parseInt(st.nextToken());

        int K = parseInt(st.nextToken());
        fREs = new ArrayList[K];

        int oldN = n;
        long ans = 0;
        n = n * 2 + 2;
        for (int i = 0; i < n + 20; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 1; i <= oldN; i++) {
            st = new StringTokenizer(in.readLine());
            for (int j = 1; j <= oldN; j++) {
                addEdge(i + 1, j + 1 + oldN, 1, parseInt(st.nextToken()));
            }
        }

        for (int i = 2; i < oldN + 2; i++) {
            addEdge(1, i, K, 0);
            addEdge(i + oldN, n, K, 0);
        }

//        for (int i = 1; i <= n; i++) {
//            for (edge edge : graph.get(i)) {
//                System.out.print(edge.a + " " + edge.b + " " + edge.c + " " + edge.w + "\t\t");
//            }
//            System.out.println();
//        }

        arr = new ArrayList<>();
        ans = mF();
        out.println(ans);
        graph.forEach(h -> h.stream().filter(e -> e.f == 0).forEach(e -> e.c = 0));
        graph.forEach(h -> h.forEach(e -> e.f = 0));
        reset();
//        for (int i = 1; i <= n; i++) {
//            graph.get(i).forEach(e -> System.out.print(e.b + " " + e.f + " " + e.c + "\t\t"));
//            System.out.println();
//        }
        for (int i = 0; i < K; i++) {
            arr = new ArrayList<>();
            mF();
            for (int ii = 2; ii <= oldN + 1; ++ii) {
                for (edge edge : graph.get(ii)) {
                    if (edge.f == 1) {
                        arr.add(edge);
                        edge.c = 0;
                        edge.f = 0;
                        edge obr = graph.get(edge.b).get(edge.bb);
                        obr.f = obr.c = 0;
                    }
                }
            }

            Collections.sort(arr, new Comparator<edge>() {
                @Override
                public int compare(edge o1, edge o2) {
                    return Integer.compare(o1.a, o2.a);
                }
            });

            for (edge ee : arr) {
                out.print(ee.b - oldN - 1 + " ");
            }
//out.print("here");
            reset();
            out.println();
        }

//        for (int i = 1; i <= n; i++) {
//            for (edge edge : graph.get(i)) {
//                System.out.print(edge.a + " " + edge.b + " " + edge.c + " " + edge.w + "\t\t");
//            }
//            System.out.println();
//        }
        out.close();

    }
    // out.println(result);


    //out.println();


    static HashSet<edge> rev = new HashSet<>();
    static HashSet<edge> rev2 = new HashSet<>();
    static HashSet<edge> rev3 = new HashSet<>();


    static void addEdge(int from, int to, int c, int w) {
        edge a = new edge(from, to, 0, c, w, graph.get(to).size());
        edge b = new edge(to, from, 0, 0, -w, graph.get(from).size());
        if (from == 0) {
            rev.add(b);
        }
        if (to == n) {
            rev2.add(b);
            rev3.add(a);
        }
        graph.get(from).add(a);
        graph.get(to).add(b);
    }

    private static long mF() {
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

            //out.println(f);
            for (int cur = n; cur != 1; cur = p[cur]) {
                edge edge = graph.get(p[cur]).get(pInd[cur]);
                edge.f += f;
                //out.println("u="+edge.a+";v="+edge.b);
                graph.get(cur).get(edge.bb).f -= f;
                //out.println(edge.a + " " + edge.b + " here");
                //System.\out.println(edge.w + " "+ f);
                result += edge.w * f;
                //out.println("res=="+result);
            }

        }


        return result;
    }

    private static void reset() {
        graph.get(1).forEach(ee -> {
            ee.f = 0;
            ee.c = 1;
        });
        rev.forEach(ee -> {
            ee.f = 0;
            ee.c = 1;
        });
        rev2.forEach(ee -> {
            ee.f = 0;
            ee.c = 1;
        });
        rev3.forEach(ee -> {
            ee.f = 0;
            ee.c = 1;

        });

    }
}
