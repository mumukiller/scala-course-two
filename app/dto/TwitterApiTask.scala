package dto

import java.util.UUID

import support.{Constant, Util}

/**
  * Created by g.gerasimov on 25.03.2017.
  */
case class TwitterApiTask(tags: Set[String], count: Int) {
  val id = UUID.randomUUID()
  private val since = "2017-01-01"

  private val query = s"${tags.mkString(" ")} since:$since"
  private val additionalArguments = s" -filter:retweets&count=${count.toString}&lang=en"

  val searchUrl = Constant.TwitterSearchUrl + Util.encodeUrl(query) + additionalArguments
  val rawSearchUrl = Constant.TwitterSearchUrl + query + additionalArguments

  override def toString: String = s"TwitterApiTask( $tags, $count, $id )"
}