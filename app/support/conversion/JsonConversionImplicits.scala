package support.conversion

import java.util.UUID

import dto.{FetchStatus, SimilarTweetsResult, TwitterApiTask}
import model.Tweet
import play.api.libs.functional.syntax._
import play.api.libs.json.{Json, _}
import twitter.BearerToken


object JsonConversionImplicits {

  implicit val dateWrites = Writes.dateWrites("dd-MM-yyyy HH:mm:ss")
  implicit val dateReads = Reads.dateReads("dd-MM-yyyy HH:mm:ss")

  implicit val uuidWrites = new Writes[UUID] {
    def writes(uuid: UUID) = Json.obj(
      "uuid" -> uuid.toString
    )
  }

  implicit val tweetDtoWrites = Json.writes[Tweet]

  implicit val twitterApiFetchStatusWrites = new Writes[FetchStatus] {
    def writes(status: FetchStatus) = Json.obj(
      "uuid" -> status.task.id.toString,
      "startTime" -> status.startTime,
      "spendTime" -> status.getSpendTime,
      "inProgress" -> status.isInProgress
    )
  }

  implicit val similarTweetsResultWrites = new Writes[SimilarTweetsResult] {
    def writes(s: SimilarTweetsResult) = Json.obj(
      "target" -> s.tweet,
      "similar" -> Json.arr(s.tweets.map { case (distance, tweets) =>
        Json.obj("distance" -> distance, "size" -> tweets.size, "tweets" -> tweets)
      })
    )
  }

  implicit val twitterApiTaskReads: Reads[TwitterApiTask] = (
    (__ \ "tags").read[String] and
      (__ \ "count").read[Int] and
      (__ \ "since").read[String]
    ) ((tags, count, since) => TwitterApiTask(tags.split(" ").toSet, count))

  implicit val tweetDtoReads: Reads[Tweet] = (
    (__ \ "id").read[Long] and
      (__ \ "text").read[String] and
      (__ \ "user" \\ "name").read[String] and
      (__ \ "source").read[String]
    ) ((id, text, author, link) => Tweet(id, text, author, link))


  implicit val appAuthBearerTokenReads: Reads[BearerToken] = (
    (__ \ "token_type").read[String] and
      (__ \ "access_token").read[String]
    ) ((tokenType, accessToken) => BearerToken(tokenType, "Bearer " + accessToken))

}