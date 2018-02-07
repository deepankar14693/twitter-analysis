package edu.knoldus.retrieve.tweets

import org.apache.log4j.Logger.getLogger

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

//import edu.knoldus.retrieve.tweets.RetrieveTweets


object TwitterAnalysis extends App {
 val log = getLogger(this.getClass)
 val retrieve = new RetrieveTweets
 /*val stream = retrieve.getTwitterStreams("#RajasthanByPolls")
for(x <- stream){
 println(x)
}
println(retrieve.tweetSize("#RajasthanByPolls"))*/
 val stream = retrieve.getTwitterStreams("#KheloIndia")
 stream onComplete {
  case Success(stream) => log.debug(stream + "\n")
  case Failure(ex) => log.debug(ex.getMessage + "\n")
 }

 val size = retrieve.tweetSize("#KheloIndia")
 size onComplete {
  case Success(size) => log.debug(size + "\n")
  case Failure(ex) => log.debug(ex.getMessage + "\n")
 }

 val avgCount = retrieve.tweetCount("#KheloIndia")
 avgCount onComplete {
  case Success(avgCount) => log.debug(avgCount + "\n")
  case Failure(ex) => log.debug(ex.getMessage + "\n")
 }

 val retweetCount = retrieve.getReTweetCount("#KheloIndia")
 retweetCount onComplete {
  case Success(retweetCount) => log.debug(retweetCount + "\n")
  case Failure(ex) => log.debug(ex.getMessage + "\n")
 }
 Thread.sleep(5000)
}
