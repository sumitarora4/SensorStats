package com.accenture.models

import cats.implicits._
import com.accenture.implicits._

import scala.collection.immutable.HashMap
import scala.util.Properties

/**
 * Intent of this case class to store data for measurements result
 * @param sensors
 * @param failedCount
 * @param totalCount
 * @param fileCount
 */
case class DataFormat(
                       sensors: Map[String, Measurements] = HashMap().withDefaultValue(MeasurementsFailed),
                       failedCount: Int = 0,
                       totalCount: Int = 0,
                       fileCount: Int = 0
                     ) {

  /* add measurements */
  /**
   *
   * @param v1 data to be add
   * @return new instance of data
   */
  def add(v1: Entity): DataFormat = {
    DataFormat(
      sensors + (v1.id -> (sensors(v1.id) add v1.humidity)),
      failedCount + (if (v1.humidity.isDefined) 0 else 1),
      totalCount + 1,
      fileCount
    )
  }

   /*combine all measurements*/
  /**
   *
   * @param v1 data to be combined
   * @return combined data
   */
  def combineAll(v1: DataFormat): DataFormat = {
    DataFormat(
      sensors combine v1.sensors,
      failedCount + v1.failedCount,
      totalCount + v1.totalCount,
      fileCount + v1.fileCount
    )
  }

 /*string format of data*/
  /**
   *
   * @return String form of data
   */
  override def toString: String = {
    implicit val ordering: Ordering[(String, Measurements)] =
      (x: (String, Measurements), y: (String, Measurements)) => (x._2.average, y._2.average) match {
        case (None, None) => 0
        case (None, _) => 1
        case (_, None) => -1
        case (Some(a), Some(b)) => b compare a
      }

    s"""Num of processed files: $fileCount
       |Num of processed measurements: $totalCount
       |Num of failed measurements: $failedCount
       |
       |Sensors with highest avg humidity:
       |
       |sensor-id,min,avg,max
       |${
      sensors.toSeq.sorted.map { case (id, data) => s"$id,$data" }.mkString(Properties.lineSeparator)
    }""".stripMargin
  }
}

