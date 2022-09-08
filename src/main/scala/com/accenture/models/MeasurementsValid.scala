package com.accenture.models

/**
 * Valid Measurements Scenario
 * @param min
 * @param max
 * @param sum
 * @param count
 */
case class MeasurementsValid(min: Int, max: Int, sum: Int, count: Int) extends Measurements {

  def add(v1: Option[Int]): Measurements = v1 match {
    case None => this
    case Some(v) => MeasurementsValid(math.min(min, v), math.max(max, v), sum + v, count + 1)
  }

  def combineAll(v1: Measurements): Measurements = v1 match {
    case MeasurementsFailed => this
    case a: MeasurementsValid => MeasurementsValid(
      math.min(min, a.min),
      math.max(max, a.max),
      sum + a.sum,
      count + a.count
    )
  }
  override def toString: String = s"$min,${if (count == 0) "NaN" else average.get},$max"
  lazy val average: Option[Int] = Some(sum / count)
}
