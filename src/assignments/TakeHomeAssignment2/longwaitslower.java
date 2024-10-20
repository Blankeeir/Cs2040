package TakeHomeAssignment2;

import java.io.*;
import java.util.*;

public class longwaitslower {
    static class Node {
        int id;
        int size;
        long priority;
        Node left, right;

        Node(int id) {
            this.id = id;
            this.size = 1;
            this.priority = rnd.nextLong();
        }
    }

    static Random rnd = new Random();
    static Node root = null;
    static int K;

    static void update(Node t) {
        if (t == null) return;
        t.size = 1;
        if (t.left != null) t.size += t.left.size;
        if (t.right != null) t.size += t.right.size;
    }

    static void split(Node t, int k, Node[] result) {
        if (t == null) {
            result[0] = result[1] = null;
            return;
        }
        int leftSize = (t.left != null) ? t.left.size : 0;
        if (k <= leftSize) {
            split(t.left, k, result);
            t.left = result[1];
            result[1] = t;
        } else {
            split(t.right, k - leftSize - 1, result);
            t.right = result[0];
            result[0] = t;
        }
        update(t);
    }

    static Node merge(Node l, Node r) {
        if (l == null) return r;
        if (r == null) return l;
        if (l.priority > r.priority) {
            l.right = merge(l.right, r);
            update(l);
            return l;
        } else {
            r.left = merge(l, r.left);
            update(r);
            return r;
        }
    }

    static Node insertAt(Node t, int pos, Node newNode) {
        Node[] splitNodes = new Node[2];
        split(t, pos, splitNodes);
        return merge(merge(splitNodes[0], newNode), splitNodes[1]);
    }

    static int findID(Node t, int pos) {
        int leftSize = (t.left != null) ? t.left.size : 0;
        if (pos == leftSize + 1) {
            return t.id;
        } else if (pos <= leftSize) {
            return findID(t.left, pos);
        } else {
            return findID(t.right, pos - leftSize - 1);
        }
    }

    public static void main(String[] args) throws IOException {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int Q = in.nextInt();
        K = in.nextInt();

        for (int q = 0; q < Q; q++) {
            String cmd = in.next();
            if (cmd.equals("queue")) {
                int x = in.nextInt();
                Node newNode = new Node(x);
                root = insertAt(root, (root != null) ? root.size : 0, newNode);
            } else if (cmd.equals("vip")) {
                int x = in.nextInt();
                Node newNode = new Node(x);
                root = insertAt(root, 0, newNode);
            } else if (cmd.equals("member")) {
                int x = in.nextInt();
                Node newNode = new Node(x);
                int pos = Math.min(K, (root != null) ? root.size : 0);
                root = insertAt(root, pos, newNode);
            } else if (cmd.equals("faster")) {
                if (K > 1) K--;
            } else if (cmd.equals("slower")) {
                K++;
            } else if (cmd.equals("findID")) {
                int pos = in.nextInt();
                out.println(findID(root, pos));
            }
        }

        out.flush();
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 1 << 16);
            tokenizer = null;
        }
        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                String s = reader.readLine();
                if (s == null) return null;
                tokenizer = new StringTokenizer(s);
            }
            return tokenizer.nextToken();
        }
        public int nextInt() throws IOException { return Integer.parseInt(next()); }
    }
}
