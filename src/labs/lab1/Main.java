import java.util.Scanner;

class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
    
        int numOfRes = 0;
        

        if (sc.hasNextInt()) {
            numOfRes += 1;
            if (numOfRes >= 1) {
                String[] resNames = new String[numOfRes];
                for (int i = 1; i <= numOfRes; i++) {
                    // interate through each restaurant and take in inputs from user

                    if (sc.hasNextInt()) {
                        // 
                    } {
                        throw new Exception(String.format("Please input number of menue items for restaurant %d", i));
                    }
 
                }
            }
        
        } else {
            throw new Exception("please input the number of the restaurant!");
        }

        sc.close();
    }

}
