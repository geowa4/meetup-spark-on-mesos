#!/usr/bin/env bash

MASTER_IP=$(vagrant ssh master1 -c "ip address show eth1 | grep 'inet ' | sed -e 's/^.*inet //' -e 's/\/.*$//' | tr -d '\n'" 2>/dev/null)

open http://$MASTER_IP:5050
open http://$MASTER_IP:8080
open http://$MASTER_IP:4400
open http://127.0.0.1:4040

spark-1.4.1-bin-hadoop2.4/bin/spark-shell --master mesos://zk://$MASTER_IP:2181/mesos --conf spark.executor.uri=file:///opt/spark.tgz
