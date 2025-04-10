package com.task.personcompareservice.util;

import com.task.personcompareservice.model.Classification;
import com.task.personcompareservice.model.Person;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TaskProcessor {

    public static Map<String, Classification> comparePeople(final Person previous, final Person current) {
        Map<String, Classification> result = new LinkedHashMap<>();

        if (previous != null && current != null) {
            compareField("name", previous.getName(), current.getName(), result);
            compareField("surname", previous.getSurname(), current.getSurname(), result);
            compareField("birthDate", previous.getBirthDate().toString(), current.getBirthDate().toString(), result);
            compareField("company", previous.getCompany(), current.getCompany(), result);
        } else {
            classifyNullCases(previous, current, result);
        }
        return result;
    }

    private static void compareField(final String field, final String previous, final String current,
                                     final Map<String, Classification> result) {
        double similarity = calculateSimilarity(previous, current);
        var classification = Classification.fromSimilarity(similarity);
        result.put(field, classification);
    }

    private static void classifyNullCases(final Person previous, final Person current, final Map<String, Classification> result) {
        if (previous == null && current != null) {
            createResult(Classification.ADDED, result);
        } else if (previous != null && current == null) {
            createResult(Classification.DELETED, result);
        }
    }

    private static void createResult(final Classification classification, final Map<String, Classification> result) {
        var fields = List.of("name", "surname", "birthDate", "company");
        for (String field : fields) {
            result.put(field, classification);
        }
    }

    public static double calculateSimilarity(String str1, String str2) {
        int longerLength = Math.max(str1.length(), str2.length());

        int differences = calculateDifferences(str1, str2);
        double dissimilarity = (double) differences / longerLength;

        return 1.0 - dissimilarity;
    }

    private static int calculateDifferences(String str1, String str2) {
        if (str1.length() > str2.length()) {
            String temp = str1;
            str1 = str2;
            str2 = temp;
        }

        int differences = str2.length() - str1.length();
        for (int i = 0; i < str1.length(); i++) {
            var character = str1.charAt(i);
            if (!str2.contains(String.valueOf(character))) {
                differences++;
            } else {
                str2 = str2.replaceFirst(String.valueOf(character), "");
            }

        }
        return differences;
    }
}
