package com.lukafurlan.adventofcode2018;

import com.lukafurlan.adventofcode2018.helpers.FileReadHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luka Furlan <luka.furlan9@gmail.com>
 */

public class Day1 {

    private static List<String> frequencies;

    public static void main(String[] args) {
        frequencies = FileReadHelper.writeLinesToList("Day1Input.txt");
        System.out.println(getFrequency());
        System.out.println(getFirstFrequencyReachedTwice());
    }

    private static int getFrequency() {
        return frequencies.stream().mapToInt(Integer::parseInt).sum();
    }

    private static int getFirstFrequencyReachedTwice() {

        List<Integer> seenFrequencies = new ArrayList<>();

        int[] frequencies = Day1.frequencies.stream().mapToInt(Integer::parseInt).toArray();

        int total = 0;
        int index = 0;

        while(!seenFrequencies.contains(total)) {
            seenFrequencies.add(total);
            total += frequencies[index++ % frequencies.length];
        }

        return total;

    }

}
