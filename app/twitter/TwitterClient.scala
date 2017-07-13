package twitter

import java.time.ZonedDateTime
import java.util.UUID

import com.typesafe.config.ConfigFactory
import dto.{FetchStatus, TwitterApiTask}
import model.Tweet
import play.api.Logger
import play.api.libs.json.{JsError, JsSuccess}
import play.api.libs.oauth.{ConsumerKey, OAuthCalculator, RequestToken}
import play.api.libs.ws.WS
import service.TweetService
import support.{Constant, Util}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api.Play.current
import support.conversion.JsonConversionImplicits._

import scala.util.{Failure, Success}

/**
  * Created by g.gerasimov on 18.03.2017.
  */
object TwitterClient {
  val config = ConfigFactory.load()
  val oAuth = OAuthCalculator(
    ConsumerKey(config.getString("twitter.consumer_key"), config.getString("twitter.consumer_secret")),
    RequestToken(config.getString("twitter.access_token_key"), config.getString("twitter.access_token_secret")))

  private val tasks = scala.collection.mutable.Map[UUID, FetchStatus]()

  private val originBearer = config.getString("twitter.consumer_key") + ":" + config.getString("twitter.consumer_secret")
  private val Body = Map("grant_type" -> Seq("client_credentials"))
  private val ContentTypeHeader = "Content-Type" -> "application/x-www-form-urlencoded;charset=UTF-8"
  private val AuthorizationHeader = "Authorization" -> ("Basic " + Util.base64Encode(originBearer))

  def submitTask(task: TwitterApiTask): FetchStatus = submitTask(task, getToken)

  private def submitTask(task: TwitterApiTask, token: Future[Option[BearerToken]]): FetchStatus = {
    val result = token.flatMap {
      case Some(t) => loadTweets(task, t)
      case None => Future.failed(UnableToObtainTokenException.getInstance())
    }

    val status = FetchStatus(task, result)

    tasks += (task.id -> status)

    result onComplete {
      case Success(tweets) =>
        Logger.info(s"Loading tweets using task - $task - successfully finished, tweets loaded ${tweets.size}")
        finishTask(task.id)
      case Failure(t) =>
        Logger.info(s"Loading tweets using task - $task - failed, reason - ${t.getMessage}, $t")
        finishTask(task.id)
    }

    status
  }

  private def finishTask(uuid: UUID) = {
    tasks.get(uuid).foreach(inProgressTask => inProgressTask.finishTime = Some(ZonedDateTime.now()))
    tasks.remove(uuid)
  }

  def getSubmittedTask(id: UUID): Option[FetchStatus] = tasks.get(id)

  def preloadTweets(): Set[FetchStatus] = {
    val token = getToken
    Constant.DefaultTagSetForPreloadingTweets
      .map(tag => submitTask(TwitterApiTask(Set(tag), 100), token))
  }

  def preloadGarbageTweets(): Set[FetchStatus] = {
    val token = getToken
    Constant.DefaultTagSetForPreloadingGarbageTweets
      .map(tag => submitTask(TwitterApiTask(Set(tag), 100), token))
  }

  private def loadTweets(task: TwitterApiTask, token: BearerToken): Future[Set[Tweet]] =
    WS.url(task.searchUrl)
    .withHeaders("Authorization" -> token.accessToken)
    .get()
    .flatMap { r => (r.json \ "statuses").validate[Set[Tweet]] match {
        case s: JsSuccess[Set[Tweet]] => TweetService.createTweets(s.get)
        case e: JsError =>
          Logger.error(JsError.toJson(e).toString())
          Future.successful(Set.empty)
      }
    }

  private def getToken: Future[Option[BearerToken]] =
    WS.url(Constant.AuthenticationTokenUrl)
    .withHeaders(AuthorizationHeader, ContentTypeHeader)
    .post(Body)
    .map { r => r.json.validate[BearerToken] match {
        case s: JsSuccess[BearerToken] => Some(s.get)
        case e: JsError =>
          Logger.error(JsError.toJson(e).toString())
          None
      }
    }
}
