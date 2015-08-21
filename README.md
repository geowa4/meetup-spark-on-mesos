Spark on Mesos
==============

This should serve as a simple demonstration of running Apache Spark on Apache Mesos.
None of the code here is meant to be production-quality.
It should only serve as a primer to learn more.

The Ansible deployment also installs other tools, like Marathon and Chronos, to encourage further experimentation.

Usage
-----

1. Install Ansible, Vagrant, Virtualbox, Scala (2.10), and SBT (0.13).
1. Get AWS keys.
1. Save http://d3kbcqa49mib13.cloudfront.net/spark-1.4.1-bin-hadoop2.4.tgz as deploy/roles/spark/files/spark-1.4.1-bin-hadoop2.4.tgz.
1. Untar deploy/roles/spark/files/spark-1.4.1-bin-hadoop2.4.tgz into the project root as spark-1.4.1-bin-hadoop2.4/
1. Edit deploy/group\_vars/mesos\_slave/aws.yml to include those keys

        aws_access_key_id=<your key id>
        aws_secret_access_key=<your secret access key>

1. Optionally, encrypt that file using Ansible vault.
1. Optionally, export these variables if you intend to work with S3 in the Spark Shell.

        export AWS_ACCESS_KEY_ID=<your key id>
        export AWS_SECRET_ACCESS_KEY=<your secret access key>

1. Run the following commands.

        $ ./up.sh
        $ ./open.sh
        scala> val data = Array(1, 2, 3, 4, 5)
        scala> val distData = sc.parallelize(data)
        scala> distData.reduce((a, b) => a + b)

If you have web logs in an S3 bucket, try something like the following.

    scala> val logLines = sc.textFile("s3n://apache-spark-log-bucket/last-1k.log")
    scala> val lengths = logLines.map(l => l.length)
    scala> val totalLength = lengths.reduce((a, b) => a + b)
    scala> val servicesWith400 = logLines.
        filter(l => l.matches(""".*\s(4\d\d)\s.*""")).
        map(l => """^\[(.+?)\s.*""".r.findFirstMatchIn(l) match {
            case Some(m) => (m.group(1), 1)
            case None => ("Other", 1)
        })
    scala> val erroringServices = servicesWith400.
        reduceByKey((a, b) => a + b).
        map(item => item.swap).
        sortByKey(false)
    scala> erroringServices.take(100).foreach(println)

For Spark Streaming with netcat, have Spark installed locally (`brew install apache-spark`) and run this in the shell.

    $ sbt clean assembly
    $ spark-submit target/scala-2.10/demo.jar
    $ # in a new shell
    $ nc -lk 9999
    $ # enter some text with a new line

For Spark Streaming with Twitter, make the necessary changes in Demo.scala and run this in the shell.

    $ sbt clean assembly
    $ ./spark-1.4.1-bin-hadoop2.4/bin/spark-submit --master mesos://$MASTER_IP:5050 --conf spark.executor.uri=file:///opt/spark.tgz target/scala-2.10/demo.jar consumerKey consumerSecret accessToken accessTokenSecret
