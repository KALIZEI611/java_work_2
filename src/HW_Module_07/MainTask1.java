package HW_Module_07;

import java.util.List;

public class MainTask1 {
        public static void main(String[] args) {
            List<Integer> numbers = NumberGenerator.generateRandomNumbers(50, -500, 500);

            System.out.println("=== БАЗОВЫЙ АНАЛИЗ ===");
            NumberAnalyzer basicAnalyzer = new NumberAnalyzer(numbers);
            basicAnalyzer.printAnalysis();

            System.out.println("\n=== РАСШИРЕННЫЙ АНАЛИЗ ===");
            AdvancedNumberAnalyzer advancedAnalyzer = new AdvancedNumberAnalyzer(numbers);
            advancedAnalyzer.printAdvancedAnalysis();
            advancedAnalyzer.printMirrorNumbersDetails();

            System.out.println("\n=== ПРИМЕРЫ ЗЕРКАЛЬНЫХ ЧИСЕЛ ===");
            testMirrorNumbers();
        }

        private static void testMirrorNumbers() {
            int[] testNumbers = {121, 1221, 123, 4224, 12321, -121, -1221};

            System.out.println("Проверка известных зеркальных чисел:");
            for (int number : testNumbers) {
                boolean isMirror = MirrorNumberChecker.isMirrorNumber(number);
                System.out.printf("Число %d - зеркальное: %s%n", number, isMirror);
            }
        }
    }

