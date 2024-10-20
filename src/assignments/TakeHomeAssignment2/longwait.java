package TakeHomeAssignment2;

import java.io.*;
import java.util.*;

public class longwait {
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

    static class XorShift {
        private long seed;

        public XorShift(long seed) {
            this.seed = seed;
        }

        public long nextLong() {
            seed ^= (seed << 13);
            seed ^= (seed >>> 7);
            seed ^= (seed << 17);
            return seed;
        }
    }

    static XorShift rnd = new XorShift(System.nanoTime());
    static Node root = null;
    static int K;
    static Node[] splitNodes = new Node[2];

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
            update(t);
        } else {
            split(t.right, k - leftSize - 1, result);
            t.right = result[0];
            result[0] = t;
            update(t);
        }
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
        splitNodes[0] = splitNodes[1] = null;
        split(t, pos, splitNodes);
        return merge(merge(splitNodes[0], newNode), splitNodes[1]);
    }

    static int findID(Node t, int pos) {
        while (t != null) {
            int leftSize = (t.left != null) ? t.left.size : 0;
            if (pos == leftSize + 1) {
                return t.id;
            } else if (pos <= leftSize) {
                t = t.left;
            } else {
                pos = pos - leftSize - 1;
                t = t.right;
            }
        }
        return -1; // Should not happen
    }

    public static void main(String[] args) throws IOException {
        Reader in = new Reader();
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

    // Fast input reader
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String nextLine() throws IOException {
            byte[] buf = new byte[1024]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n') {
                    if (cnt != 0) break;
                    else continue;
                }
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
        }

        public String next() throws IOException {
            int c;
            while ((c = read()) != -1 && isSpaceChar(c)) ;
            if (c == -1) return null;
            StringBuilder sb = new StringBuilder();
            do {
                sb.append((char) c);
            } while ((c = read()) != -1 && !isSpaceChar(c));
            return sb.toString();
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = (byte) read();
            while (c <= ' ') c = (byte) read();
            boolean neg = c == '-';
            if (neg) c = (byte) read();
            do {
                ret = ret * 10 + c - '0';
                c = (byte) read();
            } while (c >= '0' && c <= '9');
            return neg ? -ret : ret;
        }

        private boolean isSpaceChar(int c) {
            return c <= ' ';
        }

        private int read() throws IOException {
            if (bufferPointer == bytesRead) {
                fillBuffer();
                if (bytesRead == -1)
                    return -1;
            }
            return buffer[bufferPointer++];
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }
}
