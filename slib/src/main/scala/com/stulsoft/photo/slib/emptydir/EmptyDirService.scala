/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.photo.slib.emptydir

import com.stulsoft.photo.slib.utils.SystemUtils

import java.nio.file.{Files, NoSuchFileException, Path, Paths}
import java.util.function.Consumer
import scala.concurrent.{ExecutionContext, Future}
import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Using}

object EmptyDirService {
  def findEmptyDirsAsync(path:String, consumer :Consumer[EmptyDirResult]): Unit = {
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

    val executionContext: ExecutionContext = SystemUtils.executionContext
    Future{
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
    }(executionContext)
      .onComplete{
        case Success(theResult) => consumer.accept(theResult)
        case Failure(exception) =>
          exception.printStackTrace()
          consumer.accept(new EmptyDirResult("", exception.getMessage))
      }(executionContext)
  }
}
