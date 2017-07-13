package model

import scalikejdbc._

case class Tweet(id: Long, text: String, author: String, link: String) {

  override def toString: String = s"Tweet( text - $text, author - $author )"
}

object TweetSQLSyntaxSupport extends SQLSyntaxSupport[Tweet] {

  override val tableName = "tweets"
  override val columns = Seq("id", "text", "author", "link")

  def apply(t: SyntaxProvider[Tweet])(rs: WrappedResultSet): Tweet = apply(t.resultName)(rs)
  def apply(t: ResultName[Tweet])(rs: WrappedResultSet) =
    Tweet(rs.long(t.id), rs.string(t.text), rs.string(t.author), rs.string(t.link))

}

