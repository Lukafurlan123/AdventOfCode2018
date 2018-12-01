package com.lukafurlan.adventofcode2018;

import com.lukafurlan.adventofcode2018.helpers.FileReadHelper;

import java.util.List;

/**
 * @author Luka Furlan <luka.furlan9@gmail.com>
 */

public class Day1 {

    private static List<String> frequencies;

    public static void main(String[] args) {
        frequencies = FileReadHelper.writeLinesToList("Day1Input.txt");
        System.out.println(getFrequency());
    }

    private static int getFrequency() {

        int total = 0;

        for(String frequency : frequencies) {
            int freq = Integer.parseInt(frequency);
            total += freq;
        }

        return total;

    }

}
