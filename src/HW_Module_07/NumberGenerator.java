package HW_Module_07;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberGenerator {
    public static List<Integer> generateRandomNumbers(int count, int min, int max) {
        Random random = new Random();
        return IntStream.generate(() -> random.nextInt(max - min + 1) + min)
                .limit(count)
                .boxed()
                .collect(Collectors.toList());
    }
    public static List<Integer> generateRandomNumbersAlt(int count, int min, int max) {
        return new Random()
                .ints(count, min, max + 1)
                .boxed()
                .collect(Collectors.toList());
    }
}