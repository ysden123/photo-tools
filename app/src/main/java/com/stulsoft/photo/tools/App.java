/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.photo.tools;

import com.stulsoft.photo.tools.gps.ShowOnMap;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String path = args.length > 0 ? args[0] : null;

        if (path == null) {
            System.out.println("Enter path to an image:");
            Scanner scanner = new Scanner(System.in);
            path = scanner.nextLine();
            scanner.close();
        }

        ShowOnMap.showOnMap(path);
    }
}
