# 构建运行时镜像
FROM java:openjdk-8u111-jre
WORKDIR /app
COPY target/helloIm-message.jar helloIm-message.jar

# 设置环境变量
# zookeeper的地址, 用于dubbo的注册中心
ENV ZK_ADDRESS=""
# mysql的连接信息
ENV DB_USERNAME=""
ENV DB_PASSWORD=""
ENV DB_URL=""

# redis的地址, 目前是以standalone为例
ENV REDIS_ADDR=""
ENV JAVA_OPTS=""

# kafka的地址
ENV KAFKA_BOOTSTRAP_SERVERS=""

# 暴露端口号
EXPOSE 8084 28805

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar helloIm-message.jar"]
