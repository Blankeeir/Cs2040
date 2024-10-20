import java.util.*;

class Node {
    int id;
    int status;

    public Node(int id) {
        this.id = id;
        this.status = 1;
    }

    Node(int id, int status) {
        this.id = id;
        this.status = status;
    }

    public boolean isPerson() {
        return this.status == 1;
    }

    public boolean isFist() {
        return this.status == 2;
    }

    public boolean isPalm() {
        return this.status == 3;
    }

    public Node increment() {
        return new Node(this.id, this.status + 1);
    }
}

class coconut {


    public static ArrayList<Node> initiateList(int n) {
        ArrayList<Node> arr = new ArrayList<Node>();
        for(int i = 0; i < n; i++) {
            arr.add(new Node(i));
        }
        return arr;
    }
    public static void main(String[] args) {
        int winner = -1;
        Scanner sc = new Scanner(System.in);
        int s = sc.nextInt();
        int n = sc.nextInt();
        ArrayList<Node> queue = initiateList(n);

        int currNode = 0;

        while(queue.size() > 1) {
            int size =  queue.size();
            currNode = (currNode + s) % size;

        }
    }
}