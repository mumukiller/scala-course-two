package repository

import model.{Tweet, TweetSQLSyntaxSupport}
import scalikejdbc._
import sqls.{count, distinct}


/**
  * Created by g.gerasimov on 18.03.2017.
  */
object TweetRepository {

  val t = TweetSQLSyntaxSupport.syntax("t")

  def uniqueCount(implicit session: DBSession = AutoSession):Int = {
    withSQL {
      select(count(distinct(t.id)))
        .from(TweetSQLSyntaxSupport as t)
    }.map(_.int(1)).single.apply.get
  }

  def getTweet(id: Long)(implicit session: DBSession = AutoSession): Option[Tweet] = {
    withSQL {
      select
        .from(TweetSQLSyntaxSupport as t)
        .where
        .eq(t.id, id.toString)
    }.map(TweetSQLSyntaxSupport(t)).single.apply
  }

  def getTweetBy(text: String, author: String)(implicit session: DBSession = AutoSession): Option[Tweet] = {
    withSQL {
      select
        .from(TweetSQLSyntaxSupport as t)
        .where
        .eq(t.text, text.toString)
        .and
        .eq(t.author, author.toString)
    }.map(TweetSQLSyntaxSupport(t)).single.apply
  }

  def delete(id: Long)(implicit session: DBSession = AutoSession): Long = {
    withSQL {
      deleteFrom(TweetSQLSyntaxSupport).where.eq(t.id, id.toString)
    }.update.apply()
  }

  def update(id: Long, tweet: Tweet)(implicit session: DBSession = AutoSession): Tweet = {
    applyUpdate {
      insert.into(TweetSQLSyntaxSupport)
        .columns(t.id, t.text, t.author, t.link)
        .values(id, tweet.text, tweet.author, tweet.link)
    }
    Tweet(id, tweet.text, tweet.author, tweet.link)
  }

  def exist(id: Long)(implicit session: DBSession = AutoSession): Boolean =  {
    withSQL {
      select(t.id).from(TweetSQLSyntaxSupport as t)
        .where
        .eq(t.id, id.toString)
    }.map(_.int(1)).list.apply().nonEmpty
  }

  def create(tweet: Tweet)(implicit session: DBSession = AutoSession): Tweet = {
    if (!exist(tweet.id)){
      withSQL {
        insertInto(TweetSQLSyntaxSupport).values(tweet.id, tweet.text, tweet.author, tweet.link)
      }.update.apply()
    }
    tweet
  }

  def createCollection(tweets: Set[Tweet])(implicit session: DBSession = AutoSession): Set[Tweet] =
    tweets.map(create)


  def paged(offset: Int, length: Int)(implicit session: DBSession = AutoSession): Set[Tweet] = {
    withSQL {
      select
        .from(TweetSQLSyntaxSupport as t)
        .limit(length)
        .offset(offset)
    }.map(TweetSQLSyntaxSupport(t)).list.apply.toSet
  }

  def getAll()(implicit session: DBSession = AutoSession): Set[Tweet] = {
    withSQL {
      select
        .from(TweetSQLSyntaxSupport as t)
    }.map(TweetSQLSyntaxSupport(t)).list.apply.toSet
  }
}
