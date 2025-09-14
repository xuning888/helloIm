#/bin/bash

# init redis
mkdir -p ./redis/data
mkdir -p ./redis/conf
mkdir -p ./redis/log

if [ ! -f ./redis/conf/redis.conf ]; then
    cat > ./redis/conf/redis.conf << EOF
bind 0.0.0.0
port 6379
EOF
fi

# init zk
mkdir -p ./zookeeper/data
mkdir -p ./zookeeper/datalog

# init kafka
mkdir -p ./kafka/logs

# init mysql
mkdir -p ./mysql/data
mkdir -p ./mysql/logs
mkdir -p ./mysql/init
cp ./script/sql/*.sql ./mysql/init

docker-compose up -d
