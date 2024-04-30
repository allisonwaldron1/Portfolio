/* Allison Waldron
 * Hmk 5 # 9
 * Program designed to calculate the sum of the cycle lengths for each possible base,
 *  1 through p-1, inclusive for exponentiation mod.  
 *  
 *  First line contains a single integer, n, the number of input cases.
 *  The input cases follow, one per line.
 */
import java.util.Scanner;

public class CycleLengths {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read first line, n, the number of input cases.
        int n = scanner.nextInt();
        
        //System.out.println("p value:");
   
        // Process each input case, one per line
        for (int i = 0; i < n; i++) {
            int p = scanner.nextInt();
            long result = calculateCycleLength(p);
            System.out.println(result);
        }
        // Close scanner
        scanner.close();
    }

    public static long calculateCycleLength(int p) {
        int[] cycleLengths = new int[p - 1];
        for (int base = 1; base < p; base++) {
            int num = 1;
            int cycleLength = 0;
            while (num != 0) {
                num = (num * base) % p;
                cycleLength++;
                if (num == 1) {
                    break;
                }
            }
            cycleLengths[base - 1] = cycleLength;
        }
        
        int sumOfCycleLengths = 0;
        for (int length : cycleLengths) {
            sumOfCycleLengths += length;
        }
        
        return sumOfCycleLengths;
    }
}
