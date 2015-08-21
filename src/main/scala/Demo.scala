import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.twitter._
import org.apache.log4j.Logger
import org.apache.log4j.Level

object Demo {
  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val conf = new SparkConf().setAppName("NetworkWordCount")
    val ssc = new StreamingContext(conf, Seconds(20))
 
    // Create a DStream that will connect to hostname:port, like localhost:9999
    val lines = ssc.socketTextStream("localhost", 9999)

    // Split each line into words
    val words = lines.flatMap(a => a.split(" "))

    // Count each word in each batch
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey((a, b) => a + b)
 
    // Print the first ten elements of each RDD generated in this DStream to the console
    wordCounts.print()

    // val Array(consumerKey, consumerSecret, accessToken, accessTokenSecret) = args.take(4)
    // // Set the system properties so that Twitter4j library used by twitter stream
    // // can use them to generat OAuth credentials
    // System.setProperty("twitter4j.oauth.consumerKey", consumerKey)
    // System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret)
    // System.setProperty("twitter4j.oauth.accessToken", accessToken)
    // System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret)
    // val stream = TwitterUtils.createStream(ssc, None)

    // val hashTags = stream.flatMap(status => status.getText.split(" ").filter(_.startsWith("#")))

    // val topCounts60 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(60))
    //                  .map{case (topic, count) => (count, topic)}
    //                  .transform(_.sortByKey(false))

    // val topCounts10 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(10))
    //                  .map{case (topic, count) => (count, topic)}
    //                  .transform(_.sortByKey(false))


    // // Print popular hashtags
    // topCounts60.foreachRDD(rdd => {
    //   val topList = rdd.take(10)
    //   println("\nPopular topics in last 60 seconds (%s total):".format(rdd.count()))
    //   topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
    // })

    // topCounts10.foreachRDD(rdd => {
    //   val topList = rdd.take(10)
    //   println("\nPopular topics in last 10 seconds (%s total):".format(rdd.count()))
    //   topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
    // })

    ssc.start()
    ssc.awaitTerminationOrTimeout(120)
  }
}
