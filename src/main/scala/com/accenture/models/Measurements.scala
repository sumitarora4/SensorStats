package com.accenture.models

import cats.Semigroup

/**
 * measurements for sensor data
 * having all required functions
 */
trait Measurements {
  def add(v1: Option[Int]): Measurements
  def combineAll(v1: Measurements): Measurements
  def average: Option[Int]
}

/**
 * Implicit Implementation of Measurements
 */
trait MeasurementsSemigroupImpl extends Semigroup[Measurements] {
  def combine(v2: Measurements, v1: Measurements): Measurements = v2 combineAll v1
}
trait MeasurementsSemigroup {
  implicit object measurementsSemigroupImpl extends MeasurementsSemigroupImpl
}
