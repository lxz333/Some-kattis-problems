import java.io.*;
import java.util.*;

class Domino {
    public int vertex;
    public int down;
    public int visited;
    
    public Domino(int vertex) {
        this.vertex = vertex;
        this.down = 0;
        this.visited = 0;
    }
}


public class dominos {
    public static void main(String arg[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        
        String str = br.readLine();
        int testNum = Integer.parseInt(str);
        
        for (int i=0; i<testNum; i++) {
            str = br.readLine();
            String[] read = str.split(" ");
            int dominoNum = Integer.parseInt(read[0]);
            int lineNum = Integer.parseInt(read[1]);
            
            // create adjList to store all the edges
            ArrayList[] adjList = new ArrayList[dominoNum];
            for (int j=0; j<dominoNum; j++) {
                adjList[j] = new ArrayList<Integer>();
            }
        
            // store edges into adjList
            for (int m=0; m<lineNum; m++) {
                read = br.readLine().split(" ");
                int x = Integer.parseInt(read[0]) - 1;
                int y = Integer.parseInt(read[1]) - 1;
                adjList[x].add(y);
            }
        
            int result = 0;
            Domino[] array = new Domino[dominoNum];
            for (int b=0; b<dominoNum; b++) {
                array[b] = new Domino(b);
            }
            
            // rearrange the adjList into topological order
            ArrayList<Integer> topoList = new ArrayList<>();
            for (int k=0; k<dominoNum; k++) {
                if (array[k].visited != 1) {
                    Sort.Topo(k, topoList, adjList, array);
                }
            }
            Collections.reverse(topoList);
            
            // DFS
            for (int n=0; n<dominoNum; n++) {
                int vertex = topoList.get(n);
                if (array[vertex].down == 0) {
                    array[vertex].down = 1;
                    result += 1;
                    DFS.DFSrec(vertex, adjList, array);
                }
            }
            pw.println(result);
        }
        pw.close();
    } 
}

class DFS {
    public static void DFSrec(int vertex, ArrayList[] adjList, Domino[] array) {
        if (array[vertex].down != 1) {
            array[vertex].down = 1;
        }
        for (int i=0; i<adjList[vertex].size(); i++) {
            if (array[(int) adjList[vertex].get(i)].down != 1) {
                DFSrec((int) adjList[vertex].get(i), adjList, array);
            }
        }
    }
}

class Sort {
    public static void Topo(int vertex, ArrayList<Integer> topoList, ArrayList[] adjList, Domino[] array) {
        if (array[vertex].visited != 1) {
            array[vertex].visited = 1;
        }
        for (int i=0; i<adjList[vertex].size(); i++) {
            if (array[(int) adjList[vertex].get(i)].visited != 1) {
                Topo((int) adjList[vertex].get(i), topoList, adjList, array);
            }
        }
        topoList.add(vertex);
    }
}
