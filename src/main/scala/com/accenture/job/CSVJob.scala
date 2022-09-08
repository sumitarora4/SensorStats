package com.accenture.job

import com.github.tototoshi.csv.CSVReader
import com.accenture.models.{DataFormat, Entity}

import java.io.{File, IOException}
import scala.util.Try

trait CSVJob {

  /**
   * Intent of this method is to get CSV files from a directory
   * @param directoryName: directory name
   * @return list of files
   */
  def getInputFiles(directoryName: String): Seq[File] = {
    val inputDirectory = new File(directoryName)
    if (!inputDirectory.isDirectory) throw new IllegalArgumentException("Wrong input directory")
    inputDirectory.listFiles((_, name) => name.endsWith(".csv")).toIndexedSeq
  }

  /**
   *
   * @param file: CSV file
   * @param dataFormat: previous processed files
   * @return: current file processing
   */
  def processCsvFile(file: File, dataFormat: DataFormat = DataFormat()): DataFormat = {
    val input = CSVReader.open(file)

    val fileOutputData = input.toStream.tail.map {
      case id :: "NaN" :: Nil => Entity(id, None)
      case id :: humidity :: Nil => Entity(id, Some(humidity.toInt))
      case _ => throw new IOException(s"Wrong format of file '${file.getName}'")
    }
      .foldLeft(dataFormat)(_ add _)
    fileOutputData.copy(fileCount = fileOutputData.fileCount + 1)
  }
}

/**
 * Object to run CSV job through Akka Stream
 */
object CSVJob {
  def run(str: String): Try[DataFormat] = Try{
    new AkkaStream().run(str)
  }
}