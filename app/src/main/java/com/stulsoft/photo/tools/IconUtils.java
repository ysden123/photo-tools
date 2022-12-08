/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.photo.tools;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class IconUtils {
    public static void setIcon(Stage stage){
        stage
                .getIcons()
                .add(new Image(Objects.requireNonNull(IconUtils.class.getResourceAsStream("/image/icons/icons8-compact-camera-24.png"))));
    }
}
