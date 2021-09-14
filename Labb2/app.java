import java.util.*;
import java.math.BigInteger;
import java.security.SecureRandom;

class UserInputDemo1 {
    public static void main(String[] args) {
        System.out.print("Enter a string to encrypt: ");
        String mySecret = ReadInput();
        System.out.println("You have entered: " + mySecret);
        BigInteger bigB = ConvertString(mySecret);
        int p = 88532096;
        int q = 23885541;
        int n = p * q;
        BigInteger e = new BigInteger("9007"); // random prime
        // BigInteger e = bigPrime();
        
        // for (int i = 9009; 2 < i; i--) {
        //     int gcd = GCD(i, (p - 1) * (q - 1));
        //     if (gcd == 1) {
        //         System.out.println("e = " + i);
        //         e = i;
        //         break;
        //     }
        // }

        // BigInteger d = new BigInteger("116402471153538991"); // d should be this

        // Choose e such gcd(e , ϕ(n) ) = 1 


        // int ans = d.multiply(e);
        BigInteger bigP = new BigInteger("88532096");
        BigInteger bigQ = new BigInteger("23885541");
        BigInteger bigE = new BigInteger(String.valueOf(e));
        // probable prime!

        BigInteger t = (bigP.subtract(BigInteger.ONE)).multiply(bigQ.subtract(BigInteger.ONE));
        BigInteger d = bigE.modInverse(t);

        System.out.println("ans = " + d);

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

    public static BigInteger bigPrime() {
        SecureRandom rand = new SecureRandom();
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
