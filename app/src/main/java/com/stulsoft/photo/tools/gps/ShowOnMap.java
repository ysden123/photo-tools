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
    record ExtractResult(Optional<TiffImageMetadata.GPSInfo> gpsInfo, Optional<String> error) {
    }

    public static Optional<String> showOnMap(String path) {
        try {
            var extractResult = extractGPSInfo(path);
            if (extractResult.gpsInfo().isPresent()) {
                var gpsInfo = extractResult.gpsInfo().get();
                String searchURL = String.format("https://www.google.com/maps/search/?api=1&query=%f%%2C%f",
                        gpsInfo.getLatitudeAsDegreesNorth(), gpsInfo.getLongitudeAsDegreesEast());
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(searchURL));
                    return Optional.empty();
                } else {
                    return Optional.of("Cannot start browser");
                }
            }

            if (extractResult.error().isPresent()) {
                return extractResult.error();
            }

            return Optional.empty();

        } catch (Exception exception) {
            return Optional.of(exception.getMessage());
        }
    }

    private static ExtractResult extractGPSInfo(String path) {
        try {
            ImageMetadata metadata = Imaging.getMetadata(new File(path));
            TiffImageMetadata.GPSInfo gpsInfo = null;
            if (metadata instanceof JpegImageMetadata jpegImageMetadata) {
                gpsInfo = jpegImageMetadata.getExif().getGPS();
            } else if (metadata instanceof TiffImageMetadata tiffImageMetadata) {
                gpsInfo = tiffImageMetadata.getGPS();
            }
            if (gpsInfo == null) {
                return new ExtractResult(Optional.empty(), Optional.of("No GPS in the image"));
            } else {
                return new ExtractResult(Optional.of(gpsInfo), Optional.empty());
            }
        } catch (Exception exception) {
            return new ExtractResult(Optional.empty(), Optional.of(exception.getMessage()));
        }
    }
}
