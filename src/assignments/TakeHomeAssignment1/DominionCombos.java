import java.util.*;

public class DominionCombos {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Read input values N, T, and K
        int N = sc.nextInt();
        int T = sc.nextInt();
        int K = sc.nextInt();
        
        // Array to track the count of each card type
        int[] cardCounts = new int[T + 1];
        
        // Read Anthony's current deck
        for (int i = 0; i < N; i++) {
            int cardType = sc.nextInt();
            cardCounts[cardType]++;
        }
        
        // Arrays to store buy and sell prices for each card type
        int[] buyPrice = new int[T + 1];
        int[] sellPrice = new int[T + 1];
        
        // Read the buy and sell prices for each card type
        for (int i = 1; i <= T; i++) {
            buyPrice[i] = sc.nextInt();
            sellPrice[i] = sc.nextInt();
        }
        
        // Lists to store profit or cost of forming combos
        List<Integer> profits = new ArrayList<>();
        
        // First pass: Count how many existing combos Anthony has
        int currentCombos = 0;
        
        for (int i = 1; i <= T; i++) {
            if (cardCounts[i] == 2) {
                // Already a combo
                currentCombos++;
            } else if (cardCounts[i] == 1) {
                // Can either buy one more to form a combo or sell the one card
                int costToBuy = buyPrice[i];
                int profitFromSelling = sellPrice[i];
                // Profit from selling 1 card vs cost of buying 1 more to form combo
                profits.add(Math.max(-costToBuy, profitFromSelling));
            } else {
                // No cards of this type, can buy two to form a combo
                int costToBuyTwo = 2 * buyPrice[i];
                profits.add(-costToBuyTwo);
            }
        }
        
        // Sort profits to prioritize the most profitable combos
        Collections.sort(profits, Collections.reverseOrder());
        
        // Calculate the maximum profit Anthony can make
        int profit = 0;
        
        // First take the most profitable options until we have exactly K combos
        int neededCombos = K - currentCombos;
        for (int i = 0; i < neededCombos; i++) {
            profit += profits.get(i);
        }
        
        // Output the result (profit or loss)
        System.out.println(profit);
    }
}
