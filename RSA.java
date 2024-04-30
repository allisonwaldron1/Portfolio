import java.math.BigInteger;

public class RSA {

    public static void main(String[] args) {
        // Bob's Public Keys
        BigInteger nBob = new BigInteger("4751614356616424867781976168280737158143706077825299390190036646711989408286996041120433429097816319");
        BigInteger eBob = new BigInteger("4685895394525945948602630590450251030824289205");

        // Alice's Public Keys
        BigInteger nAlice = new BigInteger("5027378074612278683849377061501198168112765099092211588948671006054146262763926543857634165199504551");
        BigInteger eAlice = new BigInteger("57323338227899193769901534310113340374743309");

        // Find the GCD using fast modular exponentiation and extended Euclidean algorithm
        BigInteger gcd = calculateGCD(nAlice, nBob);

        // Print the result
        System.out.println("GCD of the two numbers is: " + gcd);
        
        // Prime factor p
        BigInteger p = gcd;

        // Intercepted ciphertexts
        BigInteger cAliceToBob = new BigInteger("1046284573466962387739473944807380691612565121927521278523282411305437337626467565825335153065591711");
        BigInteger cBobToAlice = new BigInteger("3325523873963550704984437728904370519830678404172921362089587136490304638487650626956531260800780164");

        // Calculate private keys
        BigInteger phiBob = calculatePhi(p, nBob);
        BigInteger dBob = eBob.modInverse(phiBob);

        BigInteger phiAlice = calculatePhi(p, nAlice);
        BigInteger dAlice = eAlice.modInverse(phiAlice);

        // Decrypt messages
        BigInteger mAliceToBob = cAliceToBob.modPow(dBob, nBob);
        BigInteger mBobToAlice = cBobToAlice.modPow(dAlice, nAlice);

        String aasciiString = convertBack(mAliceToBob, 70);
        System.out.println("Alice to Bob: " + removePadding(aasciiString, 'X'));
        String basciiString = convertBack(mBobToAlice, 70);
        System.out.println("Bob to Alice: " + removePadding(basciiString, 'X'));
    }

    private static BigInteger calculatePhi(BigInteger p, BigInteger n) {
        BigInteger q = n.divide(p);
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }

    // Function to convert a BigInteger to its corresponding ASCII string
    private static String convertBack(BigInteger m, int blocksize) {
        StringBuilder message = new StringBuilder();

        for (int i = 0; i < blocksize; i++) {
            // Extract the value of the current character
            BigInteger charValue = m.mod(BigInteger.valueOf(26));
            int intValue = charValue.intValue();

            // Append the current character to the result
            message.insert(0, (char) ('A' + intValue));

            // Shift to the next character position
            m = m.divide(BigInteger.valueOf(26));
        }

        return message.toString();
    }

    // Function to remove padding characters from a string
    private static String removePadding(String input, char paddingChar) {
        int endIndex = input.length();
        while (endIndex > 0 && input.charAt(endIndex - 1) == paddingChar) {
            endIndex--;
        }
        return input.substring(0, endIndex);
    }
    
    // Function to calculate GCD using iterative extended Euclidean algorithm
    private static BigInteger calculateGCD(BigInteger a, BigInteger b) {
        BigInteger x = BigInteger.ZERO;
        BigInteger y = BigInteger.ONE;
        BigInteger lastX = BigInteger.ONE;
        BigInteger lastY = BigInteger.ZERO;
        BigInteger temp;

        while (!b.equals(BigInteger.ZERO)) {
            BigInteger quotient = a.divide(b);

            temp = a;
            a = b;
            b = temp.mod(b);

            temp = x;
            x = lastX.subtract(quotient.multiply(x));
            lastX = temp;

            temp = y;
            y = lastY.subtract(quotient.multiply(y));
            lastY = temp;
        }

        return a;
    }
}
