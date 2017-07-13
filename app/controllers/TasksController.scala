package controllers

import java.util.UUID

import support.conversion.JsonConversionImplicits._
import dto.TwitterApiTask
import play.api.mvc._
import twitter.TwitterClient

object TasksController extends RestController {

  def get(id: UUID) = Action { implicit request =>
    TwitterClient.getSubmittedTask(id) match {
      case Some(task) => ok(task)
      case None => notFound
    }
  }

  def create = Action(parse.json) { implicit request =>
    request.body.validate[TwitterApiTask]
      .map(task => ok(TwitterClient.submitTask(task)))
      .recoverTotal { e => badRequest(e) }
  }

  def preloadTweets = Action { implicit request =>
    ok(TwitterClient.preloadTweets())
  }

  def preloadSomeGarbageTweets = Action { implicit request =>
    ok(TwitterClient.preloadGarbageTweets())
  }
}