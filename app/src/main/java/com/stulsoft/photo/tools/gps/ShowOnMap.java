/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.photo.tools.gps;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.Optional;

/**
 * @author Yuriy Stul
 */
public class ShowOnMap {
    public static void showOnMap(String path) {
        try {
            Optional<TiffImageMetadata.GPSInfo> gpsInfo = extractGPSInfo(path);
            if (gpsInfo.isEmpty()) {
                System.out.println("No GPS in the image");
                return;
            }

            String searchURL = String.format("https://www.google.com/maps/search/?api=1&query=%f%%2C%f",
                    gpsInfo.get().getLatitudeAsDegreesNorth(), gpsInfo.get().getLongitudeAsDegreesEast());
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(searchURL));
            } else {
                System.out.println("Cannot start browser");
                System.out.println(searchURL);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static Optional<TiffImageMetadata.GPSInfo> extractGPSInfo(String path) {
        try {
            ImageMetadata metadata = Imaging.getMetadata(new File(path));
            if (metadata instanceof JpegImageMetadata jpegImageMetadata) {
                return Optional.ofNullable(jpegImageMetadata.getExif().getGPS());
            } else if (metadata instanceof TiffImageMetadata tiffImageMetadata) {
                return Optional.ofNullable(tiffImageMetadata.getGPS());
            }
            return Optional.empty();
        } catch (Exception exception) {
            exception.printStackTrace();
            return Optional.empty();
        }
    }
}
