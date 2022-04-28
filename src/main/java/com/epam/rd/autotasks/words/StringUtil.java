package com.epam.rd.autotasks.words;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static int countEqualIgnoreCaseAndSpaces(String[] words, String sample) {
        //throw new UnsupportedOperationException();
        int count = 0;
        if (sample == null || sample.isEmpty() || words == null || words.length == 0) {
            return 0;
        }
        sample = sample.trim();
        Pattern pattern = Pattern.compile(sample + "\\b", Pattern.CASE_INSENSITIVE);
        for (String word : words) {
            Matcher matcher = pattern.matcher(word);
            if (matcher.find()) {
                count++;
            }
        }
        return count;
    }

    public static String[] splitWords(String text) {
        //throw new UnsupportedOperationException();
        if (text == null || text.length() == 0) {
            return null;
        }
        text = text.replaceAll("\\p{Punct}+", " ").trim();
        String[] splitStrings = text.split("\\s+");
        System.out.println(splitStrings.length);
        if (splitStrings[0].isEmpty()) {
            return null;
        }
        return splitStrings;
    }

    public static String convertPath(String path, boolean toWin) {
        //throw new UnsupportedOperationException();
        if (path == null || path.isEmpty() || !(checkWinPath(path) || checkUnixPath(path))) {
            return null;
        }
        if ((checkWinPath(path) && toWin) || (checkUnixPath(path) && !toWin)) {
            return path;
        }
        String resultPath;
        if (toWin) {
            resultPath = convertToWin(path);
        } else {
            resultPath = convertToUnix(path);
        }

        return resultPath;
    }

    private static boolean checkWinPath (String path) {
        return path.matches("(C:\\\\)?([\\w\\.\\-\\s\\\\]*)");
    }

    private static boolean checkUnixPath (String path) {
        return path.matches("(/|~)?([\\w\\.\\-\\s/]*)");
    }

    private static String convertToWin (String path) {
//        String resultPath = path;
        if (path.startsWith("~")) {
            path = path.replace("~", "C:\\User");
        } else if (path.startsWith("/")) {
            path = path.replaceFirst("/", "C:\\\\");
        }
        return  path.replace("/", "\\");
    }

    private static String convertToUnix (String path) {
        if (path.startsWith("C:\\User")) {
            path = path.replace("C:\\User", "~");
        } else if (path.startsWith("C:\\")) {
            path = path.replace("C:\\", "/");
        }
        return path.replace("\\", "/");
    }

    public static String joinWords(String[] words) {
        //throw new UnsupportedOperationException();
        if (words == null || words.length == 0) {
            return null;
        }
        StringJoiner result = new StringJoiner(", ", "[", "]");
        for (String i : words) {
            if (!i.isEmpty()) {
                result.add(i);
            }
        }
//        System.out.println(result.length());
        if (result.length() == 2) {
            return null;
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println("Test 1: countEqualIgnoreCaseAndSpaces");
        String[] words = new String[]{" WordS    \t", "words", "w0rds", "WOR  DS", };
        String sample = "words   ";
        int countResult = countEqualIgnoreCaseAndSpaces(words, sample);
        System.out.println("Result: " + countResult);
        int expectedCount = 2;
        System.out.println("Must be: " + expectedCount);

        System.out.println("Test 2: splitWords");
        String text = "   ,, first, second!!!! third";
//        String text =",,,    ...:::";
        String[] splitResult = splitWords(text);
        System.out.println("Result : " + Arrays.toString(splitResult));
        String[] expectedSplit = new String[]{"first", "second", "third"};
        System.out.println("Must be: " + Arrays.toString(expectedSplit));

        System.out.println("Test 3: convertPath");
        String unixPath = "/some/unix/path";
        String convertResult = convertPath(unixPath, true);
        System.out.println("Result: " + convertResult);
        String expectedWinPath = "C:\\some\\unix\\path";
        System.out.println("Must be: " + expectedWinPath);

        //my strings:
        String winPath = "C:\\root\\logs\\end.log";
        convertResult = convertPath(winPath, false);
        System.out.println("Result: " + convertResult);
        System.out.println("Must be: /root/logs/end.log");
        winPath = "file.txt";
        convertResult = convertPath(winPath, false);
        System.out.println("Result: " + convertResult);
        System.out.println("Must be: file.txt");


        System.out.println("Test 4: joinWords");
        String[] toJoin = new String[]{"go", "with", "the", "", "FLOW"};
//        String[] toJoin = new String[]{"", "with", "the", "", ""};
        String joinResult = joinWords(toJoin);
        System.out.println("Result: " + joinResult);
        String expectedJoin = "[go, with, the, FLOW]";
        System.out.println("Must be: " + expectedJoin);
    }
}