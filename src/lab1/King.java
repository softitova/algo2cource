package lab1;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by Sofia on 14.12.16.
 */
public class King {

    static ArrayList<ArrayList<Integer>> dol[] = new ArrayList[2];
    static ArrayList<Integer> used[] = new ArrayList[2] ;
    static ArrayList<Integer> path[] = new ArrayList[2] ;
    static ArrayList<Integer> parForSeq[] = new ArrayList[2] ;
    static ArrayList <Pair<Integer, Integer>> ed;
    static ArrayList<Integer> capable;
    static ArrayList<Pair<Integer, Integer>> sequence = new ArrayList<>();
    static int count = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader in= new BufferedReader(new FileReader("king.in"));
        PrintWriter out= new PrintWriter(new File("king.out"));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int n = Integer.parseInt(st.nextToken());
        dol[0] = new ArrayList<>(n);
        dol[1] = new ArrayList<>(n);
        used[0] = new ArrayList<>(n);
        used[1] = new ArrayList<>(n);
        parForSeq[0] = new ArrayList<>(n);
        parForSeq[1] = new ArrayList<>(n);
        path[0] = new ArrayList<>(n);
        path[1] = new ArrayList<>(n);
        capable = new ArrayList<>(n);
        for ( int i=0; i < n ; i ++) {
            dol[0].add(new ArrayList());
            dol[1].add(new ArrayList());
            used[0].add(0);
            used[1].add(0);
            parForSeq[0].add(0);
            parForSeq[1].add(0);
            path[0].add(0);
            path[1].add(0);
            capable.add(1);
        }
        ed = new ArrayList<>();
        for ( int i=0; i <n; i++) {

            st = new StringTokenizer(in.readLine());
            int k = Integer.parseInt(st.nextToken());
            for ( int j = 0; j <k ;j ++) {
                ed.add(new Pair(i, Integer.parseInt(st.nextToken())-1));
            }
        }
        for (int i = 0; i < ed.size(); i++) {

            dol[0].get(ed.get(i).getKey()).add(ed.get(i).getValue());
            dol[1].get(ed.get(i).getValue()).add(ed.get(i).getKey());

        }
        st = new StringTokenizer(in.readLine());
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int v = Integer.parseInt(st.nextToken()) - 1;
            for (int j = 0; j < dol[0].get(i).size(); j++) {
                if (dol[0].get(i).get(j).equals(v)) {
                    dol[0].get(i).set(j, -1);
                    break;
                }
            }
            for (int j = 0; j < dol[1].get(v).size(); j++) {
                if (dol[1].get(v).get(j).equals(i)) {
                    dol[1].get(v).set(j, -1);
                    break;
                }
            }
            parForSeq[0].set(i, v);
            parForSeq[1].set(v, i);
        }


        ++count;
        for (int i = 0; i < n; i++) {
            if (!used[0].get(i).equals(count)) {
                dfs(i, 0, "G", -1);
            }
            if (!used[1].get(i).equals(count)) {
                dfs(i, 1,"G", -1);
            }
        }

        int count2 = 0;
        count++;
        for (int i =sequence.size() - 1; i >= 0; --i)
        {
            if (!used[sequence.get(i).getValue()].get(sequence.get(i).getKey()).equals(count))            {
                dfs(sequence.get(i).getKey(), sequence.get(i).getValue() , "B", ++count2);
            }
        }

        for (int i = 0; i < n; i++) {
            ArrayList<Integer> ans = new ArrayList<>();
            dfs2(i, ans);
            out.print( (ans.size() + 1) +" " + (parForSeq[0].get(i) + 1) + " ");
            for (int y : ans) {
                out.print((y + 1) + " ");
            }
            out.println();
        }
        out.close();

    }

    public static void dfs2 (int i, ArrayList<Integer> ans) {
        for (int x : dol[0].get(i)) {
            if (x >= 0) {
                if (path[0].get(i).equals(path[1].get(x))) {
                    if (!parForSeq[0].get(i).equals(x)) {
                        ans.add(x);
                    }
                }
            }
        }
    }





    public static void dfs(int v, int p, String sex, int numb) {
        used[p].set(v, count);

        int flag = sex.equals("G") ? 1 : 0;
        for (int x : dol[p].get(v)) {
            if (x >= 0) {
                if (p != flag && used[flag].get(x) != count) {
                    dfs(x, flag, sex, numb);
                } else if (p == flag) {
                    int aa = parForSeq[flag].get(v);
                    if (used[flag ^ 1].get(aa) != count) {
                        dfs(aa, flag ^ 1, sex, numb);
                    }
                }
            }
        }
        if (flag == 1) {
            sequence.add(new Pair<>(v, p));
        } else {
            path[p].set(v, numb);
        }
    }
}
