package dto

import model.Tweet
import service.TweetService.SimilarTweets

/**
  * Created by g.gerasimov on 20.04.2017.
  */
class SimilarTweetsResult(val tweet: Tweet, val tweets: SimilarTweets) {
  override def equals(that: Any): Boolean =
    that match {
      case that: SimilarTweetsResult => this.hashCode == that.hashCode
      case _ => false
    }

  override def hashCode: Int = {
    val ourHash = if (tweets == null) 0 else tweets.hashCode
    super.hashCode + ourHash
  }
}

object SimilarTweetsResult{
  def apply(tweet: Tweet, tweets: SimilarTweets):SimilarTweetsResult = new SimilarTweetsResult(tweet, tweets)
}
