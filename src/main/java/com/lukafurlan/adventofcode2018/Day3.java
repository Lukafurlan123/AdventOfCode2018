package com.lukafurlan.adventofcode2018;

import com.lukafurlan.adventofcode2018.helpers.FileReadHelper;

import java.util.*;

/**
 * @author Luka Furlan <luka.furlan9@gmail.com>
 */
public class Day3 {

    private static List<String> entries = new ArrayList<>();
    private static Set<Claim> claims = new HashSet<>();

    public static void main(String[] args) {
        entries = FileReadHelper.writeLinesToList("Day3Input.txt");
        System.out.println(processClaims());
        System.out.println(getCleanClaim());
    }

    private static void readClaims() {
        for(String entry : entries) {
            int[] data = Arrays.stream(entry.replaceAll("[^-?0-9]+", " ")
                    .trim().split(" ")).mapToInt(Integer::parseInt).toArray();

            claims.add(new Claim(data[0], data[1], data[2], data[3], data[4]));
        }
    }

    private static int processClaims() {
        readClaims();

        int[][] grid = new int[1000][1000];
        int overlapSize = 0;

        for(Claim claim : claims) {
            for(int x = 0; x < claim.getWidth(); x++) {
                for(int y = 0; y < claim.getHeight(); y++) {
                    if(grid[claim.getLeftEdge() + x][claim.getTopEdge() + y] == 1) {
                        overlapSize++;
                    }
                    grid[claim.getLeftEdge() + x][claim.getTopEdge() + y] += 1;
                }
            }
        }

        return overlapSize;
    }

    private static int getCleanClaim() {

        for(Claim first : claims) {

            boolean overlap = false;

            for(Claim second : claims) {

                if(first.getId() == second.getId()) {
                    continue;
                }

                if(first.getLeftEdge() + first.getWidth() > second.getLeftEdge() && first.getLeftEdge() < second.getLeftEdge() + second.getWidth()
                    && first.getTopEdge() + first.getHeight() > second.getTopEdge() && first.getTopEdge() < second.getTopEdge() + second.getHeight()) {
                    overlap = true;
                }

            }

            if(!overlap) {
                return first.getId();
            }

        }

        return 0;

    }

    static class Claim {

        private int id;
        private int leftEdge;
        private int topEdge;
        private int width;
        private int height;

        Claim(int id, int leftEdge, int topEdge, int width, int height) {
            this.id = id;
            this.leftEdge = leftEdge;
            this.topEdge = topEdge;
            this.width = width;
            this.height = height;
        }

        public int getId() {
            return id;
        }

        public int getLeftEdge() {
            return leftEdge;
        }

        public int getTopEdge() {
            return topEdge;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

}
