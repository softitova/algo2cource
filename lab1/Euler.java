package lab1;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Sofia on 14.12.16.
 */
public class Euler {


    static ArrayList<ArrayList<Integer>> edEuler = new ArrayList<>();
    static int mm[][] = new int[1200][1200];
    static int pow[] = new int[1010];
    static int counter = 0;
    static ArrayList<Integer> last = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new File("euler.out"));
        BufferedReader in = new BufferedReader(new FileReader("euler.in"));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(st.nextToken());
        for (int i = 0; i < n; i++) {
            edEuler.add(new ArrayList<>());
        }

        int powChecker = 0;
        int vertex = 0;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(in.readLine());
            int mi = Integer.parseInt(st.nextToken());
            ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
            for (int ii = 0; ii < n; ii++) {
                arr.add(new ArrayList<>());
            }
            pow[i] = mi;
            for (int j = 0; j < mi; j++) {
                int to = Integer.parseInt(st.nextToken()) - 1;
                if (to > i) {
                    edEuler.get(i).add(to);
                    edEuler.get(to).add(i);
                    mm[i][to] += 1;
                    mm[to][i] += 1;

                }
            }
            if ((mi & 1) != 0) {
                ++powChecker;
                vertex = i;
            }
        }
        /*if ( ) {
            out.print(-1);
            out.close();
            return;
        }*/
        counter += 1;
        for (int i = 0; i < n; i++) {
            last.add(0);
        }
        dfs(0);
        for (int i = 0; i < n; i++) {
            if (last.get(i) != counter || powChecker > 2) {
                out.print(-1);
                out.close();
                return;
            }
        }

        ArrayList<Integer> answer = new ArrayList<>();
        ArrayList<Integer> dop = new ArrayList<>(1);
        dop.add(vertex);
        while (dop.size() != 0) {
            int to = dop.get(dop.size() - 1);
            if (pow[dop.get(dop.size() - 1)] != 0) {
                for (int i = 0; i < (int) edEuler.get(dop.get(dop.size() - 1)).size(); i++) {
                    int next = edEuler.get(dop.get(dop.size() - 1)).get(i);
                    if (mm[dop.get(dop.size() - 1)][next] != 0) {
                        --mm[dop.get(dop.size() - 1)][next];
                        --mm[next][dop.get(dop.size() - 1)];
                        --pow[next];
                        --pow[dop.get(dop.size() - 1)];
                        dop.add(next);
                        break;
                    }
                }
            }
            if (to == dop.get(dop.size() - 1)) {
                answer.add(to);
                dop.remove(dop.size() - 1);
            }

        }


        out.println(answer.size() - 1);
        for (int x : answer) {
            out.print(x + 1 + " ");
        }
        out.close();


    }

    public static void dfs(int a) {
        last.set(a, counter);
        for (int i = 0; i < (int) edEuler.get(a).size(); i++) {
            if (last.get(edEuler.get(a).get(i)) != counter) {
                dfs(edEuler.get(a).get(i));
            }
        }
    }


}
