package service

import dto.SimilarTweetsResult
import model.Tweet
import play.api.Logger
import repository.TweetRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.immutable.Map
import scala.concurrent.Future

/**
  * Created by g.gerasimov on 15.04.2017.
  */
object TweetService {
  type SimilarTweets = Map[Int, Set[Tweet]]

  def searchSimilarTweets(id: Long, count: Int): Option[SimilarTweetsResult] = {
    TweetRepository.getTweet(id).map(tweet => searchSimilarTweets(tweet, count))
  }

  def searchSimilarTweets(tweet: Tweet, count: Int): SimilarTweetsResult = {
    val indexSize = TweetIndexedStorage.getSize
    Logger.info(s"Searching tweets in Index ( keys - ${indexSize._1}, values - ${indexSize._2} ) similar to $tweet")

    SimilarTweetsResult(tweet, TweetIndexedStorage.getSimilarInIndex(tweet))
  }

  def searchSimilarTweets(tweet: Tweet): SimilarTweetsResult = searchSimilarTweets(tweet, -1)

  def createTweets(tweets: Set[Tweet]): Future[Set[Tweet]] = {
    val result = for {
      created <- Future { TweetRepository.createCollection(tweets) }
      indexed <- Future { TweetIndexedStorage.addTweets(tweets) }
    } yield created

    result map {
      case createdTweets: Set[Tweet] => createdTweets
      case _ => Set.empty
    }
  }

  def indexTweet(token: String, tweet: Tweet) = TweetIndexedStorage.addTweet(token, tweet)

  def reindexAll() = TweetIndexedStorage.addTweets(TweetRepository.getAll)
}

