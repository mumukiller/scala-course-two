package controllers

import play.api.mvc._
import service.TweetService

object Application extends Controller {

  def index = Action {
    //Add indexes for current tweets in database
    TweetService.reindexAll()

    Ok(views.html.index("Your new application is ready. "))
  }
}