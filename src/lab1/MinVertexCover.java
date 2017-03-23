package lab1;

import javafx.util.Pair;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class MinVertexCover {
    static ArrayList <Pair<Integer, Integer>> ed;
    static ArrayList<ArrayList<Integer>> dol [] = new ArrayList [2];
    static ArrayList<Boolean> used[] = new ArrayList[2] ;
    static ArrayList<Integer> capable;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("vertexcover.in"));
        PrintWriter out = new PrintWriter(new File("vertexcover.out"));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        ed = new ArrayList<>();
        for ( int i=0; i <m; i++) {

            st = new StringTokenizer(in.readLine());
            int k = Integer.parseInt(st.nextToken());
            for ( int j = 0; j <k ;j ++) {
                ed.add(new Pair(i, Integer.parseInt(st.nextToken())-1));
            }
        }
        capable = new ArrayList<>(m);
        for ( int i=0; i < m; i ++) {
            capable.add(1);
        }


        dol[0] = new ArrayList<>(m);
        dol[1] = new ArrayList<>(n);
        for ( int i=0; i < m ; i ++) {
            ArrayList<Integer> a = new ArrayList();
            dol[0].add(a);
        }
        for ( int i=0; i < n ; i ++) {
            ArrayList<Integer> a = new ArrayList();
            dol[1].add(a);
        }

        used[0] = new ArrayList<>(m);
        used[1] = new ArrayList<>(n);
        for ( int i=0; i < m ; i ++) {

            used[0].add(false);
        }
        for ( int i=0; i < n ; i ++) {

            used[1].add(false);
        }
        st = new StringTokenizer(in.readLine());
        for (int i=0; i < m; i ++) {
            int v = Integer.parseInt(st.nextToken());
            if (v>0) {
                capable.set(i, 0);
                dol[1].get(v-1).add(i);
            }
        }

        for ( int i =0;i < ed.size(); i++) {
            if(!( dol[1].get(ed.get(i).getValue()).size()!=0 && dol[1].get(ed.get(i).getValue()).get(0)== ed.get(i).getKey()) ){
                dol[0].get(ed.get(i).getKey()).add(ed.get(i).getValue());
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
            if (used[1].get(i)) {
                rightPart.add(i);
            }
        }
        for ( int i =0; i < m; i++) {
            if (!used[0].get(i)) {
                leftPart.add(i);
            }
        }



        int ans = leftPart.size() + rightPart.size();

        out.println(ans);

        out.print(leftPart.size() + " ");
        for ( int x : leftPart) {
            out.print((x + 1) +  " ");
        }

        out.println();

        out.print(rightPart.size() + " ");
        for ( int x : rightPart) {
            out.print((x + 1) + " ");
        }

        out.close();
    }

    public  static void dfs (int l, int j) {
        used[l].set(j, true);
        for ( int x : dol[l].get(j) ) {
            int nextvertex = x;

            int ind = l == 1 ? 0: 1;
            if (!used[ind].get(nextvertex))
            {
                dfs(ind, nextvertex);
            }
        }
    }
}

