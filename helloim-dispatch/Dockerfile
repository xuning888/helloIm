# 构建运行时镜像
FROM java:openjdk-8u111-jre
WORKDIR /app
COPY target/helloim-dispatch.jar helloim-dispatch.jar

# 设置环境变量
# zookeeper的地址, 用于dubbo的注册中心
ENV ZK_ADDRESS=""

ENV REDIS_ADDR=""

ENV KAFKA_BOOTSTRAP_SERVERS=""

# 暴露端口号
EXPOSE 8080 28801

# 启动应用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar helloim-dispatch.jar"]
