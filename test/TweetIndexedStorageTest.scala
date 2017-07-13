import model.Tweet
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}
import service.TweetIndexedStorage

/**
  * Created by g.gerasimov on 22.04.2017.
  */
class TweetIndexedStorageTest extends FlatSpec with Matchers with BeforeAndAfterEach {


  override protected def beforeEach(): Unit = {
    TweetIndexedStorage.clear()
  }

  "A TweetIndexedStorage" should "does not contain duplicates for a single author" in {
    val tweets = Set(Tweet(1, "aa", "author", "link"))
    TweetIndexedStorage.addTweets(tweets)

    val indexSizeBeforeAddedTweet = TweetIndexedStorage.getSize
    assert(indexSizeBeforeAddedTweet._1 equals 1)
    assert(indexSizeBeforeAddedTweet._2 equals 1)

    TweetIndexedStorage.addTweets(tweets)

    val indexSizeAfterAddedTheSameTweet = TweetIndexedStorage.getSize
    assert(indexSizeAfterAddedTheSameTweet._1 equals 1)
    assert(indexSizeAfterAddedTheSameTweet._2 equals 1)
  }

  "A TweetIndexedStorage" should "does not contain key duplicates but values for a different author" in {
    TweetIndexedStorage.addTweets(Set(Tweet(1, "aa", "author", "link")))

    val indexSizeBeforeAddedTweet = TweetIndexedStorage.getSize
    assert(indexSizeBeforeAddedTweet._1 equals 1)
    assert(indexSizeBeforeAddedTweet._2 equals 1)

    TweetIndexedStorage.addTweets(Set(Tweet(1, "aa", "author 2", "link")))

    val indexSizeAfterAddedTweetWithDifferentAuthor = TweetIndexedStorage.getSize
    assert(indexSizeAfterAddedTweetWithDifferentAuthor._1 equals 1)
    assert(indexSizeAfterAddedTweetWithDifferentAuthor._2 equals 2)
  }

  "A TweetIndexedStorage" should "does not contain key duplicates" in {
    TweetIndexedStorage.addTweets(Set(Tweet(1, "aa", "author", "link"), Tweet(2, "aa bb", "author", "link")))

    val indexSizeCase1 = TweetIndexedStorage.getSize
    assert(indexSizeCase1._1 equals 2)
    assert(indexSizeCase1._2 equals 3)

    TweetIndexedStorage.addTweets(Set(Tweet(1, "aa bb", "author", "link"), Tweet(2, "bb", "author", "link"), Tweet(3, "aa", "author", "link")))

    val indexSizeCase2 = TweetIndexedStorage.getSize
    assert(indexSizeCase2._1 equals 2)
    assert(indexSizeCase2._2 equals 7)

    TweetIndexedStorage.addTweets(Set(Tweet(1, "aa bb", "author 2", "link"), Tweet(2, "bb", "author 2", "link"), Tweet(3, "aa", "author 2", "link")))

    val indexSizeCase3 = TweetIndexedStorage.getSize
    assert(indexSizeCase3._1 equals 2)
    assert(indexSizeCase3._2 equals 11)
  }
}
