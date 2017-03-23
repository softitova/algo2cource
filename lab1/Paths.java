package lab1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;


public class Paths {
    static ArrayList <Boolean> used;
    static ArrayList <Integer> graph[];
    static ArrayList <Integer> matchingArr;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("paths.in"));
        PrintWriter out = new PrintWriter(new File("paths.out"));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        graph = new ArrayList[n];
        matchingArr = new ArrayList<>();
        for (int i=0; i < n; i++) {
            matchingArr.add(-100);
        }
        long sum =0;
        used = new ArrayList<>();
        for ( int i=0;i <n; i++) {
            used.add(false);
        }
        for ( int i=0;i <n;i++) {
            graph[i] = new ArrayList<>();
        }
        for  (int i=0; i <m ; i++) {
            st = new StringTokenizer(in.readLine());
            int v = Integer.parseInt(st.nextToken());
            int u = Integer.parseInt(st.nextToken());
            graph[v-1].add(u-1);
        }
        for ( int i=0;i< n;i++)  {
            Collections.fill(used, false);
            boolean a = dfs(i);
            if(a) {
                sum++;
            }

        }
        out.print(n -sum);
        out.close();
    }

    public static boolean dfs (int v) {
        if (used.get(v)){
            return false;
        }
        used.set(v, true);
        for (int i = 0; i< graph[v].size(); i++) {
            int next = graph[v].get(i);
            if (matchingArr.get(next) == -100
                    || dfs (matchingArr.get(next))) {
                matchingArr.set(next, v);
                return true;
            }
        }
        return false;
    }
}
