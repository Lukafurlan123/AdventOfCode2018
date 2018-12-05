package com.lukafurlan.adventofcode2018;

import com.lukafurlan.adventofcode2018.helpers.FileReadHelper;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Luka Furlan <luka.furlan9@gmail.com>
 */
public class Day4 {

    private static List<String> entries = new ArrayList<>();
    private static HashMap<Integer, Guard> guards = new HashMap<>();

    public static void main(String[] args) {

        entries = FileReadHelper.writeLinesToList("Day4Input.txt");
        Collections.sort(entries);
        processRecords();
        getSleepiestGuard();

    }


    private static void processRecords() {

        int guardId = 0;

        for(String entry : entries) {

            Pattern dateTimePattern = Pattern.compile("^\\[([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})] (wakes up|falls asleep|Guard #([0-9]+) begins shift)$");
            Matcher matcher = dateTimePattern.matcher(entry);

            if(!matcher.matches()) {
                continue;
            }

            int year = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));
            int day = Integer.parseInt(matcher.group(3));
            int hour = Integer.parseInt(matcher.group(4));
            int minute = Integer.parseInt(matcher.group(5));

            long dateTime = Long.parseLong("" + year + "" + month + "" + day + "" + hour + "" + minute);

            switch(matcher.group(6).split(" ")[0]) {
                case "Guard":
                    guardId = Integer.parseInt(matcher.group(7));
                    break;
                case "falls":
                    processGuard(guardId, dateTime, minute, false);
                    break;
                case "wakes":
                    processGuard(guardId, dateTime, minute, true);
                    break;
            }

        }

    }

    private static void processGuard(int guardId, long datetime, int time, boolean wakeUp) {

        Guard guard = guards.get(guardId);

        if(!Objects.isNull(guard)) {
            guard.addGuardAction(datetime, time, wakeUp);
        } else {
            guards.put(guardId, new Guard(guardId, datetime, time, wakeUp));
        }

    }

    private static void getSleepiestGuard() {

        int maxSleepTime = 0;
        int sleepiestGuard = 0;
        int sleepiestMinuteIndex = 0;

        int[] frequentMinuteGuardId = new int[60];
        int[] frequentMinuteTimes = new int[60];

        int maxMinute = 0;
        int maxMinuteIndex = 0;

        for(Guard guard : guards.values()) {

            int sleepTime = -1;
            int sleepiestMinute = 0;
            int[] minutes = new int[60];

            for(GuardAction action : guard.getSleepTimes()) {

                if(action.isWakeUp()) {
                    guard.incrementTotalSleepTime(action.getTime() - sleepTime);
                    for(int i = sleepTime; i < action.getTime(); i++) {
                        minutes[i]++;
                    }
                } else {
                    sleepTime = action.getTime();
                }

            }

            int totalSleepTime = guard.getTotalSleepTime();

            if(totalSleepTime > maxSleepTime) {
                maxSleepTime = totalSleepTime;
                sleepiestGuard = guard.getId();

                for(int i = 0; i < minutes.length; i++) {
                    if(minutes[i] > sleepiestMinute) {
                        sleepiestMinute = minutes[i];
                        sleepiestMinuteIndex = i;
                    }
                }
            }

            for(int i = 0; i < minutes.length; i++) {

                if(minutes[i] > frequentMinuteTimes[i]) {

                    frequentMinuteGuardId[i] = guard.getId();
                    frequentMinuteTimes[i] = minutes[i];

                    if(frequentMinuteTimes[i] > maxMinute) {
                        maxMinute = frequentMinuteTimes[i];
                        maxMinuteIndex = i;
                    }
                    
                }

            }

        }

        System.out.println("Strategy 1: " + sleepiestGuard * sleepiestMinuteIndex);
        System.out.println("Strategy 2: " + frequentMinuteGuardId[maxMinuteIndex] * maxMinuteIndex);

    }

    static class Guard {

        private int id;
        private List<GuardAction> sleepTimes = new ArrayList<>();
        private int totalSleepTime = 0;

        Guard(int id, long datetime, int time, boolean wakeup) {
            this.id = id;
            this.addGuardAction(datetime, time, wakeup);
        }

        int getId() {
            return id;
        }

        List<GuardAction> getSleepTimes() {
            return sleepTimes;
        }

        void addGuardAction(long datetime, int time, boolean wakeUp) {
            this.sleepTimes.add(new GuardAction(wakeUp, time));
        }

        void incrementTotalSleepTime(int minutes) {
            this.totalSleepTime += minutes;
        }

        int getTotalSleepTime() {
            return totalSleepTime;
        }
    }

    static class GuardAction {

        private boolean wakeUp;
        private int time;

        GuardAction(boolean wakeUp, int time) {
            this.wakeUp = wakeUp;
            this.time = time;
        }

        boolean isWakeUp() {
            return wakeUp;
        }

        int getTime() {
            return time;
        }
    }

}
