#/bin/bash

# init redis
mkdir -p ./redis/data
mkdir -p ./redis/conf
mkdir -p ./redis/log

touch ./redis/conf/redis.conf

# init zk
mkdir -p ./zookeeper/data
mkdir -p ./zookeeper/datalog

# init kafka
mkdir -p ./kafka/logs

sudo docker-compose up -d
