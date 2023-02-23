import java.util.*;
import java.io.*;


public class cardtrading {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        
        // read the first line of data
        String str = br.readLine();
        String[] read = str.split(" ");
        int N = Integer.parseInt(read[0]);
        int T = Integer.parseInt(read[1]);
        int K = Integer.parseInt(read[2]);
        
        // read the second line and get all the cards
        String[] string = br.readLine().split(" ");
        ArrayList<Integer> cards = new ArrayList<Integer>();
        for (int i = 0; i < N; i++) {
            cards.add(Integer.parseInt(string[i]));
        }
        
        // sort all the current cards
        Collections.sort(cards);

        // create an array for cardcount
        int[] tmp = new int[100000];
        for (int i=0; i<tmp.length; i++) {
          tmp[i] = 0;
        }
        for (int j=0; j<N; j++) {
          tmp[cards.get(j)-1] += 1;
        }
        
        // read all the prices
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i=0; i<T; i++) {
            read = br.readLine().split(" ");
            long buy = Long.parseLong(read[0]);
            long sell = Long.parseLong(read[1]);
            int cardcount = tmp[i];
            long total = 0;
            if (cardcount == 0) {
                total = 2*buy;
            }
            else if (cardcount == 1) {
                total = buy + sell;
            }
            else {
                total = sell*2;
            }
            list.add(new Card(i+1, buy, sell, cardcount, total));
        }
        
        
        // sort the list of cards we owned
        Collections.sort(list, (first, second) -> {
            return (int) (first.total - second.total); // ascending order
        });
        
        
        // compute the profit of selling all the cards we owned
        long profit = 0;
        for (Card card: list) {
            if (card.cardcount == 0) {
                profit += 0;
            }
            else if (card.cardcount == 1) {
                profit += card.sell ;
            }
            else {
                profit += card.sell * 2;
            }
        }
        
        
        
        // compute the real max profit
        int currPairs = 0;
        int index = 0;
        while (currPairs != K) {
            profit -= (list.get(index)).total;
            index += 1;
            currPairs += 1;
        }
        
        pw.write(Long.toString(profit));
        pw.close();
    }
}


class Card {
    // field
    public int num;
    public long buy;
    public long sell;
    public int cardcount;
    public long total;
    
    // constructor
    public Card(int num, long buy, long sell, int cardcount, long total) {
        this.num = num;
        this.buy = buy;
        this.sell = sell;
        this.cardcount = cardcount;
        this.total = total;
    }
}
