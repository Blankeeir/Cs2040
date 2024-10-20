package labs.lab8;

import java.util.*;

class islands3 {
    static int r, c;
    static char[][] grid;
    static boolean[][] visited;
    static int[] dr = {-1, 1, 0, 0}; // Up, Down
    static int[] dc = {0, 0, -1, 1}; // Left, Right

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()) {
            r = sc.nextInt();
            c = sc.nextInt();
            sc.nextLine();
            grid = new char[r][c];
            for(int i=0; i<r; i++) {
                String line = sc.nextLine();
                for(int j=0; j<c; j++) {
                    grid[i][j] = line.charAt(j);
                }
            }
            visited = new boolean[r][c];
            int islandCount = 0;
            for(int i=0; i<r; i++) {
                for(int j=0; j<c; j++) {
                    if(!visited[i][j] && (grid[i][j] == 'L' || grid[i][j] == 'C')) {
                        boolean hasLand = bfs(i, j);
                        if(hasLand) {
                            islandCount++;
                        }
                    }
                }
            }
            System.out.println(islandCount);
        }
    }

    static boolean bfs(int startR, int startC) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startR, startC});
        visited[startR][startC] = true;
        boolean hasLand = false;
        if(grid[startR][startC] == 'L') {
            hasLand = true;
        }
        while(!queue.isEmpty()) {
            int[] current = queue.poll();
            int currR = current[0];
            int currC = current[1];
            for(int d=0; d<4; d++) {
                int newR = currR + dr[d];
                int newC = currC + dc[d];
                if(newR >=0 && newR < r && newC >=0 && newC < c && !visited[newR][newC]) {
                    if(grid[newR][newC] == 'L' || grid[newR][newC] == 'C') {
                        visited[newR][newC] = true;
                        queue.offer(new int[]{newR, newC});
                        if(grid[newR][newC] == 'L') {
                            hasLand = true;
                        }
                    }
                }
            }
        }
        return hasLand;
    }
}