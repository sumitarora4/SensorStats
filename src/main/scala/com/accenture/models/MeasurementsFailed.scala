package com.accenture.models

/**
 * If failed Measurements then data for a sensor
 */
case object MeasurementsFailed extends Measurements {

  def add(v1: Option[Int]): Measurements = v1 match {
    case None => this
    case Some(v) => MeasurementsValid(v, v, v, 1)
  }
  def combineAll(v1: Measurements): Measurements = v1

  override def toString: String = "NaN,NaN,NaN"

  override def average: Option[Int] = None
}
