package com.accenture.job


import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.accenture.models.DataFormat

import java.io.File
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor}

class AkkaStream extends CSVJob {

  /**
   * Intent to this run method is execute files through Akka Stream
   * @param directoryName: directory name
   * @return executed data from all files
   */
  def run(directoryName: String) =  {
    implicit val system: ActorSystem = ActorSystem("Sensor")
    implicit val ec: ExecutionContextExecutor = system.dispatcher

    val source = Source(getInputFiles(directoryName))
    val future = source
      .via(Flow[File].map(file => processCsvFile(file)))
      .runWith(Sink.fold(DataFormat())(_ combineAll _))

    future.onComplete(_ => system.terminate())
    Await.result(future , Duration.Inf)
  }
}

