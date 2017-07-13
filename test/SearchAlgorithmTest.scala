import model.Tweet
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}
import service.{TweetIndexedStorage, TweetService}

import scala.collection.immutable.Map

/**
  * Created by g.gerasimov on 22.04.2017.
  */
class SearchAlgorithmTest extends FlatSpec with Matchers with BeforeAndAfterEach {


  override protected def beforeEach(): Unit = {
    TweetIndexedStorage.clear()
  }

  "A TweetService" should "find empty map for empty index" in {
    val result = TweetService.searchSimilarTweets(Tweet(1, "aa", "author", "link"))
    assert(result.tweets equals Map.empty[Int, Iterable[Tweet]])
  }

  "A TweetService" should "find tweet itself for index which contains this tweet" in {
    val tweet = Tweet(1, "aa", "author", "link")
    TweetIndexedStorage.addTweets(Set(tweet))

    val result = TweetService.searchSimilarTweets(tweet)
    assert(result.tweets.keys.size equals 1)
    assert(result.tweets.contains(1))
    result.tweets.get(1).foreach(v => assert(v.contains(tweet)))
  }

  "A TweetService" should "find both tweets with distance 1" in {
    val tweetOne = Tweet(2, "aa bb", "author", "link")
    val tweetTwo = Tweet(3, "aa", "author", "link")
    TweetIndexedStorage.addTweets(Set(tweetOne, tweetTwo))

    val result = TweetService.searchSimilarTweets(Tweet(1, "aa", "author", "link"))
    assert(result.tweets.keys.size equals 1)
    assert(result.tweets.contains(1))
    result.tweets.get(1).foreach(v => assert(v.contains(tweetOne) && v.contains(tweetTwo)))
  }

  "A TweetService" should "find tweets with distance 1 and both tweets 2" in {
    val tweetInDistanceOne = Tweet(2, "aa", "author", "link")
    val tweetInDistanceTwo = Tweet(3, "aa bb", "author", "link")
    val anotherTweetInDistanceTwo = Tweet(3, "~~ AA BB CC", "author", "link")
    TweetIndexedStorage.addTweets(Set(tweetInDistanceOne, tweetInDistanceTwo, anotherTweetInDistanceTwo))

    val result = TweetService.searchSimilarTweets(Tweet(1, "aa bb", "author", "link"))
    assert(result.tweets.keys.size equals 2)
    assert(result.tweets.contains(1))
    assert(result.tweets.contains(2))
    result.tweets.get(1).foreach(v => assert(v.contains(tweetInDistanceOne)))
    result.tweets.get(2).foreach(v => assert(v.contains(tweetInDistanceTwo) && v.contains(anotherTweetInDistanceTwo)))
  }
}
