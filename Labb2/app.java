import java.util.*;
import java.math.BigInteger;
import java.security.SecureRandom;

class UserInputDemo1 {
    public static void main(String[] args) {

        SecureRandom rand = new SecureRandom();
        // Generate big prime numbers
        // p, q, d secret
        BigInteger e = bigPrime(rand);  // Public key
        BigInteger p = bigPrime(rand);
        BigInteger q = bigPrime(rand);


        // Make sure p > q
        if (p.compareTo(q) < 0) {
            BigInteger temp = p;
            p = q;
            q = temp;
        }
        
        // n = p * q
        BigInteger n = p.multiply(q); // Public key

        // de = (p - 1) (q - 1)
        BigInteger de = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        // d = 1 / mod(de)
        BigInteger d = e.modInverse(de);  // Private key
        // System.out.println(" = " + d);

        
        // e, n public
        System.out.print("Enter a string to encrypt: ");
        String mySecret = ReadInput();
        System.out.println("You have entered: " + mySecret);
        // Encrypting
        // Convert string to BigInt
        BigInteger m = ConvertString(mySecret);
        BigInteger bigC = m.modPow(e, n);

        // BOB
        BigInteger originalMsg = bigC.modPow(d, n);
        System.out.println("Decrypted message: " + ConvertBack(originalMsg));
    }

    public static BigInteger ConvertString(String arg) {
        return new BigInteger(arg.getBytes());
    }

    public static String ConvertBack(BigInteger arg) {
        return new String(arg.toByteArray());
    }

    public static String ReadInput() {
        Scanner sc = new Scanner(System.in); // System.in is a standard input stream
        String str = sc.nextLine(); // reads string
        return str;
    }

    public static BigInteger bigPrime(SecureRandom rand) {
        return BigInteger.probablePrime(1024, rand);
    }

    public static int GCD(int num1, int num2) {

        // Lets take two numbers 55 and 121 and find their GCD
        int gcd = 1;

        /*
         * loop is running from 1 to the smallest of both numbers In this example the
         * loop will run from 1 to 55 because 55 is the smaller number. All the numbers
         * from 1 to 55 will be checked. A number that perfectly divides both numbers
         * would be stored in variable "gcd". By doing this, at the end, the variable
         * gcd will have the largest number that divides both numbers without remainder.
         */
        for (int i = 1; i <= num1 && i <= num2; i++) {
            if (num1 % i == 0 && num2 % i == 0)
                gcd = i;
        }

        return gcd;
    }

    public static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
