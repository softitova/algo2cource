package lab1;

import javafx.util.Pair;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;


public class Birthday {

    static ArrayList<Boolean> used;
    static ArrayList <Integer> graph[];
    static ArrayList <Integer> matchingArr;

    static ArrayList <Pair<Integer, Integer>> ed;
    static ArrayList<ArrayList<Integer>> dol [] = new ArrayList [2];
    static ArrayList<Boolean> usedd[] = new ArrayList[2] ;
    static ArrayList<Integer> capable;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("birthday.in"));
        PrintWriter out = new PrintWriter(new File("birthday.out"));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int count = Integer.parseInt(st.nextToken());
        for (int ll = 0; ll < count; ++ll) {
            st = new StringTokenizer(in.readLine());
            int m = Integer.parseInt(st.nextToken());
            int n = Integer.parseInt(st.nextToken());
            int[][] map = new int[m][n];
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(in.readLine());
                int a = Integer.parseInt(st.nextToken())-1;
                while (a != -1) {
                    map[i][a] = 1;
                    a = Integer.parseInt(st.nextToken())-1;
                }
            }
            graph = new ArrayList[m];
            matchingArr = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                matchingArr.add(-100);
            }
            long sum = 0;
            used = new ArrayList<>();
            for (int i = 0; i < n + m; i++) {
                used.add(false);
            }
            for (int i = 0; i < m; i++) {
                graph[i] = new ArrayList<>();
            }
            for (int i = 0; i < m; i++) {
                for  (int j=0;j<n;j++) {
                    if(map[i][j] != 1) {
                        graph[i].add(j);
                    }
                }
            }
            for (int i = 0; i < m; i++) {
                Collections.fill(used, false);
                dfs(i);
            }

            capable = new ArrayList<>(m);
            dol[0] = new ArrayList<>(m);
            dol[1] = new ArrayList<>(n);
            usedd[0] = new ArrayList<>(m);
            usedd[1] = new ArrayList<>(n);

            for ( int i=0; i < m ; i ++) {
                capable.add(1);
                dol[0].add(new ArrayList<>());
                usedd[0].add(false);
            }

            for ( int i=0; i < n; i ++) {
                dol[1].add(new ArrayList<>());
                usedd[1].add(false);
            }

            for ( int i=0; i <m; i++) {
                for ( int j=0; j < graph[i].size();++j) {
                    int v = graph[i].get(j);
                    if (matchingArr.get(v) != i)
                        dol[0].get(i).add(v);
                    else {
                        dol[1].get(v).add(i);
                        capable.set(i, 0);
                    }
                }
            }

            for (int i = 0; i < m; i++) {
                if (capable.get(i) == 1) {
                    dfs(0, i);
                }
            }

            ArrayList<Integer> leftPart = new ArrayList<>();
            ArrayList<Integer> rightPart = new ArrayList<>();
            for ( int i =0; i < n; i++) {
                if (!usedd[1].get(i)) {
                    rightPart.add(i);
                }
            }
            for ( int i =0; i < m; i++) {
                if (usedd[0].get(i)) {
                    leftPart.add(i);
                }
            }



            int ans = leftPart.size() + rightPart.size();

            out.println(ans);

            out.print(leftPart.size() + " ");

            out.println(rightPart.size() + " ");
            for ( int x : leftPart) {
                out.print((x + 1) +  " ");
            }
            out.println();
            for ( int x : rightPart) {
                out.print((x + 1) + " ");
            }
            out.println();
            out.println();

        }
        out.close();


    }
    public static boolean dfs (int v) {
        if (used.get(v)){
            return false;
        }
        used.set(v, true);
        for (Integer next : graph[v]) {
            if (matchingArr.get(next) == -100
                    || dfs (matchingArr.get(next))) {
                matchingArr.set(next, v);
                return true;
            }
        }
        return false;
    }

    public  static void dfs (int l, int j) {
        usedd[l].set(j, true);
        for ( int x : dol[l].get(j) ) {
            int nextvertex = x;

            int ind = l == 1 ? 0: 1;
            if (!usedd[ind].get(nextvertex))
            {
                dfs(ind, nextvertex);
            }
        }
    }
}