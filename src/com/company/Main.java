/**
 * @author Ivanna Moskaliuk KNUTE
 * @version 23.06.2020
 *
 * Classname Main
 *  Final course task
 *          1. GLOSSARY:
 *  1.1. Download a text about Harry Potter.
 *  1.2. For each distinct word in the text calculate the number of occurrence.
 *  1.3. Use RegEx..
 *  1.4. Sort in the DESC mode by the number of occurrence..
 *  1.5. Find  the first 20 pairs.
 *  1.6  Find all the proper names
 *  1.7.  Count them and arrange in alphabetic order.
 *  1.8.   First 20 pairs and names write into to a file test.txt
 *  1.9.  Create a fine header for the file
 *  1.10  Use Java  Collections to demonstrate your experience (Map, List )
 **/
package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    // creating output file
    final static String outputFilePath = "C:\\Users\\ivank\\Desktop\\test.txt";

    public static void main(String[] args) throws IOException {

        // 1.1. Download a text about Harry Potter.
        String harry = new String(Files.readAllBytes(Paths.get("C:\\java\\" +
                "glossary\\harry.txt")));

        String harryCleaned = harry.replaceAll("[^a-zA-Z0-9 ]", "");

        String[] harryWords = harryCleaned.split(" ");

        // 1.2. For each distinct word in the text calculate the number of occurrence

        Map<String, Integer> distinktWordCount = new HashMap<>();
        for (String word : harryWords) {
            if (distinktWordCount.containsKey(word)) {
                // Map already contains the word key. Just increment it's count by 1
                distinktWordCount.put(word, distinktWordCount.get(word) + 1);
            } else {
                // Map doesn't have mapping for word. Add one with count = 1
                distinktWordCount.put(word, 1);
            }
        }
        for (Map.Entry<String, Integer> entry : distinktWordCount.entrySet()) {
            System.out.println("Count of : " + entry.getKey() +
                    " in sentence = " + entry.getValue());
        }
        // 1.4. Sort in the DESC mode by the number of occurrence..

        final Map<String, Integer> sortedByCount = distinktWordCount.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        System.out.println("\n-------- Sorted by the number of occurrence ------------");
        System.out.println(sortedByCount);

        // 1.5. define a path where first 20 pairs will be written

        Path path = Paths.get("C:\\Users\\ivank\\Desktop\\test.txt");

        // create Iterator 'items' to be able to switch to next DescendingSortedMap elements from beginning
        Iterator<Map.Entry<String, Integer>> items = sortedByCount.entrySet().iterator();

        // write first 20 pairs to the file test.txt
        for (int i = 0; i < 20; i++) {
            Map.Entry<String, Integer> pair = items.next(); // get next item
            System.out.format("Word: %s, occurences: %d%n", pair.getKey(), pair.getValue());
            // write occurrence pair to the file
            Files.write(path, (pair.getKey() + "\n").getBytes(), StandardOpenOption.APPEND);
        }

        // 1.6  Find all the proper names

        List<String> properName = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\b[A-Z][a-z]{3,}\\b");
        Matcher matcher = pattern.matcher(harryCleaned);
        while (matcher.find()) {
            String word = matcher.group();
            properName.add(word);
        }

        // 1.7.  Count them and arrange in alphabetic order.

        Collections.sort(properName); // sort properNames in alphabetic order
        int properNamesAmount = properName.size(); // count proper names
        System.out.println("Proper names amount: " + properNamesAmount);

        // 1.8.   First 20 pairs and names write into to a file test.txt
        // writing first 20 names to the file test.txt
        for (int i = 0; i < 20; i++) {

            System.out.println(properName.get(i));
            // write proper name to the file
            Files.write(path, (properName.get(i) + "\n").getBytes(), StandardOpenOption.APPEND);
        }

    }
}
