package HW_Module_07;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class AdvancedNumberAnalyzer extends NumberAnalyzer {

    public AdvancedNumberAnalyzer(List<Integer> numbers) {
        super(numbers);
    }

    public Optional<Integer> findMaxMirrorNumber() {
        return getMirrorNumbers().stream()
                .max(Comparator.naturalOrder());
    }

    public Optional<Integer> findMinMirrorNumber() {
        return getMirrorNumbers().stream()
                .min(Comparator.naturalOrder());
    }
    public OptionalDouble calculateAverageOfPositive() {
        return getNumbers().stream()
                .filter(n -> n > 0)
                .mapToInt(Integer::intValue)
                .average();
    }

    public void printAdvancedAnalysis() {
        System.out.println("\nРАСШИРЕННЫЙ АНАЛИЗ:");

        findMaxMirrorNumber().ifPresent(max ->
                System.out.println("Максимальное зеркальное число: " + max));

        findMinMirrorNumber().ifPresent(min ->
                System.out.println("Минимальное зеркальное число: " + min));

        calculateAverageOfPositive().ifPresent(avg ->
                System.out.printf("Среднее значение положительных чисел: %.2f%n", avg));

        List<Integer> twoDigitNumbers = getTwoDigitNumbers();
        if (!twoDigitNumbers.isEmpty()) {
            System.out.println("Двухзначные числа: " + twoDigitNumbers);
            System.out.println("Количество уникальных двухзначных чисел: " +
                    twoDigitNumbers.stream().distinct().count());
        }
    }
}
