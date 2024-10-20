import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Sample {
    static class Node {
        int key;
        int value;
        int height;
        int size;
        long sum;
        Node left;
        Node right;
        Node parent;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.height = 1;
            this.size = 1;
            this.sum = value;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    static HashMap<Integer, Node> nodeMap = new HashMap<>();

    static Node findRoot(Node x) {
        while (x.parent != null) {
            x = x.parent;
        }

        return x;
    }

    // Update the height, size, and sum of node x
    static void update(Node x) {
        if (x == null) return;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        x.size = 1 + size(x.left) + size(x.right);
        x.sum = x.value + sum(x.left) + sum(x.right);
        if (x.left != null) x.left.parent = x;
        if (x.right != null) x.right.parent = x;
    }

    static int height(Node x) {
        return x == null ? 0 : x.height;
    }

    static int size(Node x) {
        return x == null ? 0 : x.size;
    }

    static long sum(Node x) {
        return x == null ? 0 : x.sum;
    }

    static int balanceFactor(Node x) {
        return x == null ? 0 : height(x.left) - height(x.right);
    }

    // Rotate right
    static Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        update(y);
        update(x);

        x.parent = y.parent;
        y.parent = x;
        if (T2 != null) T2.parent = y;

        return x;
    }

    // Rotate left
    static Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        update(x);
        update(y);

        y.parent = x.parent;
        x.parent = y;
        if (T2 != null) T2.parent = x;

        return y;
    }

    // Balance the node
    static Node balance(Node x) {
        update(x);
        int bf = balanceFactor(x);

        if (bf > 1) {
            if (balanceFactor(x.left) < 0) {
                x.left = rotateLeft(x.left);
            }
            x = rotateRight(x);
        } else if (bf < -1) {
            if (balanceFactor(x.right) > 0) {
                x.right = rotateRight(x.right);
            }
            x = rotateLeft(x);
        }
        return x;
    }

    // Insert a node into the AVL tree
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

    // Find the node with the minimum key
    static Node findMin(Node x) {
        while (x.left != null) x = x.left;
        return x;
    }

    // Remove the node with the minimum key
    static Node removeMin(Node x) {
        if (x.left == null) return x.right;
        x.left = removeMin(x.left);
        if (x.left != null) x.left.parent = x;
        return balance(x);
    }

    // Delete a node from the AVL tree
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

    // Merge two AVL trees
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

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line;
        int keyCounter = 0;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String[] nm = line.trim().split(" ");
            int n = Integer.parseInt(nm[0]);
            int m = Integer.parseInt(nm[1]);

            nodeMap.clear();

            // Initialize singleton sets
            for (int i = 1; i <= n; i++) {
                Node node = new Node(++keyCounter, i);
                nodeMap.put(i, node);
            }

            for (int i = 0; i < m; i++) {
                String[] cmd = br.readLine().trim().split(" ");
                int type = Integer.parseInt(cmd[0]);
                if (type == 1) {
                    int p = Integer.parseInt(cmd[1]);
                    int q = Integer.parseInt(cmd[2]);
                    Node nodeP = nodeMap.get(p);
                    Node nodeQ = nodeMap.get(q);
                    Node rootP = findRoot(nodeP);
                    Node rootQ = findRoot(nodeQ);
                    if (rootP != rootQ) {
                        Node newRoot = merge(rootP, rootQ);
                        newRoot.parent = null;
                    }
                } else if (type == 2) {
                    int p = Integer.parseInt(cmd[1]);
                    int q = Integer.parseInt(cmd[2]);
                    Node nodeP = nodeMap.get(p);
                    Node nodeQ = nodeMap.get(q);
                    Node rootP = findRoot(nodeP);
                    Node rootQ = findRoot(nodeQ);
                    if (rootP != rootQ) {
                        // Remove nodeP from its current tree
                        rootP = delete(rootP, nodeP.key);
                        if (rootP != null) rootP.parent = null;
                        nodeP.parent = null;
                        nodeP.left = null;
                        nodeP.right = null;
                        update(nodeP);
                        // Insert nodeP into rootQ
                        rootQ = insert(rootQ, nodeP);
                        rootQ.parent = null;
                    }
                } else if (type == 3) {
                    int p = Integer.parseInt(cmd[1]);
                    Node nodeP = nodeMap.get(p);
                    Node rootP = findRoot(nodeP);
                    System.out.println(rootP.size + " " + rootP.sum);
                }
            }
        }
    }
}
