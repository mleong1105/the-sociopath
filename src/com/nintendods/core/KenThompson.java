package com.nintendods.core;

import com.nintendods.util.Util;
import java.util.ArrayList;
import java.util.Random;

public class KenThompson extends Event{

    private ArrayList<Integer>[] adjList;
    private int friend = 8;
    private int edge_num;
    private boolean[][] isVisited = new boolean[friend + 1][friend + 1];
    private int printcount = 1;
    private int maxhop = 0;
    private int exceptionPath = 0;
    Random r = new Random();

    public void execute() {
        adjList = new ArrayList[friend + 1];
        edge_num = r.nextInt(10)+1;
        for (int i = 1; i < 9; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int i = 1; i < 8; i++) {
            adjList[i].add(i + 1);
            adjList[i + 1].add(i);
            isVisited[i][i + 1] = true;
            isVisited[i + 1][i] = true;
        }
        System.out.println("Number of Edge added :" + edge_num);
        addEdge(edge_num);
        System.out.println("\nPath between you(1) to Ken Thompson(8) :");
        boolean[] isV = new boolean[friend + 1];
        ArrayList<Integer> path = new ArrayList();
        path.add(1);
        printAllPath(1, 8, isV, path);
        System.out.println("\nThe maximum hop of path is " + maxhop);
        System.out.println("\nThere is only "+exceptionPath+" path that have more than 6 hop.");
    }
    
    public KenThompson() {
        super(new Student());//obey parent class command
        count = 1;
    }

    public String addEdge(int count) {
        if (count != 0) {
            int a = r.nextInt(friend) + 1;
            int b = r.nextInt(friend) + 1;
            if (a == b) {
                return addEdge(count);
            }
            if (!addEdgeUtil(a, b)) {
                return addEdge(count);
            } else {
                return addEdge(--count);
            }
        } else {
            return "\n";
        }
    }

    public boolean addEdgeUtil(int a, int b) {
        if (isVisited[a][b]) {
            return false;
        }
        isVisited[a][b] = true;
        isVisited[b][a] = true;
        adjList[a].add(b);
        adjList[b].add(a);
        System.out.println(a + " " + b);
        return true;
    }

    public void printAllPath(int start, int end, boolean[] isV, ArrayList<Integer> path) {
        if (start == end) {
            if (path.size() < 8) {
                if(path.size()>maxhop+1){
                    maxhop=path.size()-1;
                }
                System.out.println(printcount + ". " + path);
                printcount++;
                return;
            } else {
                exceptionPath++;
                return;
            }
        }

        isV[start] = true;

        for (Integer i : adjList[start]) {
            if (!isV[i]) {
                path.add(i);
                printAllPath(i, end, isV, path);
                path.remove(i);
            }
        }
        isV[start] = false;
    }

}
