package HW_Module_08;

public class StringValidator {

    public boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    public boolean isOnlyLetters(String input) {
        if (input == null) {
            return false;
        }

        return input.matches("[а-яА-Яa-zA-Z]+");
    }

    public boolean isOnlyDigits(String input) {
        if (input == null) {
            return false;
        }

        return input.matches("\\d+");
    }

    public boolean isAlphaNumeric(String input) {
        if (input == null) {
            return false;
        }

        return input.matches("[а-яА-Яa-zA-Z0-9]+");
    }

    public int getLength(String input) {
        if (input == null) {
            return 0;
        }

        return input.length();
    }

    public String trimSpaces(String input) {
        if (input == null) {
            return "";
        }

        return input.trim();
    }
}
