package com.lukafurlan.adventofcode2018;

import com.lukafurlan.adventofcode2018.helpers.FileReadHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luka Furlan <luka.furlan9@gmail.com>
 */
public class Day2 {

    private static List<String> entries = new ArrayList<>();

    public static void main(String[] args) {
        entries = FileReadHelper.writeLinesToList("Day2Input.txt");
        System.out.println(getChecksum());
    }

    private static int getChecksum() {
        return getNumberOfBoxesWithRepetitiveLetters(2) * getNumberOfBoxesWithRepetitiveLetters(3);
    }

    private static int getNumberOfBoxesWithRepetitiveLetters(int repeats) {
        int words = 0;
        for(String entry : entries) {
            words += countMatches(entry, repeats);
        }
        return words;
    }

    private static int countMatches(String entry, int repeats) {
        for(char c : entry.toCharArray()) {
            if(StringUtils.countMatches(entry, c) == repeats) {
                return 1;
            }
        }
        return 0;
    }

}
