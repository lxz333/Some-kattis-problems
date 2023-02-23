import java.io.*;
import java.util.*;

public class nicknames {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        
        String str = br.readLine();
        int nameNum = Integer.parseInt(str);
        
        // seperate names into different groups of ALVTree based on their first char
        HashMap<Character, AVLTree> map = new HashMap<>();
        
        for (int i=0; i<nameNum; i++) {
            String name = br.readLine();
            if (!map.containsKey(name.charAt(0))) {
                map.put(name.charAt(0), new AVLTree());
            }
            map.get(name.charAt(0)).insert(name);
        }
        
        // create a hashmap for all nicknames to save time on repeated calling of the same nickname
        str = br.readLine();
        int nickNum = Integer.parseInt(str);
        
        HashMap<String, Integer> map2 = new HashMap<>();
        
        for (int j=0; j<nickNum; j++) {
            String nickname = br.readLine();
            if (!map2.containsKey(nickname)) {
                // if the first char of the nickname is not stored in map, just return 0
                if (!map.containsKey(nickname.charAt(0))) {
                    pw.println(0);
                    continue;
                }
                // if the first char of the nickname is contained in map, put the result into map2 and print it
                int result = map.get(nickname.charAt(0)).getCount(nickname);
                map2.put(nickname, result);
                pw.println(result);
            } else {
                pw.println(map2.get(nickname));
            }
        }
        pw.close();
    }
}

class Vertex {
    // field
    public String key;
    public int height;
    public Vertex parent, left, right;
    
    // constructor
    Vertex(String key) {
        this.key = key;
        this.height = 0;
        this.parent = null;
        this.left = null;
        this.right = null;
    }
}

class AVLTree {
    public Vertex root;
    
    public AVLTree() {
        this.root = null;
    }
    
    // method
    public int height(Vertex node) {
        if (node == null) {
            return -1;
        } else {
            return node.height;
        }
    }
    
    public void updateHeight(Vertex node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }
    
    public int getBalance(Vertex node) {
        if (node == null) {
            return 0;
        } else {
            return height(node.left) - height(node.right);
        }
    } 
    
    public Vertex rotateLeft(Vertex T) {
        Vertex w = T.right;
        w.parent = T.parent;
        T.parent = w;
        T.right = w.left;
        if (w.left != null) {
            w.left.parent = T;
        }
        w.left = T;
        updateHeight(T);
        updateHeight(w);
        return w;
    }
    
    public Vertex rotateRight(Vertex T) {
        Vertex w = T.left;
        w.parent = T.parent;
        T.parent = w;
        T.left = w.right;
        if (w.right != null) {
            w.right.parent = T;
        }
        w.right = T;
        updateHeight(T);
        updateHeight(w);
        return w;
    }
    
    public Vertex rebalance(Vertex T) {
        int bf = getBalance(T);
        int bfLeft = getBalance(T.left);
        int bfRight = getBalance(T.right);
        
        // 4 possible cases
        if (bf == 2 && bfLeft<=1 && bfLeft>=0) {
            return rotateRight(T);
        } else if (bf == 2 && bfLeft == -1) {
            T.left = rotateLeft(T.left);
            return rotateRight(T);
        } else if (bf == -2 && bfRight>=-1 && bfRight<=0) {
            return rotateLeft(T);
        } else if (bf == -2 && bfRight == 1) {
            T.right = rotateRight(T.right);
            return rotateLeft(T);
        }
        return T;
    }
    
    // define insert starting from random Vertex T
    public Vertex insert(Vertex T, String key) throws Exception {
        if (T == null) {
            return new Vertex(key);
        } else if (key.compareTo(T.key)>0) {
            T.right = insert(T.right, key);
        } else if (key.compareTo(T.key)<0) {
            T.left = insert(T.left, key);
        } else {
            throw new Exception("Duplicate keys!");
        }
        updateHeight(T);
        return rebalance(T);
    }
    
    // implement insert from the root
    public void insert(String key) throws Exception {
        this.root = insert(root, key);
    }
    
    // define getCount from random Vertex T
    public int getCount(String nick, Vertex T) {
        if (T == null) {
            return 0;
        } else if (T.key.indexOf(nick) == 0) {
            return 1 + getCount(nick, T.left) + getCount(nick, T.right);
        } else if (T.key.compareTo(nick) > 0) {
            return getCount(nick, T.left);
        } else {
            return getCount(nick, T.right);
        }
    }
    
    // implement getCount from the root
    public int getCount(String nick) {
        return getCount(nick, this.root);
    }
}
