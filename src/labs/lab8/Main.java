package labs.lab8;

import java.util.*;
import java.io.*;

public class Main { // Renamed class to 'Main'
    private static char[][] grid;
    private static int rows, cols;
    private static boolean[][] visited; // Changed to boolean for clarity
    private static final int[] dx = {0, 0, 1, -1};
    private static final int[] dy = {1, -1, 0, 0};

    public static boolean bfs(int r, int c) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{r, c});
        visited[r][c] = true;
        boolean hasLand = false;
        if (grid[r][c] == 'L') {
            hasLand = true;
        }
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currR = current[0];
            int currC = current[1];
            for (int d = 0; d < 4; d++) {
                int newR = currR + dx[d];
                int newC = currC + dy[d];
                if (newR >= 0 && newR < rows && newC >= 0 && newC < cols && !visited[newR][newC]) {
                    if (grid[newR][newC] == 'L' || grid[newR][newC] == 'C') {
                        visited[newR][newC] = true;
                        queue.offer(new int[]{newR, newC});
                        if (grid[newR][newC] == 'L') {
                            hasLand = true;
                        }
                    }
                }
            }
        }
        return hasLand;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Since the problem specifies a single test case per run, we can read just once
        if (sc.hasNextInt()) { // Changed from while loop to if statement
            int r = sc.nextInt();
            int c = sc.nextInt();
            sc.nextLine(); // consume the remaining line
            grid = new char[r][c];
            for (int i = 0; i < r; i++) {
                String line = sc.nextLine();
                for (int j = 0; j < c; j++) {
                    grid[i][j] = line.charAt(j); // Fill the grid
                }
            }
            rows = r;
            cols = c;
            visited = new boolean[r][c]; // Initialize visited as boolean
            int islandCount = 0;
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    if (!visited[i][j] && (grid[i][j] == 'L' || grid[i][j] == 'C')) {
                        boolean hasLand = bfs(i, j);
                        if (hasLand) {
                            islandCount++;
                        }
                    }
                }
            }
            System.out.println(islandCount);
        }
        sc.close();
    }
}
