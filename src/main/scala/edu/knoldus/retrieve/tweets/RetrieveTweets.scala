package edu.knoldus.retrieve.tweets

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.typesafe.config.ConfigFactory
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Query, Status, Twitter, TwitterFactory}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class RetrieveTweets {

 def getTwitterStreams(input: String): Future[List[Status]] = Future {
  //val statuses = getTwitterInstance.getHomeTimeline()
  //Thread.sleep(1000)
  //val newList =
  val query = new Query(input)
  val result = getTwitterInstance.search(query).getTweets.asScala.toList
  //val strength = result.size
  /*println(result)
  println(strength)*/
  if (result == 0)
   throw new Exception("invaliid")
  else
   result
 }

 //getTwitterStreams("#Katrina Kaif")
 def tweetSize(input: String): Future[Int] = Future {
  val query = new Query(input)
  val result = getTwitterInstance.search(query).getTweets.asScala.toList
  if (result == 0)
   throw new Exception("invaliid")
  else
   result.size
 }

 def tweetCount(input: String): Future[Long] = Future {
  val startDate = "2018-01-28"
  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  val oldDate = LocalDate.parse(startDate, formatter)
  val currentDate = "2018-02-01"
  val newDate = LocalDate.parse(currentDate, formatter)
  val diff = (newDate.toEpochDay() - oldDate.toEpochDay() + 1)
  if (diff == 0) {
   throw new Exception("invaliid")
  }
  else {
   val query = new Query(input)
   query.lang("en")
   query.setSince(startDate)
   query.setUntil(currentDate)
   val result = getTwitterInstance.search(query).getTweets
   println(result)
   result.size / diff
   //println(getTwitterInstance.getFavorites("#KheloIndia"))
  }
 }

  def getReTweetCount(hashTag: String): Future[Int] = Future {
   try {
    val query = new Query(hashTag)
    val tweets = getTwitterInstance.search(query).getTweets.asScala.toList
    tweets.map(_.getRetweetCount).size / tweets.size
   }
   catch {
    case exception: Exception => throw exception
   }
  }

  def getLikesCount(hashTag: String): Future[Int] = Future {
   try {
    val query = new Query(hashTag)
    val tweets = getTwitterInstance.search(query).getTweets.asScala.toList
    tweets.map(_.getFavoriteCount).size / tweets.size
   }
   catch {
    case exception: Exception => throw exception
   }
  }


  def getTwitterInstance: Twitter = {
  val config = ConfigFactory.load("application.conf")
  val configurationBuilder = new ConfigurationBuilder()
   .setDebugEnabled(true)
   .setOAuthConsumerKey(config.getString("SecretCredits.Key.ConsumerKey"))
   .setOAuthConsumerSecret(config.getString("SecretCredits.Key.ConsumerSecret"))
   .setOAuthAccessToken(config.getString("SecretCredits.Key.AccessToken"))
   .setOAuthAccessTokenSecret(config.getString("SecretCredits.Key.AccessTokenSecret"))
  new TwitterFactory(configurationBuilder.build).getInstance()
 }

}
