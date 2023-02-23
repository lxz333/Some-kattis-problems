import java.io.*;

class Thing {
    // field
    public String curr;
    public Thing back;
    public Thing next;
    
    // constructor
    public Thing(String curr) {
        this.curr = curr;
        this.back = null;
        this.next = null;
    }
}


public class joinstrings {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        
        // read the input data
        String str = br.readLine();
        int totalNum = Integer.parseInt(str);
      
        Thing[] things = new Thing[totalNum];
        for (int i=0; i<totalNum; i++) {
          things[i] = new Thing(br.readLine());
        }
        
        // create a linked list of all the Things
        int A = 1;
        int B = 1;
        for (int j=0; j<totalNum-1; j++) {
            String[] read = br.readLine().split(" ");
            A = Integer.parseInt(read[0]);
            B = Integer.parseInt(read[1]);
            Thing thingA = things[A-1];
            Thing thingB = things[B-1];
            
            if (thingA.back != null) {
                thingA.back.next = thingB;
            } 
            
            if (thingB.next == null) {
                thingA.back = thingB;
            } else {
                thingA.back = thingB.back;
            }
                
            
            
            if (thingA.next == null) {
                thingA.next = thingB;
            }
                           
            
        }
        
        // loop around the list we created to get the concatenated result
        Thing i = things[A-1];
        while (i.next != null) {
            pw.print(i.curr);
            i = i.next;
        }
        pw.print(i.curr);
        pw.close();
    }
}
