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
    private final String path;

    public ShowOnMap(String path) {
        this.path = path;
    }

    public void showOnMap() {
        try {
            Optional<TiffImageMetadata.GPSInfo> gpsInfo = extractGPSInfo();
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

    private Optional<TiffImageMetadata.GPSInfo> extractGPSInfo() {
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
