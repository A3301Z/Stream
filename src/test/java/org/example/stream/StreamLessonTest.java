package org.example.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class StreamLessonTest {

    private static final List<Integer> integerList = Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13);

    private static final List<Employee> employeeList = Arrays.asList(
            Employee.builder().name("khalid Kashmiri").age(31).jobTitle(Employee.JobTitle.ИНЖЕНЕР).build(),
            Employee.builder().name("Khidir Karawita").age(29).jobTitle(Employee.JobTitle.ЧАБАН).build(),
            Employee.builder().name("Ismail Ahmad Kanabawi").age(41).jobTitle(Employee.JobTitle.ИНЖЕНЕР).build(),
            Employee.builder().name("Muhammad Sumbul").age(98).jobTitle(Employee.JobTitle.ИНЖЕНЕР).build(),
            Employee.builder().name("Yagub Kamaradin Jibiazah").age(13).jobTitle(Employee.JobTitle.ИНЖЕНЕР).build(),
            Employee.builder().name("Usman Abdul Jalil Sisha").age(64).jobTitle(Employee.JobTitle.ЧАБАН).build(),
            Employee.builder().name("Джейсон Стетхем").age(57).jobTitle(Employee.JobTitle.ФОТОГРАФ_ФОТОГРАФИЙ_И_ФОТООБОРУДОВАНИЯ).build(),
            Employee.builder().name("Александр Друзь").age(69).jobTitle(Employee.JobTitle.ФОТОГРАФ_ФОТОГРАФИЙ_И_ФОТООБОРУДОВАНИЯ).build(),
            Employee.builder().name("Антон").age(28).jobTitle(Employee.JobTitle.ИНЖЕНЕР).build()
    );

    private static final String input = "один два три семь двадцатьчетыретысячисемьсотвосемьдесятдва один три два четыре";
    private static final List<String> words = Arrays.asList("кошка", "собака", "обезьяна", "человек", "ванильныйрафнаальтернативноммолоке", "пенсионерка", "деревня", "свитер", "авиация");

    private static final String[] arrayOfSetOfWords = {
            "воробей деревня вишня слива никотин",
            "бамбук виноград дыня киви валерьянка",
            "кроссовок монитор колонки микрофон ручка",
            "нож доброта угли аннигиляторная_пушка пупок",
            "самолёт красота шторы баклажан стирка"
    };

    @Test
    public void testDeleteDuplicates() {
        List<Integer> expected = Arrays.asList(5, 2, 10, 9, 4, 3, 1, 13);
        List<Integer> result = integerList.stream().distinct().collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    public void testThirdLargest() {
        Integer thirdLargest = integerList.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElse(null);
        assertEquals(10, thirdLargest);
    }

    @Test
    public void testThirdUniqueLargest() {
        Integer thirdUniqueLargest = integerList.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElse(null);
        assertEquals(9, thirdUniqueLargest);
    }

    @Test
    public void testListNamesDescending() {
        List<String> expected = Arrays.asList("Muhammad Sumbul", "Ismail Ahmad Kanabawi", "khalid Kashmiri");
        List<String> result = employeeList.stream()
                .filter(employee -> employee.getJobTitle() == Employee.JobTitle.ИНЖЕНЕР)
                .sorted(Comparator.comparingInt(Employee::getAge).reversed())
                .limit(3)
                .map(Employee::getName)
                .collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    public void testAverageAgeOfEngineers() {
        double expected = (31 + 41 + 98 + 13 + 28) / 5.0;
        Double result = employeeList.stream()
                .filter(employee -> employee.getJobTitle() == Employee.JobTitle.ИНЖЕНЕР)
                .collect(Collectors.averagingDouble(Employee::getAge));
        assertEquals(expected, result, 0.01);
    }

    @Test
    public void testLongestWord() {
        String longestWord = employeeList.stream()
                .map(Employee::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
        assertEquals("Yagub Kamaradin Jibiazah", longestWord);
    }

    @Test
    public void testWordsCountInLine() {
        Map<String, Long> expected = Map.of(
                "один", 2L,
                "два", 2L,
                "три", 2L,
                "семь", 1L,
                "двадцатьчетыретысячисемьсотвосемьдесятдва", 1L,
                "четыре", 1L
        );
        Map<String, Long> result = Arrays.stream(input.split(" "))
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
        assertEquals(expected, result);
    }

    @Test
    public void testSortByWordLength() {
        List<String> expected = Arrays.asList("кошка", "свитер", "собака", "авиация", "деревня", "человек", "обезьяна", "пенсионерка", "ванильныйрафнаальтернативноммолоке");
        List<String> result = words.stream()
                .sorted(Comparator.comparingInt(String::length).thenComparing(String::compareTo))
                .collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    public void testLongestWordFromArray() {
        String expected = "аннигиляторная_пушка";
        String result = Arrays.stream(arrayOfSetOfWords)
                .flatMap(phrase -> Arrays.stream(phrase.split(" ")))
                .reduce((word1, word2) -> word1.length() >= word2.length() ? word1 : word2)
                .orElse("Нет слов");
        assertEquals(expected, result);
    }
}
