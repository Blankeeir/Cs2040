import java.util.HashMap;
import java.util.Scanner;

public class t9spelling {

    private static HashMap<Character, String> buildKeyMapping() {
        HashMap<Character, String> keyMap = new HashMap<>();
        keyMap.put('a', "2");
        keyMap.put('b', "22");
        keyMap.put('c', "222");
        keyMap.put('d', "3");
        keyMap.put('e', "33");
        keyMap.put('f', "333");
        keyMap.put('g', "4");
        keyMap.put('h', "44");
        keyMap.put('i', "444");
        keyMap.put('j', "5");
        keyMap.put('k', "55");
        keyMap.put('l', "555");
        keyMap.put('m', "6");
        keyMap.put('n', "66");
        keyMap.put('o', "666");
        keyMap.put('p', "7");
        keyMap.put('q', "77");
        keyMap.put('r', "777");
        keyMap.put('s', "7777");
        keyMap.put('t', "8");
        keyMap.put('u', "88");
        keyMap.put('v', "888");
        keyMap.put('w', "9");
        keyMap.put('x', "99");
        keyMap.put('y', "999");
        keyMap.put('z', "9999");
        keyMap.put(' ', "0");

        return keyMap;
    }

    private static String translate(String message, HashMap<Character, String> keyMap) {
        StringBuilder result = new StringBuilder();
        char prevKey = '_';

        for (char ch : message.toCharArray()) {
            String value = keyMap.get(ch);
            // System.out.println(value);

            if (!result.isEmpty() && value.charAt(0) == prevKey) {
                result.append(" "); 
            }

            result.append(value);
            prevKey = value.charAt(value.length() - 1);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = Integer.parseInt(scanner.nextLine()); 

        HashMap<Character, String> keyMap = buildKeyMapping();

        // Iterate over each test case
        for (int i = 1; i <= num; i++) {
            String res = scanner.nextLine(); 
            res = translate(res, keyMap);
            System.out.println("Case #" + i + ": " + res); 
        }

        scanner.close();
    }
}
