package service

import com.typesafe.config.ConfigFactory
import model.Tweet
import play.api.Logger

import scala.collection.concurrent.TrieMap

/**
  * Created by g.gerasimov on 22.04.2017.
  */
object TweetIndexedStorage {
  private val config = ConfigFactory.load()
  private val StopWords = config.getList("stop_words")
  private val indexedTweets = TrieMap.empty[String, Set[Tweet]]

  def addTweets(tweets: Set[Tweet]) =
    tweets.foreach(tweet => getTokens(tweet).foreach(token => addTweet(token, tweet)))

  def getSimilarInIndex(tweet:Tweet) = getTokens(tweet).flatten(token => indexedTweets.get(token))
    .toList
    .flatten
    .groupBy(identity).mapValues(_.size)
    .groupBy { case (key, value) => value }
    .mapValues(_.keys.toSet)

  private def getTokens(tweet:Tweet) =
    tweet.text.replaceAll("[.,+!@\"]", "")
    .split(" ")
    .filterNot(_.startsWith("https"))
    .filterNot(_.isEmpty)
    .filterNot(StopWords.contains(_))
    .map(_.trim.toLowerCase)
    .toSet

  def getSize:(Int, Int) = (TweetIndexedStorage.indexedTweets.keys.size,
    TweetIndexedStorage.indexedTweets.values.flatten.size)

  def addTweet(token: String, tweet: Tweet) = {
    Logger.info(s"Added tweet $tweet for token $token to Index ( keys - ${TweetIndexedStorage.indexedTweets.keys.size}," +
      s" values - ${TweetIndexedStorage.indexedTweets.values.size} )")

    indexedTweets.get(token) match {
      case Some(foundTweets) =>
        indexedTweets += (token -> (foundTweets + tweet))
      case None =>
        indexedTweets += (token -> Set(tweet))
    }
  }

  def clear() = indexedTweets.clear()
}
