package com.accenture

import com.accenture.job.CSVJob
import scala.util.{Failure, Success}

/**
 * Main Class for CSVJob Run
 * Passing command line arguments
 */
object SensorStatistics {

  def main(args: Array[String]): Unit = {
    val result = CSVJob.run(args.headOption.getOrElse("."))

    /* Catching result and exception*/
    result match {
      case Failure(e) => println(e.getMessage)
      case Success(value) => println(value)
    }
    }
  }


