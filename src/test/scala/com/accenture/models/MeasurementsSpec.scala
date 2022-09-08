package com.accenture.models

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class MeasurementsSpec extends AnyFlatSpec with should.Matchers {

  "A failed measurements" should "be equal to the measurements, with which it combines" in {
    val valid = MeasurementsValid(5, 13, 15, 5)
    val combined = MeasurementsFailed combineAll valid
    combined should be(valid)
  }

  it should "be be failed after combining with failed measurements" in {
    val combined = MeasurementsFailed combineAll MeasurementsFailed
    combined should be(MeasurementsFailed)
  }
  "A valid measurements" should "be the same after combining with failed measurements" in {
    val valid = MeasurementsValid(5, 13, 15, 5)
    val combined = valid combineAll MeasurementsFailed
    combined should be(valid)
  }

  it should "be valid measurements after combining with valid measurements" in {
    val valid0 = MeasurementsValid(5, 13, 15, 5)
    val valid1 = MeasurementsValid(1, 15, 20, 3)
    val combined = valid0 combineAll valid1
    combined should be(MeasurementsValid(1, 15, 35, 8))
  }

}
