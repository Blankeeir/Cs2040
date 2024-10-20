package TakeHomeAssignment2;


import java.io.*;
import java.util.*;

public class joinstrings {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        
        String[] strings = new String[N + 1]; 
        int[] next = new int[N + 1];
        int[] tail = new int[N + 1];
        boolean[] isHead = new boolean[N + 1];
        
        for (int i = 1; i <= N; i++) {
            strings[i] = br.readLine();
            next[i] = -1;
            tail[i] = i;
            isHead[i] = true;
        }
        
        int numOps = N - 1;
        for (int i = 0; i < numOps; i++) {
            String[] tokens = br.readLine().split(" ");
            int a = Integer.parseInt(tokens[0]);
            int b = Integer.parseInt(tokens[1]);
            next[tail[a]] = b;
            tail[a] = tail[b];
            isHead[b] = false;
        }
        
        int head = -1;
        for (int i = 1; i <= N; i++) {
            if (isHead[i]) {
                head = i;
                break;
            }
        }
        
        StringBuilder sb = new StringBuilder();
        int current = head;
        while (current != -1) {
            sb.append(strings[current]);
            current = next[current];
        }
        
        System.out.println(sb.toString());
    }
}
