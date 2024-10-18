package org.example.stream;

import java.util.*;
import java.util.stream.Collectors;

public class StreamLesson {

    public static void main(String[] args) {

    }
    
    // 1) Удаление дубликатов
    public static void deleteDuplicates(List<Integer> integerList) {
        System.out.println(); // Просто от логов отлепить, чтобы глаза не ломать
        System.out.print("1) Лист без дубликатов: ");
        integerList.stream()
                .distinct()
                .forEach(integer -> System.out.print(integer + " "));
        System.out.println("\n");
    }

    // 2) Третье самое большое число
    public static void thirdLargest(List<Integer> integerList) {
        System.out.print("2) Третье наибольшее число: ");
        Integer thirdLargest = integerList.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElse(null);
        System.out.println(thirdLargest + "\n");
    }

    // 3) Третье самое большое уникальное число
    public static void thirdUniqueLargest(List<Integer> integerList) {
        System.out.print("3) Третье наибольшее уникальное число: ");
        Integer thirdUniqueLargest = integerList.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElse(null);
        System.out.println(thirdUniqueLargest + "\n");
    }

    // 4) Список имен 3-х самых старших инженеров по убыванию
    public static void listNamesDescending(List<Employee> employeeList) {
        System.out.println("4) Список имен трёх самых старших инженеров по убыванию: {");
        employeeList.stream()
                .filter(employee -> employee.getJobTitle() == Employee.JobTitle.ИНЖЕНЕР)
                .sorted(Comparator.comparingInt(Employee::getAge).reversed())
                .limit(3)
                .map(Employee::getName)
                .forEach(name -> System.out.println("      " + name));
        System.out.println("}\n");
    }

    // 5) Средний возраст инженеров
    public static void averageAgeOfEngineers(List<Employee> employeeList) {
        Double averageAge = employeeList.stream()
                .filter(employee -> employee.getJobTitle() == Employee.JobTitle.ИНЖЕНЕР)
                .collect(Collectors.averagingDouble(Employee::getAge));
        System.out.println("5) Средний возраст всех инженеров: " + averageAge + "\n");
    }

    //6) Поиск самого длинного слова в списке (например имена сотрудников)
    public static void longestWord(List<Employee> employeeList) {
        String longestWord = employeeList.stream()
                .map(Employee::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
        System.out.println("6) Самое длинное имя в списке: " + longestWord + "\n");
    }

    //7) Количество слов во входящей строке (Если нужно - переделаю на Map<String, Long> wordCount = ...)
    public static void wordsCountInLine(String input) {
        System.out.println("7) Количество всех слов во входящей строке: {");
        Arrays.stream(input.split(" "))
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()))
                .forEach((word, count) -> System.out.println("      " + word + ": " + count));
        System.out.println("}\n");
    }

    //8) Сортировка в порядке увеличения длины слова (если одинаковая длина - то по алфавиту)
    public static void sortByWordLength(List<String> words) {
        System.out.println("8) Слова отсортированые по длине + по алфавиту: {");
        words.stream()
                .sorted((word1, word2) -> {
                    int lengthCompare = Integer.compare(word1.length(), word2.length());
                    return (lengthCompare != 0) ? lengthCompare : word1.compareTo(word2);
                })
                .forEach(word -> System.out.println("      " + word));
        System.out.println("}\n");
    }

    //9) Самое длинное слово из пяти строк массива
    public static void longestWordFromArray(String[] arrayOfSetOfWords) {
        System.out.print("9) Самое длинное слово из всех строк массива: ");
        String longestWordInArray = Arrays.stream(arrayOfSetOfWords)
                .flatMap(phrase -> Arrays.stream(phrase.split(" ")))
                .reduce((word1, word2) -> word1.length() >= word2.length() ? word1 : word2)
                .orElse("Нет слов");
        System.out.println(longestWordInArray);
    }
}
