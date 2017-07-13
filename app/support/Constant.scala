package support

import com.typesafe.config.ConfigFactory

/**
  * Created by g.gerasimov on 22.04.2017.
  */
object Constant {

  val DefaultTagSetForPreloadingTweets = Set("#bmw", "#audi", "#porshe", "#mercedes", "#nissan", "#bentley", "#acura",
    "#gmc", "#jeep", "#jaguar", "#opel", "#toyota", "#bugatti", "#ferrari", "#ford", "#fiat", "#infiniti", "#kia",
    "#lexus", "#maserati", "#mazda", "#mini")

  val DefaultTagSetForPreloadingGarbageTweets = Set("#abstract", "#abstractart", "#abstractartist", "#abstractarts",
    "#abstracted", "#abstractexpressionism", "#abstractexpressionist", "#abstraction", "#abstractors",
    "#abstractpainting", "#abstractphoto", "#abstractphotography", "#abstracts", "#acrylic", "#animation", "#art",
    "#artcall", "#artcompetitions", "#artcontest", "#arte", "#artfair", "#artgallery", "#artinfo", "#artist",
    "#artnews", "#artshow", "#artwork", "#black", "#blackandwhite", "#blackwhitephotography", "#callforart",
    "#callforentries", "#color", "#colour", "#creative", "#drawing", "#drawings", "#fineart", "#graffitiart",
    "#grafiti", "#graphic", "#graphicdesign", "#illustration", "#ink", "#lightspacetime", "#markers", "#model",
    "#mono", "#monoart", "#monochrome", "#mural", "#murals", "#myart", "#onlineart", "#onlineartgallery",
    "#onlineartsales", "#paint", "#painting", "#paintings", "#pencil", "#photo", "#photobomb", "#photobooth",
    "#photocollage", "#photodaily", "#photoday", "#photoftheday", "#photogram", "#photograph", "#photographer",
    "#photography", "#photoofday", "#photooftheweek", "#photos", "#photoscape", "#photoself", "#photosession",
    "#photoshare", "#photoshoot", "#photoshoots", "#photoshop", "#photoshopped", "#photoshot", "#photostudio",
    "#phototag", "#photowall", "#portrait", "#portraits", "#portraiture", "#selfie", "#selfportrait", "#sketch",
    "#spray", "#spraypaint", "#streetart", "#streetartistry", "#streetphotography", "#urban", "#wallart",
    "#watercolor", "#watercolour")

  val AuthenticationTokenUrl = "https://api.twitter.com/oauth2/token"
  val TwitterSearchUrl = "https://api.twitter.com/1.1/search/tweets.json?q="
}
