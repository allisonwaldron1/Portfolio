import java.math.BigInteger;

public class ElGamalDecrypt {

    // Function for fast modular exponentiation
    public static BigInteger modPow(BigInteger base, BigInteger exponent, BigInteger modulus) {
        BigInteger result = BigInteger.ONE;
        base = base.mod(modulus);
        while (exponent.compareTo(BigInteger.ZERO) > 0) {
            if (exponent.testBit(0)) {
                result = (result.multiply(base)).mod(modulus);
            }
            exponent = exponent.shiftRight(1);
            base = (base.multiply(base)).mod(modulus);
        }
        return result;
    }

    // Convert base 26 equivalent to uppercase text
    public static String base26ToText(BigInteger base26) {
        StringBuilder result = new StringBuilder();
        while (base26.compareTo(BigInteger.ZERO) > 0) {
            BigInteger remainder = base26.mod(BigInteger.valueOf(26));
            char character = (char) ('A' + remainder.intValue());
            result.insert(0, character);
            base26 = base26.divide(BigInteger.valueOf(26));
        }
        return result.toString();
    }

    // Find Alice's private key (Xa)
    public static BigInteger findPrivateKey(BigInteger q, BigInteger g, BigInteger ya) {
        BigInteger xa = BigInteger.ONE;
        while (true) {
            if (modPow(g, xa, q).equals(ya)) {
                return xa;
            }
            xa = xa.add(BigInteger.ONE);
        }
    }

    // Decrypt the ciphertext using ElGamal
    public static String decryptCiphertext(BigInteger q, BigInteger xa, BigInteger[][] ciphertext) {
        StringBuilder decryptedText = new StringBuilder();

        for (BigInteger[] block : ciphertext) {
            BigInteger c1 = block[0];
            BigInteger c2 = block[1];

            // Compute s = c1^xa mod q
            BigInteger s = modPow(c1, xa, q);

            // Compute s_inverse = s^(q-2) mod q
            BigInteger sInverse = modPow(s, q.subtract(BigInteger.valueOf(2)), q);

            // Compute m = c2 * s_inverse mod q
            BigInteger m = c2.multiply(sInverse).mod(q);

            // Convert base 26 equivalent to uppercase text
            decryptedText.append(base26ToText(m));
        }

        return decryptedText.toString();
    }

    public static void main(String[] args) {
        // Public elements
        BigInteger q = new BigInteger("208827064597");
        BigInteger g = new BigInteger("148730885957");
        BigInteger ya = new BigInteger("22100313146");

        // Find the private key for Alice
        BigInteger xa = findPrivateKey(q, g, ya);
        System.out.println("Private key for Alice (xa): " + xa);

        // Ciphertext blocks
        BigInteger[][] ciphertext = {
                {new BigInteger("111537273770"), new BigInteger("135478365325")},
                {new BigInteger("135874735585"), new BigInteger("91980775319")},
                {new BigInteger("114008480727"), new BigInteger("189673465227")},
                {new BigInteger("51859330405"), new BigInteger("168411804286")},
                {new BigInteger("147104771103"), new BigInteger("105548487980")},
                {new BigInteger("1785731042"), new BigInteger("1829176754")},
                {new BigInteger("177464475649"), new BigInteger("64097517903")},
                {new BigInteger("31371229910"), new BigInteger("160373635219")},
                {new BigInteger("155078142943"), new BigInteger("88685005036")},
                {new BigInteger("92163822772"), new BigInteger("83821102277")},
                {new BigInteger("114922855196"), new BigInteger("109868919775")},
                {new BigInteger("139345072195"), new BigInteger("37637914660")},
                {new BigInteger("180092093087"), new BigInteger("72241715608")},
                {new BigInteger("54004792799"), new BigInteger("102543868605")},
                {new BigInteger("180382625017"), new BigInteger("101039780617")},
                {new BigInteger("52988886207"), new BigInteger("106664942086")},
                {new BigInteger("196270659674"), new BigInteger("1848943199")},
                {new BigInteger("35121652348"), new BigInteger("107823603240")},
                {new BigInteger("158294360726"), new BigInteger("126547219134")},
                {new BigInteger("84120560054"), new BigInteger("82845406333")},
                {new BigInteger("106816610946"), new BigInteger("160475344872")},
                {new BigInteger("109119061605"), new BigInteger("199945687440")},
                {new BigInteger("135206784648"), new BigInteger("11466088841")},
                {new BigInteger("104712202700"), new BigInteger("173240884045")},
                {new BigInteger("126757086234"), new BigInteger("179687780957")}
        };

        // Decrypt and print the plaintext
        String decryptedText = decryptCiphertext(q, xa, ciphertext);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}
