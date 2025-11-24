package HW_Module_07;

public class MirrorNumberChecker {

    public static boolean isMirrorNumber(int number) {
        int absoluteValue = Math.abs(number);
        String numberStr = String.valueOf(absoluteValue);
        String reversedStr = new StringBuilder(numberStr).reverse().toString();

        return numberStr.equals(reversedStr);
    }

    public static boolean isPalindrome(int number) {
        return isMirrorNumber(number);
    }

    public static int getMirrorNumber(int number) {
        String numberStr = String.valueOf(Math.abs(number));
        String reversedStr = new StringBuilder(numberStr).reverse().toString();
        return Integer.parseInt(reversedStr);
    }
}
