package com.lukafurlan.adventofcode2018.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Luka Furlan <luka.furlan9@gmail.com>
 */

public class FileReadHelper {

    public static List<String> writeLinesToList(String fileName) {

        List<String> list = new ArrayList<>();

        try {
            Scanner scan = new Scanner(new File(FileReadHelper.class.getClassLoader().getResource(fileName).getFile()));
            while(scan.hasNextLine()){
                list.add(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list;

    }

}
