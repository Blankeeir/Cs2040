import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class weakvertices {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            if (!scanner.hasNextInt()) {
                break;
            }
            int n = scanner.nextInt();
            if (n == -1) {
                break;
            }

            
            boolean[][] adj = new boolean[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (scanner.hasNextInt()) {
                        int val = scanner.nextInt();
                        adj[i][j] = (val == 1);
                    } else {
                        // If input is not sufficient, assume no edge
                        adj[i][j] = false;
                    }
                }
            }


            List<Integer> weakVertices = new ArrayList<>();


            for (int vertex = 0; vertex < n; vertex++) {
                boolean isWeak = true;

          
                List<Integer> neighbors = new ArrayList<>();
                for (int neighbor = 0; neighbor < n; neighbor++) {
                    if (adj[vertex][neighbor]) {
                        neighbors.add(neighbor);
                    }
                }

               
                outerLoop:
                for (int i = 0; i < neighbors.size(); i++) {
                    for (int j = i + 1; j < neighbors.size(); j++) {
                        int neighbor1 = neighbors.get(i);
                        int neighbor2 = neighbors.get(j);
                        if (adj[neighbor1][neighbor2]) {
                           
                            isWeak = false;
                            break outerLoop;
                        }
                    }
                }


                if (isWeak) {
                    weakVertices.add(vertex);
                }
            }

            // Sort the weak vertices
            Collections.sort(weakVertices);

            // Prepare the output
            if (!weakVertices.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < weakVertices.size(); i++) {
                    sb.append(weakVertices.get(i));
                    if (i != weakVertices.size() - 1) {
                        sb.append(" ");
                    }
                }
                System.out.println(sb.toString());
            } else {
                // If there are no weak vertices, print an empty line
                System.out.println();
            }
        }

        scanner.close();
    }
}
