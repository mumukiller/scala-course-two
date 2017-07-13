package controllers


import support.conversion.JsonConversionImplicits._
import model.Tweet
import play.api.mvc._
import repository.TweetRepository
import service.TweetService

object TweetController extends RestController {

  def get(id: Long) = Action { implicit request =>
    TweetRepository.getTweet(id) match {
      case Some(tweet) => ok(tweet)
      case None => notFound
    }
  }

  def getSimilarTweets(id: Long, count: Int) = Action { implicit request =>
    TweetService.searchSimilarTweets(id, count) match {
      case Some(result) => ok(result)
      case None => notFound
    }
  }

  def delete(id: Long) = Action { implicit request =>
    ok(TweetRepository.delete(id))
  }

  def create = Action(parse.json) { implicit request =>
    request.body.validate[Tweet]
      .map(obj => created(TweetRepository.create(obj)))
      .recoverTotal { e => badRequest(e) }
  }

  def update(id: Long) = Action(parse.json) { implicit request =>
    request.body.validate[Tweet]
      .map(obj => ok(TweetRepository.update(id, obj)))
      .recoverTotal { e => badRequest(e) }
  }

  def paged(offset: Int = 0, length: Int = 10) = Action { implicit request =>
    ok(TweetRepository.paged(offset, length))
  }

}