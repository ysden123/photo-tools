/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.photo.slib

import org.apache.commons.imaging.Imaging
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata

import java.awt.Desktop
import java.io.File
import java.net.URI

object GpsService {
  def showOnMap(path: String): String = {
    try {
      extractGPSInfo(path) match {
        case ExtractResult(Some(gpsInfo), None) => {
          var searchURL = String.format("https://www.google.com/maps/search/?api=1&query=%f%%2C%f",
            gpsInfo.getLatitudeAsDegreesNorth, gpsInfo.getLongitudeAsDegreesEast)
          if (Desktop.isDesktopSupported && Desktop.getDesktop.isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop.browse(new URI(searchURL))
            ""
          } else {
            "Cannot start browser"
          }
        }
        case ExtractResult(None, Some(error)) => error
      }

    } catch {
      case exception: Exception => exception.getMessage
    }
  }

  private def extractGPSInfo(path: String): ExtractResult = {
    try {
      var gpsInfo: TiffImageMetadata.GPSInfo = null
      Imaging.getMetadata(new File(path)) match {
        case jpegImageMetadata: JpegImageMetadata => ExtractResult(Option(jpegImageMetadata.getExif().getGPS()), None)
        case tiffImageMetadata: TiffImageMetadata => ExtractResult(Option(tiffImageMetadata.getGPS), None)
        case null => ExtractResult(Option.empty, Option("No GPS in the image"))
        case x => ExtractResult(Option.empty, Option(s"Unsupported type ${x.getClass.getName}"))
      }
    } catch {
      case exception: Exception => ExtractResult(Option.empty, Option(exception.getMessage))
    }
  }
}

case class ExtractResult(gpsInfo: Option[TiffImageMetadata.GPSInfo], error: Option[String])
