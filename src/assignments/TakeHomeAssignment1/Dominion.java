import java.io.*;
import java.util.*;

public class Dominion {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int T = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[] counts = new int[T + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            int type = Integer.parseInt(st.nextToken());
            counts[type]++;
        }

        long[] a = new long[T + 1];
        long[] b = new long[T + 1];
        for (int i = 1; i <= T; i++) {
            st = new StringTokenizer(br.readLine());
            a[i] = Long.parseLong(st.nextToken());
            b[i] = Long.parseLong(st.nextToken());
        }

        long sumAllSellProfit = 0;
        for (int i = 1; i <= T; i++) {
            if (counts[i] == 2) {
                sumAllSellProfit += 2 * b[i];
            } else if (counts[i] == 1) {
                sumAllSellProfit += b[i];
            }
        }

        long[] selectionCosts = new long[T];
        for (int i = 1; i <= T; i++) {
            if (counts[i] == 2) {
                selectionCosts[i - 1] = 2 * b[i];
            } else if (counts[i] == 1) {
                selectionCosts[i - 1] = b[i] + a[i];
            } else {
                selectionCosts[i - 1] = 2 * a[i];
            }
        }

        Arrays.sort(selectionCosts);

        long sumSelectedCost = 0;
        for (int i = 0; i < K; i++) {
            sumSelectedCost += selectionCosts[i];
        }

        long totalProfit = sumAllSellProfit - sumSelectedCost;

        System.out.println(totalProfit);
    }
}
