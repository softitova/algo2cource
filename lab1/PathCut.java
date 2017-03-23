package lab1;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Created by Sofia on 14.12.16.
 */
public class PathCut {


    static ArrayList<HashSet<Pair<Integer, Integer>>> edEuler = new ArrayList<>();


    static int counter = 0;
    static ArrayList<Integer> last = new ArrayList<>();
    static int pow[];

    public static void main(String[] args) throws IOException {
        PrintWriter out = new PrintWriter(new File("paths.out"));
        BufferedReader in = new BufferedReader(new FileReader("paths.in"));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        for (int i = 0; i < n; i++) {
            edEuler.add(new HashSet<>());
        }

        pow = new int[n];

        int vertex = 0;
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());


            int aa = Integer.parseInt(st.nextToken()) - 1;
            int bb = Integer.parseInt(st.nextToken()) - 1;

            edEuler.get(aa).add(new Pair(bb, 0));
            edEuler.get(bb).add(new Pair(aa, 0));




           /* if ((mi & 1) !=0 ) {
                ++powChecker;
                vertex = i;
            }*/
        }
        for (int i = 0; i < n; i++) {
            pow[i] = edEuler.get(i).size();
            if ((pow[i] & 1) != 0) {

            }
            /*if ( ) {
            out.print(-1);
            out.close();
            return;
        }*/
        }
        HashSet<Integer> set = new HashSet<>();
        HashSet<Pair<Integer, Integer>> setA = new HashSet<>();
        ArrayList<Integer> vertexToPair = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (pow[i] % 2 != 0) {
                vertexToPair.add(i);

                set.add(i);
            }
        }
        if (vertexToPair.size() > 0) {
            vertex = vertexToPair.get(vertexToPair.size() / 2);
        }


        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < vertexToPair.size() / 2 - 1; i++) {
            int a = vertexToPair.get(i);
            int b = vertexToPair.get(vertexToPair.size() - 1 - i);
            edEuler.get(a).add(new Pair(b, 1));
            edEuler.get(b).add(new Pair(a, 1));
            setA.add(new Pair(a, b));
            setA.add(new Pair(b, a));
            map.put(a, edEuler.get(a).size() - 1);
        }

        counter += 1;
        for (int i = 0; i < n; i++) {
            last.add(0);
        }


        ArrayList<Integer> answer = new ArrayList<>();
        ArrayList<Integer> dop = new ArrayList<>(1);
        dop.add(vertex);
        while (dop.size() != 0) {
            int to = dop.get(dop.size() - 1);
            if (pow[dop.get(dop.size() - 1)] != 0) {
                for (Pair x : edEuler.get(dop.get(dop.size() - 1))) {
                    int next = (int) x.getKey();
                    edEuler.get(dop.get(dop.size() - 1)).remove(x);
                    edEuler.get((int) x.getKey()).remove(new Pair(dop.get(dop.size() - 1), x.getValue()));
                    dop.add(next);
                    break;
                }
            }
            if (to == dop.get(dop.size() - 1)) {
                answer.add(to);
                dop.remove(dop.size() - 1);
            }

        }
        int ans = vertexToPair.size() / 2;
        if (ans == 0) {
            ans = 1;
        }
        out.println(ans);
        for (int i = 0; i < answer.size() - 1; ) {
            int from = answer.get(i);
            int to = answer.get(++i);
            out.print((from + 1) + " ");
            if (setA.contains(new Pair(from, to))) {
                setA.remove(new Pair(from, to));
                setA.remove(new Pair(to, from));
                out.println();
            }
        }
        out.print((answer.get(answer.size() - 1) + 1));
        out.close();


    }


}
