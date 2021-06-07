import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Friendship extends Event{
   
    private int num_friend;
    private ArrayList<Integer>[] adjList;
    private int count;
    
    public Friendship(){
        super(new Student());//obey parent class command
        count = 1;
    }

    @Override
    public void execute() {
        Scanner sc=new Scanner(System.in);
        System.out.println("Input :");
        int num=sc.nextInt();
        num_friend = num + 1;
        adjList = new ArrayList[num_friend];
        for (int i = 0; i < num_friend; i++) {
            adjList[i] = new ArrayList<>();
        }

        for(int i=0;i!=num;i++){
            addEdge(sc.nextInt(),sc.nextInt());
        }
        System.out.println("\nYou can form the following friendship :\n");
        printAllPaths(1);

        sc.close();
    }
    
    public void addEdge(int u, int v){
        adjList[u].add(v);
        adjList[v].add(u);
    }
    
    public void printAllPaths(int start){
        if(start!=num_friend){
            boolean[] isVisited = new boolean[num_friend];
            ArrayList<Integer> pathList = new ArrayList<>();
            pathList.add(start);
            for(int end=num_friend; end>start; end--){
                printAllPathsUtil(start, end, isVisited, pathList);
            }
            printAllPaths(++start);
        }
        else return;
    }
    
    private void printAllPathsUtil(Integer start, Integer end, boolean[] isVisited, List<Integer> pathList){
        if (start.equals(end)) {
            System.out.println(count+". "+pathList);
            count++;
            return;
        }
  
        isVisited[start] = true;

        for (Integer i : adjList[start]) {
            if (!isVisited[i]) {
                pathList.add(i);
                printAllPathsUtil(i, end, isVisited, pathList);
                pathList.remove(i);
            }
        }
        isVisited[start] = false;
    }
    
}