import java.util.*;

public class signs {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        scanner.nextLine();

        List<Sign> signs = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String signText = scanner.nextLine();
            String middleLetters = getMiddleLetters(signText);
            signs.add(new Sign(signText, middleLetters));
        }

        signs.sort(Comparator.comparing(sign -> sign.middleLetters));

        for (Sign sign : signs) {
            System.out.println(sign.text);
        }

        scanner.close();
    }

    static String getMiddleLetters(String s) {
        int len = s.length();
        if (len % 2 == 0) {
            return s.substring(len / 2 - 1, len / 2 + 1);
        } else {
            return s.substring(len / 2, len / 2 + 1);
        }
    }

    static class Sign {
        String text;
        String middleLetters;

        Sign(String text, String middleLetters) {
            this.text = text;
            this.middleLetters = middleLetters;
        }
    }
}
