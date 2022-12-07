package com.stulsoft.photo.slib.emptydir

import java.nio.file.{Files, NoSuchFileException, Path, Paths}
import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Using}

object EmptyDirService {
  def findEmptyDirs(path: String): EmptyDirResult = {
    def predicateEmptyDir(p: Path): Boolean = {
      Using(Files.list(p)) {
        stream => stream.findAny().isEmpty
      } match {
        case Success(value) => value
        case Failure(exception) =>
          exception.printStackTrace()
          false
      }
    }

    Using(Files.walk(Paths.get(path))) {
      stream => {
        val result = stream.filter(f => Files.isDirectory(f))
          .filter(p => predicateEmptyDir(p))
          .map(p => p.toFile.getAbsolutePath)
          .toList
        List.from(result.asScala)
      }
    } match {
      case Success(emptyDirs) => new EmptyDirResult(emptyDirs.mkString("\n"), "")
      case Failure(exception) => exception match {
        case _: NoSuchFileException =>
          new EmptyDirResult("", s"Cannot find the file $path")
        case exception: Exception =>
          new EmptyDirResult("", exception.getMessage)
      }
    }
  }
}
