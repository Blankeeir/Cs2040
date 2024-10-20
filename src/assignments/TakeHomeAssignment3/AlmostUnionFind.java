import java.io.*;
import java.util.*;

public class AlmostUnionFind {
    static class Node {
        int key, value;  // key is unique identifier, value is the actual element
        int height, size;
        long sum;
        Node left, right, parent;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.height = 1;
            this.size = 1;
            this.sum = value;
        }
    }
    
    static Map<Integer, Node> nodeMap;
    static BufferedReader br;
    static PrintWriter out;
    
    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(System.out);
        solve();
        out.close();
    }
    
    static void solve() throws IOException {
        String line;
        int keyCounter = 0;
        
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            StringTokenizer st = new StringTokenizer(line);
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            
            // Initialize
            nodeMap = new HashMap<>();
            for (int i = 1; i <= n; i++) {
                Node node = new Node(++keyCounter, i);
                nodeMap.put(i, node);
            }
            
            // Process commands
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int type = Integer.parseInt(st.nextToken());
                
                if (type == 1) {
                    int p = Integer.parseInt(st.nextToken());
                    int q = Integer.parseInt(st.nextToken());
                    union(p, q);
                } else if (type == 2) {
                    int p = Integer.parseInt(st.nextToken());
                    int q = Integer.parseInt(st.nextToken());
                    move(p, q);
                } else {
                    int p = Integer.parseInt(st.nextToken());
                    query(p);
                }
            }
        }
    }
    
    static Node findRoot(Node x) {
        while (x.parent != null) x = x.parent;
        return x;
    }
    
    static void union(int p, int q) {
        Node rootP = findRoot(nodeMap.get(p));
        Node rootQ = findRoot(nodeMap.get(q));
        if (rootP != rootQ) {
            Node newRoot = merge(rootP, rootQ);
            newRoot.parent = null;
        }
    }
    
    static void move(int p, int q) {
        Node nodeP = nodeMap.get(p);
        Node nodeQ = nodeMap.get(q);
        Node rootP = findRoot(nodeP);
        Node rootQ = findRoot(nodeQ);
        
        if (rootP != rootQ) {
            // Remove p from its set
            rootP = delete(rootP, nodeP.key);
            if (rootP != null) rootP.parent = null;
            
            // Reset node P
            nodeP.parent = null;
            nodeP.left = null;
            nodeP.right = null;
            updateNode(nodeP);
            
            // Add to new set
            rootQ = insert(rootQ, nodeP);
            rootQ.parent = null;
        }
    }
    
    static void query(int p) {
        Node root = findRoot(nodeMap.get(p));
        out.println(root.size + " " + root.sum);
    }
    
    // AVL Tree operations
    static void updateNode(Node x) {
        if (x == null) return;
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        x.size = 1 + getSize(x.left) + getSize(x.right);
        x.sum = x.value + getSum(x.left) + getSum(x.right);
        if (x.left != null) x.left.parent = x;
        if (x.right != null) x.right.parent = x;
    }
    
    static int getHeight(Node x) { return x == null ? 0 : x.height; }
    static int getSize(Node x) { return x == null ? 0 : x.size; }
    static long getSum(Node x) { return x == null ? 0 : x.sum; }
    
    static Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        
        x.right = y;
        y.left = T2;
        
        updateNode(y);
        updateNode(x);
        
        x.parent = y.parent;
        y.parent = x;
        if (T2 != null) T2.parent = y;
        
        return x;
    }
    
    static Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        
        y.left = x;
        x.right = T2;
        
        updateNode(x);
        updateNode(y);
        
        y.parent = x.parent;
        x.parent = y;
        if (T2 != null) T2.parent = x;
        
        return y;
    }
    
    static Node balance(Node x) {
        updateNode(x);
        int balance = getHeight(x.left) - getHeight(x.right);
        
        if (balance > 1) {
            if (getHeight(x.left.left) < getHeight(x.left.right)) {
                x.left = rotateLeft(x.left);
            }
            return rotateRight(x);
        }
        if (balance < -1) {
            if (getHeight(x.right.right) < getHeight(x.right.left)) {
                x.right = rotateRight(x.right);
            }
            return rotateLeft(x);
        }
        return x;
    }
    
    static Node insert(Node root, Node node) {
        if (root == null) return node;
        if (node.key < root.key) {
            root.left = insert(root.left, node);
            root.left.parent = root;
        } else {
            root.right = insert(root.right, node);
            root.right.parent = root;
        }
        return balance(root);
    }
    
    static Node findMin(Node x) {
        while (x.left != null) x = x.left;
        return x;
    }
    
    static Node removeMin(Node x) {
        if (x.left == null) return x.right;
        x.left = removeMin(x.left);
        if (x.left != null) x.left.parent = x;
        return balance(x);
    }
    
    static Node delete(Node root, int key) {
        if (root == null) return null;
        if (key < root.key) {
            root.left = delete(root.left, key);
            if (root.left != null) root.left.parent = root;
        } else if (key > root.key) {
            root.right = delete(root.right, key);
            if (root.right != null) root.right.parent = root;
        } else {
            Node left = root.left;
            Node right = root.right;
            if (left != null) left.parent = null;
            if (right != null) right.parent = null;
            if (right == null) return left;
            Node min = findMin(right);
            min.right = removeMin(right);
            if (min.right != null) min.right.parent = min;
            min.left = left;
            if (left != null) left.parent = min;
            return balance(min);
        }
        return balance(root);
    }
    
    static Node merge(Node left, Node right) {
        if (left == null) return right;
        if (right == null) return left;
        Node min = findMin(right);
        right = delete(right, min.key);
        min.left = left;
        min.right = right;
        if (left != null) left.parent = min;
        if (right != null) right.parent = min;
        return balance(min);
    }
}