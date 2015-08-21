name := "twitter-test"

version := "1.0"

scalaVersion := "2.10.5"

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
  }
}

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.4.1" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.4.1" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "1.4.1" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-streaming-twitter" % "1.4.1" 

assemblyJarName in assembly := "demo.jar"
