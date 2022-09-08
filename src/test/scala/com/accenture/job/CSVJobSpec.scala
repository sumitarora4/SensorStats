package com.accenture.job

import java.io.IOException
import com.accenture.models.{DataFormat, MeasurementsFailed, MeasurementsValid}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.collection.immutable.HashMap
import scala.util.{Failure, Success}

class CSVJobSpec extends AnyFlatSpec with should.Matchers {

trait Job {
    val job = CSVJob.run("src/test/resources/default/")
  }

  it should "throw IOException for an invalid path" in {
    {
      val directoryName = getClass.getResource("/invalid").getPath
      val result = CSVJob.run(directoryName)
      result match {
        case Failure(e) => assert(e.isInstanceOf[IOException])
        case Success(_) => fail("Should fail!")
      }
    }
  }


  it should "throw NullPointerException for an empty path" in {
    assertThrows[NullPointerException] {
      val directoryName = getClass.getResource("/empty").getPath
      CSVJob.run(directoryName)
    }
  }

  val defaultDataFormat: DataFormat = DataFormat(
    HashMap(
      "s1" -> MeasurementsValid(10, 98, 108, 2),
      "s2" -> MeasurementsValid(78, 88, 246, 3),
      "s3" -> MeasurementsFailed
    ), 2, 7, 2
  )



  "Output of default test" should "be as specified including string representation" in new Job {

    job match {
      case Failure(e) => assert(e.isInstanceOf[IOException])
      case Success(value) =>
        def newSeparator(s: String) = s.replaceAll("\\r\\n|\\r|\\n", "\\n")

        newSeparator(value.toString) should be(
          newSeparator(
            """Num of processed files: 2
              |Num of processed measurements: 7
              |Num of failed measurements: 2
              |
              |Sensors with highest avg humidity:
              |
              |sensor-id,min,avg,max
              |s2,78,82,88
              |s1,10,54,98
              |s3,NaN,NaN,NaN""".stripMargin))
    }
  }


}



