package dto

import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

import model.Tweet

import scala.concurrent.Future

/**
  * Created by g.gerasimov on 22.04.2017.
  */
case class FetchStatus(task: TwitterApiTask, result:Future[Set[Tweet]]) {
  val startTime = ZonedDateTime.now()
  var finishTime: Option[ZonedDateTime] = None

  def getSpendTime: Long = ChronoUnit.MILLIS.between(startTime, ZonedDateTime.now())
  def isInProgress: Boolean = finishTime.isEmpty

  override def toString: String = s"FetchStatus ( $startTime / $getSpendTime / $isInProgress )"
}
