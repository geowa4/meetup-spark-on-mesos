footer: George Adams IV, 2015
slidenumbers: true

# Spark on Mesos

---

## Who am I?

- Born in Rochester
- RIT Software Engineering alum
- Brand Networks for 3+ years
- [http://geowa4.software](http://geowa4.software)

---

## Topics

* Overview
* Use Cases
* Architecture
* Mesos
* Demo

^ There's a lot to Spark. This should serve as an introduction to encourage further exploration.

---

## Overview

> ...fast and general-purpose cluster computing system.
-- http://spark.apache.org/docs/latest/index.html

^ ...with an emphasis on data.

---

## Language Support

- Scala
- Java
- Python
- R

^ R was recently added in 1.4.

^ Scala has the best support and will be my focus.

---

## Tools

- Spark SQL
- MLib
- GraphX
- Spark Streaming
- Spark Shell

^ And these are just the official libraries.

---

## Clustering Options

- Apache Mesos
- Standalone
  - Amazon EC2
- Hadoop YARN

^ I recommend using Mesos.

^ Use the standalone option if you don't want to worry about anything else.

---

## Data Sources

- JDBC
- Cassandra
- Elasticsearch
- HDFS / S3

---

### Streaming Sources

- Kafka
- AWS Kinesis
- Twitter
- HDFS / S3

---

### Outputs

Pretty much the same as the inputs.

---

## ETL

> Spark is my new go-to ETL tool.
-- Brian Clapper

^ https://twitter.com/brianclapper/status/617114500882386944

^ Use case #1 and a good place to start with Spark.

^ Including joining from multiple data stores.

---

## Data Consumption

Pull data from external sources into local data store.

[Custom Receivers](http://spark.apache.org/docs/latest/streaming-custom-receivers.html)

^ Find or write your own Receiver.

---

## Machine Learning

- With static data set
- Iteratively with streaming

---

## Scalable Code

Find the most common word in the lines containing "Spark".

```
lines
    .filter(l => l.contains("Spark"))
    .flatMap(l => l.split(" "))
    .map(word => (word, 1))
    .reduceByKey((a, b) => a + b)
    .map(pair => pair.swap)
    .sortByKey(false)
```

```
(12, foo)
(3, bar)
(1, baz)
```

^ This is why I like Spark.

^ This code will work on a small in-memory list or a giant data set.

---

## Architecture

![inline](http://spark.apache.org/docs/latest/img/cluster-overview.png)

---

## Streaming Architecture

![inline](https://spark.apache.org/docs/latest/img/streaming-flow.png)

---

## Mesos Achitecture

![inline](http://www.ericsson.com/research-blog/wp-content/uploads/2015/01/Mesosmesos.jpg)

^ Mesos is a general purpose resource manager.

^ Spark was initially built by the people who made Mesos to prove their design.

^ Mesos allows Spark to run alongside other applications, maximizing utilization.

---

## Let's run it

