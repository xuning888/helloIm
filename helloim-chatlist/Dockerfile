# 构建运行时镜像
FROM java:openjdk-8u111-jre
WORKDIR /app
COPY target/helloim-chatlist.jar helloim-chatlist.jar

# 设置环境变量
ENV ZK_ADDRESS=""
ENV REDIS_ADDR=""
ENV KAFKA_BOOTSTRAP_SERVERS=""
ENV JAVA_OPTS=""

# 暴露端口号
EXPOSE 8082 28803

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar helloim-chatlist.jar"]
