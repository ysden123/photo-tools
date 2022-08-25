/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.photo.tools;

import com.stulsoft.photo.tools.gps.ShowOnMap;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello!");

        System.out.println("Enter path to an image:");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        scanner.close();
        new ShowOnMap(path).showOnMap();
    }
}
