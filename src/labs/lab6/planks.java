import java.io.*;
import java.util.*;

public class planks {
    static class Plank implements Comparable<Plank> {
        int W_p;
        int L_p;
        int id;

        public Plank(int W_p, int L_p, int id) {
            this.W_p = W_p;
            this.L_p = L_p;
            this.id = id;
        }

        @Override
        // override compareTo to sort by W_p ascending, id ascending
        public int compareTo(Plank other) {
            if (this.W_p != other.W_p) {
                return Integer.compare(this.W_p, other.W_p);
            }
            return Integer.compare(this.id, other.id);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // To write output efficiently
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        int Q = Integer.parseInt(br.readLine());
        // TreeMap with key as L_p, value as TreeSet of Planks sorted by W_p ascending, id ascending
        TreeMap<Integer, TreeSet<Plank>> treeMap = new TreeMap<>();
        int plankId = 0;

        for (int i = 0; i < Q; i++) {
            String line = br.readLine();
            if (line.charAt(0) == 'a') {
                // Add operation
                // line format: a W_p L_p
                int firstSpace = line.indexOf(' ');
                int secondSpace = line.indexOf(' ', firstSpace + 1);
                int W_p = Integer.parseInt(line.substring(firstSpace + 1, secondSpace));
                int L_p = Integer.parseInt(line.substring(secondSpace + 1));
                Plank plank = new Plank(W_p, L_p, plankId++);
                treeMap.computeIfAbsent(L_p, k -> new TreeSet<>()).add(plank);
            } else if (line.charAt(0) == 'c') {
                // Chase operation
                // line format: c X
                int space = line.indexOf(' ');
                int X = Integer.parseInt(line.substring(space + 1));

                // Select A
                Integer keyA = treeMap.floorKey(X);
                TreeSet<Plank> setA = treeMap.get(keyA);
                Plank plankA = setA.first();
                // Remove plankA
                setA.remove(plankA);
                if (setA.isEmpty()) {
                    treeMap.remove(keyA);
                }

                // Select B
                Integer keyB = treeMap.ceilingKey(X);
                TreeSet<Plank> setB = treeMap.get(keyB);
                Plank plankB = setB.last();
                // Remove plankB
                setB.remove(plankB);
                if (setB.isEmpty()) {
                    treeMap.remove(keyB);
                }

                // Compute E
                long E = (1L + plankA.W_p + plankB.W_p) * (1L + Math.abs((long)plankA.L_p - (long)plankB.L_p));
                sb.append(E).append('\n');
            }
        }
        bw.write(sb.toString());
        bw.flush();
    }
}