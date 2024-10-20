import java.io.*;
import java.util.StringTokenizer;

class Nicknames {
    // AVL Node class
    static class AVLNode {
        String key;
        AVLNode left, right;
        int height;
        int size;

        AVLNode(String key) {
            this.key = key;
            this.height = 1;
            this.size = 1;
        }
    }

    // AVL Tree class with insert and countLessThan
    static class AVLTree {
        AVLNode root;

        // Get height of a node
        int height(AVLNode N) {
            if (N == null)
                return 0;
            return N.height;
        }

        // Get size of a node
        int size(AVLNode N) {
            if (N == null)
                return 0;
            return N.size;
        }

        // Right rotate
        AVLNode rightRotate(AVLNode y) {
            AVLNode x = y.left;
            AVLNode T2 = x.right;

            // Perform rotation
            x.right = y;
            y.left = T2;

            // Update heights
            y.height = Math.max(height(y.left), height(y.right)) + 1;
            x.height = Math.max(height(x.left), height(x.right)) + 1;

            // Update sizes
            y.size = size(y.left) + size(y.right) + 1;
            x.size = size(x.left) + size(x.right) + 1;

            // Return new root
            return x;
        }

        // Left rotate
        AVLNode leftRotate(AVLNode x) {
            AVLNode y = x.right;
            AVLNode T2 = y.left;

            // Perform rotation
            y.left = x;
            x.right = T2;

            // Update heights
            x.height = Math.max(height(x.left), height(x.right)) + 1;
            y.height = Math.max(height(y.left), height(y.right)) + 1;

            // Update sizes
            x.size = size(x.left) + size(x.right) + 1;
            y.size = size(y.left) + size(y.right) + 1;

            // Return new root
            return y;
        }

        // Get balance factor
        int getBalance(AVLNode N) {
            if (N == null)
                return 0;
            return height(N.left) - height(N.right);
        }

        // Insert a key into the AVL tree
        AVLNode insert(AVLNode node, String key) {
            // Perform normal BST insertion
            if (node == null)
                return new AVLNode(key);

            if (key.compareTo(node.key) < 0)
                node.left = insert(node.left, key);
            else
                node.right = insert(node.right, key);

            // Update height and size
            node.height = 1 + Math.max(height(node.left), height(node.right));
            node.size = 1 + size(node.left) + size(node.right);

            // Get balance factor
            int balance = getBalance(node);

            // If node is unbalanced, perform rotations

            // Left Left Case
            if (balance > 1 && key.compareTo(node.left.key) < 0)
                return rightRotate(node);

            // Right Right Case
            if (balance < -1 && key.compareTo(node.right.key) > 0)
                return leftRotate(node);

            // Left Right Case
            if (balance > 1 && key.compareTo(node.left.key) > 0) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }

            // Right Left Case
            if (balance < -1 && key.compareTo(node.right.key) < 0) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }

            // Return the unchanged node
            return node;
        }

        // Public insert method
        void insert(String key) {
            root = insert(root, key);
        }

        // Count number of keys less than the given key
        int countLessThan(AVLNode node, String key) {
            if (node == null)
                return 0;
            if (key.compareTo(node.key) <= 0) {
                return countLessThan(node.left, key);
            } else {
                return size(node.left) + 1 + countLessThan(node.right, key);
            }
        }

        // Public countLessThan method
        int countLessThan(String key) {
            return countLessThan(root, key);
        }
    }

    // Kattio class as provided
    static class Kattio extends PrintWriter {
        private BufferedReader r;
        private StringTokenizer st;
        private String token;

        public Kattio(InputStream i) {
            super(new BufferedOutputStream(System.out));
            r = new BufferedReader(new InputStreamReader(i));
        }

        public Kattio(InputStream i, OutputStream o) {
            super(new BufferedOutputStream(o));
            r = new BufferedReader(new InputStreamReader(i));
        }

        public boolean hasMoreTokens() {
            return peekToken() != null;
        }

        public int getInt() {
            return Integer.parseInt(nextToken());
        }

        public double getDouble() {
            return Double.parseDouble(nextToken());
        }

        public long getLong() {
            return Long.parseLong(nextToken());
        }

        public String getWord() {
            return nextToken();
        }

        private String peekToken() {
            if (token == null)
                try {
                    while (st == null || !st.hasMoreTokens()) {
                        String line = r.readLine();
                        if (line == null)
                            return null;
                        st = new StringTokenizer(line);
                    }
                    token = st.nextToken();
                } catch (IOException e) {
                }
            return token;
        }

        private String nextToken() {
            String ans = peekToken();
            token = null;
            return ans;
        }
    }

    public static void main(String[] args) throws IOException {
        Kattio io = new Kattio(System.in, System.out);

        // Read number of names
        while (io.hasMoreTokens()) {
            int A = io.getInt();
            AVLTree tree = new AVLTree();

            // Read A names and insert into AVL tree
            for (int i = 0; i < A; i++) {
                String name = io.getWord();
                tree.insert(name);
            }

            // Read number of nicknames
            if (!io.hasMoreTokens())
                break;
            int B = io.getInt();

            // Process B nicknames
            for (int i = 0; i < B; i++) {
                String nickname = io.getWord();
                String start = nickname;
                // Using '{' which is after 'z' in ASCII
                String end = nickname + '{';
                int count = tree.countLessThan(end) - tree.countLessThan(start);
                io.println(count);
            }
        }

        io.close();
    }
}
