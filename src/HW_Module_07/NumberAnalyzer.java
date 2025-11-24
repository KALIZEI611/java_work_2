package HW_Module_07;

import java.util.List;
import java.util.stream.Collectors;

public class NumberAnalyzer {
    private final List<Integer> numbers;

    public NumberAnalyzer(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public long countPositive() {
        return numbers.stream()
                .filter(n -> n > 0)
                .count();
    }

    public long countNegative() {
        return numbers.stream()
                .filter(n -> n < 0)
                .count();
    }

    public long countTwoDigit() {
        return numbers.stream()
                .filter(n -> (n >= 10 && n <= 99) || (n <= -10 && n >= -99))
                .count();
    }

    public long countMirrorNumbers() {
        return numbers.stream()
                .filter(MirrorNumberChecker::isMirrorNumber)
                .count();
    }

    public List<Integer> getMirrorNumbers() {
        return numbers.stream()
                .filter(MirrorNumberChecker::isMirrorNumber)
                .collect(Collectors.toList());
    }

    public List<Integer> getPositiveNumbers() {
        return numbers.stream()
                .filter(n -> n > 0)
                .collect(Collectors.toList());
    }

    public List<Integer> getNegativeNumbers() {
        return numbers.stream()
                .filter(n -> n < 0)
                .collect(Collectors.toList());
    }

    public List<Integer> getTwoDigitNumbers() {
        return numbers.stream()
                .filter(n -> (n >= 10 && n <= 99) || (n <= -10 && n >= -99))
                .collect(Collectors.toList());
    }

    public void printAnalysis() {
        System.out.println("РЕЗУЛЬТАТЫ АНАЛИЗА:");
        System.out.println("Количество положительных чисел: " + countPositive());
        System.out.println("Количество отрицательных чисел: " + countNegative());
        System.out.println("Количество двухзначных чисел: " + countTwoDigit());
        System.out.println("Количество зеркальных чисел: " + countMirrorNumbers());
    }

    public void printMirrorNumbersDetails() {
        List<Integer> mirrorNumbers = getMirrorNumbers();
        System.out.println("\nДЕТАЛЬНАЯ ИНФОРМАЦИЯ О ЗЕРКАЛЬНЫХ ЧИСЛАХ:");
        System.out.println("Зеркальные числа: " + mirrorNumbers);

        if (!mirrorNumbers.isEmpty()) {
            System.out.println("Проверка зеркальности:");
            mirrorNumbers.forEach(number ->
                    System.out.printf("Число %d -> зеркальное: %s%n",
                            number, MirrorNumberChecker.isMirrorNumber(number))
            );
        }
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}